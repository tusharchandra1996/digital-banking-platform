package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaSendResponse {

    private String referenceNo;

    private String channel;

    private LocalDateTime expiryTime;
}
