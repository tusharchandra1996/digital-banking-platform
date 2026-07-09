package com.digitalbanking.authservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.RegisterResponse;
import com.digitalbanking.authservice.entity.Role;
import com.digitalbanking.authservice.entity.User;
import com.digitalbanking.authservice.exception.AuthErrorCode;
import com.digitalbanking.authservice.exception.AuthException;
import com.digitalbanking.authservice.mapper.UserMapper;
import com.digitalbanking.authservice.repository.RoleRepository;
import com.digitalbanking.authservice.repository.UserRepository;
import com.digitalbanking.authservice.service.AuthService;
import com.digitalbanking.authservice.validator.PasswordValidator;

@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private static final String DEFAULT_CUSTOMER_ROLE = "ROLE_CUSTOMER";

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final PasswordValidator passwordValidator;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, UserMapper userMapper, PasswordValidator passwordValidator) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.passwordValidator = passwordValidator;
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
}
