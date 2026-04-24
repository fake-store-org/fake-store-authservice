package se.jensen.johanna.fakestoreapi.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.RefreshToken;
import se.jensen.johanna.fakestoreapi.repository.RefreshTokenRepository;
import se.jensen.johanna.fakestoreapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  @Value("${app.cookie.refresh-expiration-seconds}")
  private long refreshExpirationSeconds;

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshToken createRefreshToken(UUID userId) {
    AppUser user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    refreshTokenRepository.deleteByUser(user);
    refreshTokenRepository.flush();
    RefreshToken newRefreshToken = RefreshToken.create(user, refreshExpirationSeconds);
    return refreshTokenRepository.save(newRefreshToken);
  }

  public RefreshToken findByToken(String token) {
    return refreshTokenRepository.findByToken(token).orElseThrow(IllegalArgumentException::new);
  }

  public RefreshToken verifyExpiration(RefreshToken refreshToken) {
    if (refreshToken.isExpired()) {
      deleteRefreshToken(refreshToken);
      throw new IllegalArgumentException("Refresh token has expired. Please login again.");
    }
    return refreshToken;

  }

  @Transactional
  public RefreshToken rotateRefreshToken(RefreshToken oldRefreshToken) {
    AppUser user = oldRefreshToken.getUser();
    deleteRefreshToken(oldRefreshToken);
    refreshTokenRepository.flush();
    return refreshTokenRepository.save(RefreshToken.create(user, refreshExpirationSeconds));
  }

  public void deleteRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.delete(refreshToken);
  }

}
