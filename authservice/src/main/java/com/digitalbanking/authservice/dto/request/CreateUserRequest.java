package com.digitalbanking.authservice.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;

    private String email;

    private String mobile;

    private String firstName;

    private String lastName;

    private List<String> roles;
}
