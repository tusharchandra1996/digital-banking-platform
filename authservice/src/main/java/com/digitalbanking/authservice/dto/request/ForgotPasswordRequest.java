package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String username;

    private String email;
}
