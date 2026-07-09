package com.digitalbanking.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbanking.authservice.dto.common.ApiResponse;
import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.RegisterResponse;
import com.digitalbanking.authservice.service.AuthService;
import com.digitalbanking.authservice.util.CorrelationIdUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request,
			HttpServletRequest httpRequest) {
		String correlationId = CorrelationIdUtil.getCorrelationId(httpRequest);
		System.out.println("correlationId=="+correlationId);
		RegisterResponse response = authService.register(request, correlationId);

		ApiResponse<RegisterResponse> apiResponse = ApiResponse.success(correlationId, "User registered successfully",
				response);

		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	// POST /api/auth/login

	// POST /api/auth/refresh-token

	// POST /api/auth/logout

	// POST /api/auth/change-password

	// POST /api/auth/forgot-password

	// POST /api/auth/reset-password

	// GET /api/auth/profile
	@GetMapping("/profile")
	public String getProfile() {
		return "api called";

	}

}
