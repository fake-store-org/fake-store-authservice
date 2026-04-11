package se.jensen.johanna.fakestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.johanna.fakestoreapi.dto.AddressRequest;
import se.jensen.johanna.fakestoreapi.dto.AddressResponse;
import se.jensen.johanna.fakestoreapi.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  public ResponseEntity<AddressResponse> updateAddress(@AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid AddressRequest request) {
    Long userId = Long.parseLong(jwt.getSubject());
    return ResponseEntity.ok(userService.updateAddress(userId, request));
  }
}
