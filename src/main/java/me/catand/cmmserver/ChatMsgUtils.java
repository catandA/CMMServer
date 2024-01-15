package me.catand.cmmserver;

public class ChatMsgUtils {
	public static final String MOTD = "欢迎连接到 CatandMineMod 服务器！\\\\n随便聊天 别太极端";

	public static String sendAuthJson() {
		return "{\"type\":\"auth\",\"message\":\"" + MOTD + "\"}";
	}

	public static String sendChatJson(User sender, String message) {
		return "{\"type\":\"chat\",\"message\":\"\\[norank\\] " + sender.getName() + ": " + message + "\"}";
	}

	public static String sendErrorJson(String message) {
		return "{\"type\":\"error\",\"message\":\"" + message + "\"}";
	}
}
