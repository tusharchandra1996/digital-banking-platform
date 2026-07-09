package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateTokenResponse {

    private boolean valid;

    private Long userId;

    private String username;

    private List<String> roles;

    private LocalDateTime expiryTime;
}
