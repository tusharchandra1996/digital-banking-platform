package com.digitalbanking.authservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlockAccountResponse {

    private String status;

    private String message;

    private LocalDateTime unlockedAt;
}
