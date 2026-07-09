package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpResponse {

    private boolean verified;

    private LocalDateTime verifiedAt;
}
