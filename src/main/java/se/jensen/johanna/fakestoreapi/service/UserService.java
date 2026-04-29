package se.jensen.johanna.fakestoreapi.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johanna.fakestoreapi.dto.AddressRequest;
import se.jensen.johanna.fakestoreapi.dto.AddressResponse;
import se.jensen.johanna.fakestoreapi.dto.RegisterUserRequest;
import se.jensen.johanna.fakestoreapi.exception.DomainStateException;
import se.jensen.johanna.fakestoreapi.exception.UserAlreadyExistsException;
import se.jensen.johanna.fakestoreapi.mapper.UserMapper;
import se.jensen.johanna.fakestoreapi.model.Address;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.Role;
import se.jensen.johanna.fakestoreapi.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @Transactional
  public AppUser createUser(RegisterUserRequest request) {
    log.info("Creating new user: {}", request.email());
    if (userRepository.existsByEmail(request.email())) {
      log.warn("User attempting to register already exists: {}", request.email());
      throw new UserAlreadyExistsException("This email is already registered.");
    }
    String hashedPw = passwordEncoder.encode(request.password());
    AppUser user = AppUser.create(request.email(), hashedPw, Role.USER);
    log.info("New user created: {}", user.getEmail());
    return userRepository.save(user);
  }

  @Transactional
  public AddressResponse updateAddress(Jwt jwt, AddressRequest request) {
    log.info("Updating address for user: {}", jwt.getSubject());
    UUID userId = UUID.fromString(jwt.getSubject());
    AppUser user = userRepository.findById(userId)
        .orElseThrow(() -> new DomainStateException("Unexpected error. User not found"));
    Address address = userMapper.toAddress(request);
    user.updateAddress(address);
    userRepository.save(user);
    log.info("Address updated for user: {} , {}", user.getEmail(), user.getUserId());
    return userMapper.toAddressResponse(address);
  }

}
