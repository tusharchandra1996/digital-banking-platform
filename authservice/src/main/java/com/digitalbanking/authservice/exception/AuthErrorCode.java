package com.digitalbanking.authservice.exception;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode {

	USERNAME_ALREADY_EXISTS("AUTH-1010", "Username already exists", HttpStatus.CONFLICT),

	EMAIL_ALREADY_REGISTERED("AUTH-1011", "Email already registered", HttpStatus.CONFLICT),

	MOBILE_ALREADY_REGISTERED("AUTH-1012", "Mobile number already registered", HttpStatus.CONFLICT),

	PASSWORD_CONFIRMATION_MISMATCH("AUTH-1009", "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

	INVALID_PASSWORD_FORMAT("AUTH-1008", "Password must contain uppercase, lowercase, number and special character",
			HttpStatus.BAD_REQUEST),

	INVALID_MOBILE_NUMBER("AUTH-1007", "Mobile number must be 10 digits", HttpStatus.BAD_REQUEST),

	DEFAULT_ROLE_NOT_FOUND("AUTH-1014", "Default customer role not configured", HttpStatus.INTERNAL_SERVER_ERROR),

	USER_REGISTRATION_FAILED("AUTH-1013", "Failed to create user account", HttpStatus.INTERNAL_SERVER_ERROR),

	VALIDATION_FAILED("AUTH-0001", "Request validation failed", HttpStatus.BAD_REQUEST),

	INTERNAL_SERVER_ERROR("AUTH-9999", "Internal authentication service error", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	AuthErrorCode(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
