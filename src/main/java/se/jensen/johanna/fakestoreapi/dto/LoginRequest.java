package se.jensen.johanna.fakestoreapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email @NotBlank(message = "Enter your email.")
    String email,

    @NotBlank(message = "Enter your password")
    String password) {

}
