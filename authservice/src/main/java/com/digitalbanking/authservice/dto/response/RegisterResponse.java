package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;

public class RegisterResponse {

	private Long userId;
	private String username;
	private String email;
	private String mobile;
	private String firstName;
	private String lastName;
	private String status;
	private LocalDateTime createdAt;

	public RegisterResponse() {
	}

	public RegisterResponse(Long userId, String username, String email, String mobile, String firstName,
			String lastName, String status, LocalDateTime createdAt) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.createdAt = createdAt;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
