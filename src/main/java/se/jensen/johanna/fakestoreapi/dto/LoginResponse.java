package se.jensen.johanna.fakestoreapi.dto;

import se.jensen.johanna.fakestoreapi.model.Role;

public record LoginResponse(
    String accessToken,
    Long userId,
    Role role,
    String email
) {

}
