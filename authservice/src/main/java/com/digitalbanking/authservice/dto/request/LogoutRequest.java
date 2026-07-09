package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class LogoutRequest {

    private String refreshToken;

    private String deviceId;
}