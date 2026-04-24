package se.jensen.johanna.fakestoreapi.dto;

import java.util.UUID;
import se.jensen.johanna.fakestoreapi.model.Role;

public record LoginResponse(
    String accessToken,
    UUID userId,
    Role role,
    String email
) {

}
