package com.digitalbanking.authservice.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class AssignRoleRequest {

    private List<String> roles;
}
