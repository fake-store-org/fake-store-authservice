package se.jensen.johanna.fakestoreapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.johanna.fakestoreapi.dto.RegisterUserRequest;
import se.jensen.johanna.fakestoreapi.exception.UserAlreadyExistsException;
import se.jensen.johanna.fakestoreapi.mapper.UserMapper;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.Role;
import se.jensen.johanna.fakestoreapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private UserMapper userMapper;

  private RegisterUserRequest request;

  @BeforeEach
  void setUp() {
    request = new RegisterUserRequest("test@test.com", "Password1",
        "Password1");
  }

  @Test
  void createUser_ShouldCreateUserAndSave() {

    when(userRepository.existsByEmail(request.email())).thenReturn(false);
    when(passwordEncoder.encode(request.password())).thenReturn("hashedPw");
    when(userRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));

    AppUser result = userService.createUser(request);

    assertThat(result.getEmail()).isEqualTo("test@test.com");
    assertThat(result.getRole()).isEqualTo(Role.USER);
    verify(userRepository).save(any(AppUser.class));
  }

  @Test
  void createUser_ShouldThrowExceptionIfUserAlreadyExists() {

    when(userRepository.existsByEmail(request.email())).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(request));
    verify(userRepository, never()).save(any(AppUser.class));

  }

  @Test
  void updateAddress() {
  }
}