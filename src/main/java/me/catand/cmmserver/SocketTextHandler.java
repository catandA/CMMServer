package me.catand.cmmserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class SocketTextHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(SocketTextHandler.class);
	Gson gson = new Gson();
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info(session.getId() + " 已连接");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info(session.getId() + " 已断开连接");
		boolean sendLeave = true;
		if (SessionHandler.sessionInfoMap.containsKey(session)) {
			User info = SessionHandler.sessionInfoMap.get(session);
			if (!info.isInvisible()) {
				sendLeave = false;
			}
		}
		User info = SessionHandler.sessionInfoMap.get(session);
		SessionHandler.removeSession(session);
		if (sendLeave) {
			ChatSender.sendLeave(info);
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		try {
			JsonObject msgJson = gson.fromJson(message.getPayload(), JsonObject.class);
			String type = msgJson.get("type").getAsString();
			if (type == null) {
				logger.error(session.getId() + " 没有消息类型\n" + msgJson);
				ChatSender.sendError(session, "没有消息类型");
			} else {
				try {
					MessageType messageType = MessageType.valueOf(type.toUpperCase());
					messageType.handleMessage(session, msgJson);
				} catch (IllegalArgumentException e) {
					logger.error(session.getId() + " 无效的消息类型\n" + msgJson);
					ChatSender.sendError(session, "无效的消息类型");
				}
			}
		} catch (JsonSyntaxException e) {
			logger.error(session.getId() + " 无效消息\n" + message.getPayload());
			logger.error(e.getMessage());
			try {
				ChatSender.sendError(session, "无效消息");
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}