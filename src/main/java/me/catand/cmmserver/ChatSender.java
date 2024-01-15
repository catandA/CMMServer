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
		logger.info("正在给" + session.getId() + "发送认证信息...");
	}

	public static void sendChat(User sender, String message) {
		SessionHandler.broadcastMessage(ChatMsgUtils.sendChatJson(sender, message));
	}

	public static void sendPlayerList(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(ChatMsgUtils.sendPlayerListJson()));
	}

	public static void sendError(WebSocketSession session, String message) throws IOException {
		session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson(message)));
	}
}
