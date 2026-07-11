package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class LoginResponse {

	private String accessToken;

	private String refreshToken;

	private String tokenType;

	private Long expiresIn;

	private Long userId;

	private String username;

	private List<String> roles;

	private LocalDateTime loginTime;

	public LoginResponse() {
	}

	public LoginResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn, Long userId,
			String username, List<String> roles, LocalDateTime loginTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.userId = userId;
		this.username = username;
		this.roles = roles;
		this.loginTime = loginTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}
}
