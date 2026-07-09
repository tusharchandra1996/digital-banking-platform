package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String referenceNo;

    private String otp;

    private String password;

    private String confirmPassword;
}
