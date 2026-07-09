package com.digitalbanking.authservice.exception;

public class AuthException extends RuntimeException {

	private final AuthErrorCode errorCode;

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public AuthException(AuthErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public AuthErrorCode getErrorCode() {
		return errorCode;
	}
}
