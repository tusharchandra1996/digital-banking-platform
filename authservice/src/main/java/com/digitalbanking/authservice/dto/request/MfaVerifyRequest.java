package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class MfaVerifyRequest {

    private String referenceNo;

    private String otp;
}
