package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {

    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private List<String> roles;

    private String status;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;
}
