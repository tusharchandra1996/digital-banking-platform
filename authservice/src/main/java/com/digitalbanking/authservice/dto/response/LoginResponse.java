package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

    private String tokenType;

    private Long userId;

    private String username;

    private List<String> roles;

    private LocalDateTime loginTime;
}
