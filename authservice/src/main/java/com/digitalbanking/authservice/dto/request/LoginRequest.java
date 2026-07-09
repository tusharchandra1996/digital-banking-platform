package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

    private String deviceId;

    private String deviceName;

    private String ipAddress;

  
}
