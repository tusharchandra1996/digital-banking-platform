package com.digitalbanking.authservice.util;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

public final class CorrelationIdUtil {

	private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

	private CorrelationIdUtil() {
	}

	public static String getCorrelationId(HttpServletRequest request) {
		String correlationId = request.getHeader(CORRELATION_ID_HEADER);

		if (correlationId == null || correlationId.isBlank()) {
			return UUID.randomUUID().toString();
		}

		return correlationId;
	}
}
