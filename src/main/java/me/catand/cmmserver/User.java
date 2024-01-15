package me.catand.cmmserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
	private String uuid;
	private String name;
	private String version;
	private String clientType;
	private boolean invisible;
	// 仅能被服务器修改
	private boolean isOp = false;
	private boolean isBanned = false;
	private boolean isMuted = false;
}