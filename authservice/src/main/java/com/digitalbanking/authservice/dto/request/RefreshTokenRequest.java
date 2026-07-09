package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String refreshToken;

    private String deviceId;
}
