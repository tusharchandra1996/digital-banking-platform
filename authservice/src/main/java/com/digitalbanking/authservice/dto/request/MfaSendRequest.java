package com.digitalbanking.authservice.dto.request;

import lombok.Data;

@Data
public class MfaSendRequest {

    private String username;

    private String channel;
}
