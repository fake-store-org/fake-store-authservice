package se.jensen.johanna.fakestoreapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
    @Email @NotBlank(message = "Please enter valid email.")
    String email,

    @NotBlank(message = "Password cant be empty.") @Size(min = 8, message = "Must be minimum 8 characters")
    @Size(min = 8, message = "Password needs to be atleast 8 characters long and contain one digit and one capital letter")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
        message = "Password needs to contain at least one number and one capital letter."
    )
    String password,

    @NotBlank(message = "Please confirm password")
    String confirmPassword) {

}
