package com.digitalbanking.authservice.dto.common;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	private String correlationId;
	private LocalDateTime timestamp;
	private String message;
	private T data;

	public ApiResponse() {
	}

	public ApiResponse(String correlationId, LocalDateTime timestamp, String message, T data) {
		this.correlationId = correlationId;
		this.timestamp = timestamp;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(String correlationId, String message, T data) {
		return new ApiResponse<T>(correlationId, LocalDateTime.now(), message, data);
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
