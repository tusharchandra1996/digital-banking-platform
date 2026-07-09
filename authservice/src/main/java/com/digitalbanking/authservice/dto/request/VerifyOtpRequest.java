package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {

    private String referenceNo;

    private String otp;
}