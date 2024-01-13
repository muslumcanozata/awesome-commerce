package com.commerce.user.model.entity;

import com.commerce.user.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}