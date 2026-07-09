package com.digitalbanking.authservice.mapper;

import org.springframework.stereotype.Component;

import com.digitalbanking.authservice.dto.request.RegisterRequest;
import com.digitalbanking.authservice.dto.response.RegisterResponse;
import com.digitalbanking.authservice.entity.User;
import com.digitalbanking.authservice.entity.UserStatus;

@Component
public class UserMapper {

	public User toEntity(RegisterRequest request) {
		User user = new User();

		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setMobile(request.getMobile());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setCountryCode(request.getCountryCode());
		user.setStatus(UserStatus.ACTIVE);
		user.setEnabled(true);
		user.setAccountLocked(false);
		user.setFailedAttempts(0);

		return user;
	}

	public RegisterResponse toRegisterResponse(User user) {
		RegisterResponse response = new RegisterResponse();

		response.setUserId(user.getId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setMobile(user.getMobile());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setStatus(user.getStatus().name());
		response.setCreatedAt(user.getCreatedAt());

		return response;
	}
}
