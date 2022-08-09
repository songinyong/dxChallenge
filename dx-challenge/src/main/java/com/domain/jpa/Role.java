package com.domain.jpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	GUEST("ROLE_GUEST", "게스트"),
	USER("ROLE_USER", "사용자"),
	ADMIN("ROLE_ADMIN", "어드민");
	
	private final String key;
	private final String title ;
	
}
