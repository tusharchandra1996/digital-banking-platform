package com.digitalbanking.authservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalbanking.authservice.dto.request.LoginRequest;
import com.digitalbanking.authservice.dto.request.RefreshTokenRequest;
import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.LoginResponse;
import com.digitalbanking.authservice.dto.response.RefreshTokenResponse;
import com.digitalbanking.authservice.dto.response.RegisterResponse;
import com.digitalbanking.authservice.entity.RefreshToken;
import com.digitalbanking.authservice.entity.Role;
import com.digitalbanking.authservice.entity.User;
import com.digitalbanking.authservice.entity.UserStatus;
import com.digitalbanking.authservice.exception.AuthErrorCode;
import com.digitalbanking.authservice.exception.AuthException;
import com.digitalbanking.authservice.mapper.UserMapper;
import com.digitalbanking.authservice.repository.RefreshTokenRepository;
import com.digitalbanking.authservice.repository.RoleRepository;
import com.digitalbanking.authservice.repository.UserRepository;
import com.digitalbanking.authservice.security.JwtService;
import com.digitalbanking.authservice.service.AuthService;
import com.digitalbanking.authservice.validator.PasswordValidator;

@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private static final String DEFAULT_CUSTOMER_ROLE = "ROLE_CUSTOMER";

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final RefreshTokenRepository refreshTokenRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	private final PasswordValidator passwordValidator;

	private final JwtService jwtService;

	@Value("${jwt.refresh-token-expiry-ms}")
	private Long refreshTokenExpiryMs;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder, UserMapper userMapper,
			PasswordValidator passwordValidator, JwtService jwtService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.passwordValidator = passwordValidator;
		this.jwtService = jwtService;
	}

	@Override
	@Transactional
	public RegisterResponse register(RegisterRequest request, String correlationId) {
		logger.info("Register request received. correlationId={}, username={}", correlationId, request.getUsername());

		validateRegisterRequest(request);

		try {
			Role customerRole = roleRepository.findByRoleName(DEFAULT_CUSTOMER_ROLE)
					.orElseThrow(() -> new AuthException(AuthErrorCode.DEFAULT_ROLE_NOT_FOUND));

			User user = userMapper.toEntity(request);

			String encryptedPassword = passwordEncoder.encode(request.getPassword());
			user.setPassword(encryptedPassword);

			user.getRoles().add(customerRole);

			User savedUser = userRepository.save(user);

			logger.info("User registered successfully. correlationId={}, userId={}", correlationId, savedUser.getId());

			return userMapper.toRegisterResponse(savedUser);

		} catch (AuthException exception) {
			throw exception;

		} catch (DataIntegrityViolationException exception) {
			logger.error("Database constraint error during registration. correlationId={}", correlationId, exception);

			throw new AuthException(AuthErrorCode.USER_REGISTRATION_FAILED);

		} catch (Exception exception) {
			logger.error("Unexpected error during registration. correlationId={}", correlationId, exception);

			throw new AuthException(AuthErrorCode.USER_REGISTRATION_FAILED);
		}
	}

	@Override
	@Transactional
	public LoginResponse login(LoginRequest request, String correlationId) {
		logger.info("Login request received. correlationId={}, username={}", correlationId, request.getUsername());

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

		validateUserCanLogin(user);

		boolean passwordMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
		if (!passwordMatched) {
			logger.warn("Invalid password. correlationId={}, username={}", correlationId, request.getUsername());
			throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
		}

		try {
			String accessToken = jwtService.generateAccessToken(user);
			String refreshTokenValue = UUID.randomUUID().toString();
			saveRefreshToken(user, refreshTokenValue);
			user.setLastLogin(LocalDateTime.now());
			userRepository.save(user);
			List<String> roles = user.getRoles().stream().map(Role::getRoleName).toList();
			
			LoginResponse response = new LoginResponse();
			response.setAccessToken(accessToken);
			response.setRefreshToken(refreshTokenValue);
			response.setTokenType("Bearer");
			response.setExpiresIn(jwtService.getAccessTokenExpiryInSeconds());
			response.setUserId(user.getId());
			response.setUsername(user.getUsername());
			response.setRoles(roles);
			response.setLoginTime(LocalDateTime.now());
			logger.info("Login successful. correlationId={}, userId={}", correlationId, user.getId());
			return response;
		} catch (Exception exception) {
			logger.error("Token generation failed. correlationId={}, username={}", correlationId, request.getUsername(),
					exception);
			throw new AuthException(AuthErrorCode.TOKEN_GENERATION_FAILED);
		}
	}

	private void validateRegisterRequest(RegisterRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new AuthException(AuthErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
		}
		passwordValidator.validate(request.getPassword());
		validateMobile(request.getMobile());
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_REGISTERED);
		}
		if (userRepository.existsByMobile(request.getMobile())) {
			throw new AuthException(AuthErrorCode.MOBILE_ALREADY_REGISTERED);
		}
	}

	private void validateMobile(String mobile) {
		if (mobile == null || mobile.length() != 10) {
			throw new AuthException(AuthErrorCode.INVALID_MOBILE_NUMBER);
		}
		for (char ch : mobile.toCharArray()) {
			if (!Character.isDigit(ch)) {
				throw new AuthException(AuthErrorCode.INVALID_MOBILE_NUMBER);
			}
		}
	}

	private void validateUserCanLogin(User user) {
		if (Boolean.TRUE.equals(user.getAccountLocked())) {
			throw new AuthException(AuthErrorCode.USER_ACCOUNT_LOCKED);
		}
		if (Boolean.FALSE.equals(user.getEnabled())) {
			throw new AuthException(AuthErrorCode.USER_ACCOUNT_DISABLED);
		}
		if (UserStatus.INACTIVE.equals(user.getStatus())) {
			throw new AuthException(AuthErrorCode.USER_ACCOUNT_INACTIVE);
		}
		if (UserStatus.DISABLED.equals(user.getStatus())) {
			throw new AuthException(AuthErrorCode.USER_ACCOUNT_DISABLED);
		}
		if (UserStatus.LOCKED.equals(user.getStatus())) {
			throw new AuthException(AuthErrorCode.USER_ACCOUNT_LOCKED);
		}
	}

	@Override
	@Transactional
	public RefreshTokenResponse refreshToken(RefreshTokenRequest request, String correlationId) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
				.orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_INVALID));

		if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
			throw new AuthException(AuthErrorCode.REFRESH_TOKEN_REVOKED);
		}
		if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new AuthException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
		}

		User user = refreshToken.getUser();

		String newAccessToken = jwtService.generateAccessToken(user);
		refreshToken.setRevoked(true);
		refreshTokenRepository.save(refreshToken);
		String newRefreshToken = UUID.randomUUID().toString();
		saveRefreshToken(user, newRefreshToken);
		RefreshTokenResponse response = new RefreshTokenResponse();
		response.setAccessToken(newAccessToken);
		response.setRefreshToken(newRefreshToken);
		response.setExpiresIn(jwtService.getAccessTokenExpiryInSeconds());

		return response;
	}

	private void saveRefreshToken(User user, String refreshTokenValue) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(refreshTokenValue);
		refreshToken.setUser(user);
		refreshToken.setRevoked(false);
		refreshToken.setCreatedAt(LocalDateTime.now());
		LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(refreshTokenExpiryMs / 1000);
		refreshToken.setExpiryDate(expiryDate);
		refreshTokenRepository.save(refreshToken);
	}
}
