package com.digitalbanking.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaVerifyResponse {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;
}