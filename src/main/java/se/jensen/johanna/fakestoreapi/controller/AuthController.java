package se.jensen.johanna.fakestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.johanna.fakestoreapi.controller.webutils.CookieFactory;
import se.jensen.johanna.fakestoreapi.dto.AuthResult;
import se.jensen.johanna.fakestoreapi.dto.LoginRequest;
import se.jensen.johanna.fakestoreapi.dto.LoginResponse;
import se.jensen.johanna.fakestoreapi.dto.RegisterUserRequest;
import se.jensen.johanna.fakestoreapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final CookieFactory cookieFactory;
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    AuthResult result = authService.login(request);
    ResponseCookie responseCookie = cookieFactory.createRefreshTokenCookie(result.refreshToken());
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .body(result.loginResponse());
  }

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterUserRequest request) {
    AuthResult result = authService.register(request);
    ResponseCookie responseCookie = cookieFactory.createRefreshTokenCookie(result.refreshToken());
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .body(result.loginResponse());
  }


}
