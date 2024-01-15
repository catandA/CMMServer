package me.catand.cmmserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChatMsgUtils {
	public static final String MOTD = "欢迎连接到 CatandMineMod 服务器！\\\\n随便聊天 别太极端";

	public static String sendAuthJson() {
		return "{\"type\":\"auth\",\"message\":\"" + MOTD + "\"}";
	}

	public static String sendChatJson(User sender, String message) {
		return "{\"type\":\"chat\",\"message\":\"\\[norank\\] " + sender.getName() + ": " + message + "\"}";
	}

	public static String sendPlayerListJson() {
		JsonObject result = new JsonObject();
		result.addProperty("type", "player_list");
		JsonArray playersArray = new JsonArray();
		for (User user : SessionHandler.sessionInfoMap.values()) {
			JsonObject playerObject = new JsonObject();
			playerObject.addProperty("name", user.getName());
			playersArray.add(playerObject);
		}
		result.add("players", playersArray);
		return result.toString();
	}

	public static String sendErrorJson(String message) {
		return "{\"type\":\"error\",\"message\":\"" + message + "\"}";
	}
}
