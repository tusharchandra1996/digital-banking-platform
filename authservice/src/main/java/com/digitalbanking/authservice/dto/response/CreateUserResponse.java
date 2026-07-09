package com.digitalbanking.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {

    private Long userId;

    private String temporaryPassword;

    private String status;
}
