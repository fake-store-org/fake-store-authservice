package se.jensen.johanna.fakestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johanna.fakestoreapi.dto.AddressRequest;
import se.jensen.johanna.fakestoreapi.dto.AddressResponse;
import se.jensen.johanna.fakestoreapi.dto.RegisterUserRequest;
import se.jensen.johanna.fakestoreapi.mapper.UserMapper;
import se.jensen.johanna.fakestoreapi.model.Address;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.Role;
import se.jensen.johanna.fakestoreapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  private final UserRepository userRepository;

  @Transactional
  public AppUser createUser(RegisterUserRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("This email is already registered");
    }
    String hashedPw = passwordEncoder.encode(request.password());
    AppUser user = AppUser.create(request.email(), hashedPw, Role.USER);
    return userRepository.save(user);

  }

  @Transactional
  public AddressResponse updateAddress(Long userId, AddressRequest request) {
    AppUser user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Address address = userMapper.toAddress(request);
    user.updateAddress(address);
    return userMapper.toAddressResponse(address);
  }

}
