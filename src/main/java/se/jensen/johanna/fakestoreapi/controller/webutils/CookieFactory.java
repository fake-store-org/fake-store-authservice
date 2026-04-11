package se.jensen.johanna.fakestoreapi.controller.webutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieFactory {

  @Value("${app.cookie.refresh-expiration-seconds}")
  private long refreshExpirationSeconds;

  @Value("${app.cookie.same-site}")
  private String sameSite;

  @Value("${app.cookie-secure}")
  private Boolean cookieSecure;

  public ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .maxAge(refreshExpirationSeconds)
        .httpOnly(true)
        .path("/")
        .sameSite(sameSite)
        .secure(cookieSecure)
        .build();
  }

  public ResponseCookie getCleanResponseCookie() {
    return ResponseCookie.from("refreshToken", "")
        .maxAge(0)
        .httpOnly(true)
        .path("/")
        .sameSite(sameSite)
        .secure(cookieSecure)
        .build();

  }


}
