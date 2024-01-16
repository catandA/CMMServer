package me.catand.cmmserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class ChatSender {

	private static final Logger logger = LoggerFactory.getLogger(ChatSender.class);

	public static void sendAuth(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(ChatMsgUtils.sendAuthJson()));
	}

	public static void sendChat(User sender, String message) {
		SessionHandler.broadcastMessage(ChatMsgUtils.sendChatJson(sender, message));
	}

	public static void sendPlayerList(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(ChatMsgUtils.sendPlayerListJson()));
	}

	public static void sendJoin(User player) throws IOException {
		SessionHandler.broadcastMessage(ChatMsgUtils.sendJoinJson(player));
	}

	public static void sendLeave(User player) throws IOException {
		SessionHandler.broadcastMessage(ChatMsgUtils.sendLeaveJson(player));
	}

	public static void sendError(WebSocketSession session, String message) throws IOException {
		session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson(message)));
	}
}
