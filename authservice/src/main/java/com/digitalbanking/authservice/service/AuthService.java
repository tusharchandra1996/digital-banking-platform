package com.digitalbanking.authservice.service;

import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest request, String correlationId);
}
