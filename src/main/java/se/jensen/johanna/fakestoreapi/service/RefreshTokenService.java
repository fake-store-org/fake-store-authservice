package se.jensen.johanna.fakestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

  public RefreshToken createRefreshToken(Long userId) {
    AppUser appUser = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    refreshTokenRepository.deleteByUser_UserId(userId);
    refreshTokenRepository.flush();
    RefreshToken newRefreshToken = RefreshToken.create(appUser, refreshExpirationSeconds);
    refreshTokenRepository.save(newRefreshToken);
    return newRefreshToken;
  }

}
