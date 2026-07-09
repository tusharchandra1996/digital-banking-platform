package com.digitalbanking.authservice.dto.common;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

	private String correlationId;
	private LocalDateTime timestamp;
	private String errorCode;
	private String message;
	private String path;
	private Map<String, String> validationErrors;

	public ErrorResponse() {
	}

	public ErrorResponse(String correlationId, LocalDateTime timestamp, String errorCode, String message, String path,
			Map<String, String> validationErrors) {
		this.correlationId = correlationId;
		this.timestamp = timestamp;
		this.errorCode = errorCode;
		this.message = message;
		this.path = path;
		this.validationErrors = validationErrors;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
