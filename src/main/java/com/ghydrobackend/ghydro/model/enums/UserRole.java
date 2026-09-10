package com.ghydrobackend.ghydro.model.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    TECNICO("ROLE_TECNICO"),
    PRODUTOR("ROLE_PRODUTOR");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}