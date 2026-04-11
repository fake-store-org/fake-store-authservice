package se.jensen.johanna.fakestoreapi.dto;

public record AuthResult(
    LoginResponse loginResponse,
    String refreshToken
) {

}
