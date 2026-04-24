package se.jensen.johanna.fakestoreapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JwtEncoder jwtEncoder;
  @Value("${jwt.expiration-minutes}")
  private Long jwtExpirationMinutes;

  public String generateToken(UUID userId, List<String> roles, String email) {
    Instant now = Instant.now();
    Instant expiresAt = now.plus(jwtExpirationMinutes, ChronoUnit.MINUTES);

    JwtClaimsSet claimsSet = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(expiresAt)
        .subject(userId.toString())
        .claim("email", email)
        .claim("scope", roles)
        .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

  }

}
