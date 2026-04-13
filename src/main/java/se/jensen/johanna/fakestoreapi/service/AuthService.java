package se.jensen.johanna.fakestoreapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import se.jensen.johanna.fakestoreapi.dto.AuthResult;
import se.jensen.johanna.fakestoreapi.dto.LoginRequest;
import se.jensen.johanna.fakestoreapi.dto.LoginResponse;
import se.jensen.johanna.fakestoreapi.dto.RefreshResponse;
import se.jensen.johanna.fakestoreapi.dto.RefreshResult;
import se.jensen.johanna.fakestoreapi.dto.RegisterUserRequest;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.RefreshToken;
import se.jensen.johanna.fakestoreapi.security.MyUserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public AuthResult login(LoginRequest loginRequest) {
    Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(),
        loginRequest.password());
    Authentication authenticatedAuth = authenticationManager.authenticate(auth);
    MyUserDetails userDetails = (MyUserDetails) authenticatedAuth.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
    String accessToken = tokenService.generateToken(userDetails.getUserId(), roles,
        userDetails.getUsername());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());
    return new AuthResult(
        new LoginResponse(
            accessToken,
            userDetails.getUserId(),
            userDetails.getRole(),
            userDetails.getUsername()
        ),
        refreshToken.getToken()
    );
  }

  public AuthResult register(RegisterUserRequest request) {
    AppUser user = userService.createUser(request);
    return new AuthResult(
        new LoginResponse(
            tokenService.generateToken(user.getUserId(),
                List.of("ROLE_" + user.getRole().name()),
                user.getEmail()),
            user.getUserId(),
            user.getRole(),
            user.getEmail()),
        refreshTokenService.createRefreshToken(user.getUserId()).getToken());


  }

  public RefreshResult refresh(String oldRefreshToken) {
    RefreshToken oldToken = refreshTokenService.findByToken(oldRefreshToken);
    refreshTokenService.verifyExpiration(oldToken);
    RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(oldToken);
    AppUser user = newRefreshToken.getUser();
    String newAccessToken = tokenService.generateToken(user.getUserId(),
        List.of("ROLE_" + user.getRole().name()), user.getEmail());
    return new RefreshResult(new RefreshResponse(newAccessToken), newRefreshToken.getToken());


  }

  public void logout(String refreshToken) {
    refreshTokenService.deleteRefreshToken(refreshTokenService.findByToken(refreshToken));
  }

}
