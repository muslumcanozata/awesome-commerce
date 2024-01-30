package com.commerce.user.model.dto;

import com.commerce.user.model.enums.UserRole;

public record UserDTO(Long id, String username, String password, String email, String address, UserRole role) {}