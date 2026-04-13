package se.jensen.johanna.fakestoreapi.dto;

public record RefreshResult(
    RefreshResponse refreshResponse,
    String refreshToken
) {

}
