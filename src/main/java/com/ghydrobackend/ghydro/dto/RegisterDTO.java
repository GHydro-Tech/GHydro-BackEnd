package com.ghydrobackend.ghydro.dto;

import com.ghydrobackend.ghydro.model.enums.UserRole;

public record RegisterDTO(String email, String senha, UserRole role) {
}