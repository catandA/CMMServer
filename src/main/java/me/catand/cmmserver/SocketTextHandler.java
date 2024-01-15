package me.catand.cmmserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
		SessionHandler.removeSession(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		JsonObject msgJson = gson.fromJson(message.getPayload(), JsonObject.class);
		String type = msgJson.get("type").getAsString();
		if (type == null) {
			try {
				logger.error(session.getId() + " 没有消息类型\n" + msgJson);
				session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson("没有消息类型")));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				MessageType messageType = MessageType.valueOf(type.toUpperCase());
				messageType.handleMessage(session, msgJson);
			} catch (IllegalArgumentException e) {
				try {
					logger.error(session.getId() + " 无效的消息类型\n" + msgJson);
					session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson("无效的消息类型")));
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}
}