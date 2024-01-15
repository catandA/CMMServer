package me.catand.cmmserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private String uuid;
	private String name;
	private String version;
	private String clientType;
	private boolean invisible;
}