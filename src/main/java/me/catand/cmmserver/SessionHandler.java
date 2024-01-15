package me.catand.cmmserver;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SessionHandler {
	private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	public static final ConcurrentHashMap<WebSocketSession, User> sessionInfoMap = new ConcurrentHashMap<>();

	public static void addSession(WebSocketSession session, User info) {
		sessions.add(session);
		sessionInfoMap.put(session, info);
	}

	public static void removeSession(WebSocketSession session) {
		sessions.remove(session);
		sessionInfoMap.remove(session);
	}

	public static void broadcastMessage(String message) {
		TextMessage textMessage = new TextMessage(message);
		for (WebSocketSession session : sessions) {
			try {
				session.sendMessage(textMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}