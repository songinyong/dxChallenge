/*
 * 인증용 jwttoken과 refreshToken으로 나누어짐
 * 현재 보안을 위해 jwttoken을 클라이언트 역할을 하는 앱에서 저장하는것은 확실하나
 * refresh token은 따로 서버에 보관해서 연동하든지 새롭게 토큰을 발급할지는 프젝 발표 이후 상의해봐야함
 * */

package com.domain.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {

	private String token;
	private String refreshToken;
	
	public Token(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}
}
