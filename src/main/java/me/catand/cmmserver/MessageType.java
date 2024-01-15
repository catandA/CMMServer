package me.catand.cmmserver;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public enum MessageType {
	//验证
	AUTH {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {
			String uuid = msgJson.get("uuid").getAsString();
			String name = msgJson.get("name").getAsString();
			String version = msgJson.get("version").getAsString();
			String clientType = msgJson.get("clientType").getAsString();
			boolean invisible = msgJson.get("invisible").getAsBoolean();
			if (uuid == null || uuid.isEmpty() || name == null || name.isEmpty() || version == null || version.isEmpty() || clientType == null || clientType.isEmpty()) {
				try {
					logger.error(session.getId() + " 验证失败\n" + msgJson);
					session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson("消息参数错误, 验证失败")));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				try {
					ChatSender.sendAuth(session);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				logger.info(session.getId() + " 验证成功, " + name + " (" + version + clientType + ", " + (invisible ? "隐身" : "可见") + ")");
				User info = new User(uuid, name, version, clientType, invisible);
				SessionHandler.addSession(session, info);
			}
		}
	},
	//聊天消息
	CHAT {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {
			String message = msgJson.get("message").getAsString();
			User sender = SessionHandler.sessionInfoMap.get(session);
			if (sender == null) {
				try {
					logger.error(session.getId() + " 未验证, 发送消息失败\n" + msgJson);
					session.sendMessage(new TextMessage(ChatMsgUtils.sendErrorJson("未成功验证, 发送消息失败")));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				logger.info(sender.getName() + " 发送消息: " + message);
				ChatSender.sendChat(sender, message);
			}
		}
	},
	//展示物品
	SHOW {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//广播
	BROADCAST {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//命令
	COMMAND {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//调试
	DEBUG {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//在线列表
	PLAYER_LIST {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//错误
	ERROR {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	},
	//链接
	LINK {
		@Override
		public void handleMessage(WebSocketSession session, JsonObject msgJson) {

		}
	};

	private static final Logger logger = LoggerFactory.getLogger(MessageType.class);

	public abstract void handleMessage(WebSocketSession session, JsonObject msgJson);
}