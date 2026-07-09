package com.digitalbanking.authservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignRoleResponse {

    private Long userId;

    private List<String> assignedRoles;

    private String status;
}
