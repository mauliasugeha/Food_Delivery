package com.example.order_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Email(message = "Email tidak valid")
    @NotBlank(message = "Email wajib diisi")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    private String password;

    // getter setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}