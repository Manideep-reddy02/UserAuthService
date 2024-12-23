package com.example.userauthservice.Security.Services;

import com.example.userauthservice.models.Role;

public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    private String role;

    public GrantedAuthority(Role role) {
        this.role = role.getName();
    }
    @Override
    public String getAuthority() {
        return role;
    }
}
