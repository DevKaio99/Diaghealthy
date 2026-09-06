package com.fiap.diaghealthy.domain.entities;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    protected UUID id;
    protected String name;
    protected String email;
    protected String password;
    protected LocalDateTime dateLastUpdate;
    protected LocalDateTime createdAt;
    protected boolean isActive;
    protected Role role;

    public User(String name, String email, String password, Role role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateLastUpdate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
        this.role = role;

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar em branco");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (password == null || password.length()<6) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 6 caracteres");
        }

    }

    public static User reconstitute (
            UUID id,
            String name,
            String email,
            String password,
            LocalDateTime dateLastUpdate,
            LocalDateTime createdAt,
            boolean isActive,
            Role role) {

        User user = new User(name, email, password, role);

        user.id = id;
        user.dateLastUpdate = dateLastUpdate;
        user.createdAt = createdAt;
        user.isActive = isActive;

        return user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(LocalDateTime dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




