package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class UnlockAccountRequest {

    private String username;

    private String unlockCode;
}
