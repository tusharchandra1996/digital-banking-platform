package com.digitalbanking.authservice.service;

import com.digitalbanking.authservice.dto.request.LoginRequest;
import com.digitalbanking.authservice.dto.request.RefreshTokenRequest;
import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.LoginResponse;
import com.digitalbanking.authservice.dto.response.RefreshTokenResponse;
import com.digitalbanking.authservice.dto.response.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest request, String correlationId);

	LoginResponse login(LoginRequest request, String correlationId);

	RefreshTokenResponse refreshToken(RefreshTokenRequest request, String correlationId);

}
