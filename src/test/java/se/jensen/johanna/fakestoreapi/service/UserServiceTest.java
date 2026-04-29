package se.jensen.johanna.fakestoreapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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

  private Jwt jwt;
  private RegisterUserRequest request;

  private AppUser user;
  private UUID userId;

  @BeforeEach
  void setUp() {
    request = new RegisterUserRequest("test@test.com", "Password1",
        "Password1");
    user = AppUser.create("test@test.com", "hashedPw", Role.USER);
    userId = UUID.randomUUID();
    jwt = Jwt.withTokenValue("mock-token")
        .header("alg", "none")
        .subject(userId.toString())
        .claim("scope", "ROLE_USER")
        .build();
  }

  @Test
  void createUser_ShouldCreateUserAndSave() {
    ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
    when(userRepository.existsByEmail(request.email())).thenReturn(false);
    when(passwordEncoder.encode(request.password())).thenReturn("hashedPw");
    when(userRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));

    userService.createUser(request);

    verify(userRepository).save(userCaptor.capture());
    assertEquals(Role.USER, userCaptor.getValue().getRole());
    assertEquals("hashedPw", userCaptor.getValue().getHashedPw());
    assertEquals(request.email(), userCaptor.getValue().getEmail());
  }

  @Test
  void createUser_ShouldThrowExceptionIfUserAlreadyExists() {

    when(userRepository.existsByEmail(request.email())).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(request));
    verify(userRepository, never()).save(any(AppUser.class));

  }

  @Test
  void updateAddress_ShouldUpdateAddressAndSave() {
    AddressRequest addressRequest = new AddressRequest("firstname", "lastname", null, "street 1",
        null, "45675", "city", "country");
    AddressResponse expectedResponse = new AddressResponse(
        "firstname", "lastname", null, "street 1", null, "45675", "city", "country"
    );
    Address mockAddress = new Address("firstname", "lastname", null, "street 1", null, "45675",
        "city", "country");
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userMapper.toAddress(any(AddressRequest.class))).thenReturn(mockAddress);

    when(userMapper.toAddressResponse(any(Address.class))).thenReturn(expectedResponse);
    AddressResponse result = userService.updateAddress(jwt, addressRequest);

    verify(userRepository).save(user);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expectedResponse);

  }

  @Test
  void updateAddress_ShouldThrowDomainExceptionIfUserNotFound() {
    AddressRequest request1 = mock(AddressRequest.class);
    when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    assertThrows(DomainStateException.class, () -> userService.updateAddress(jwt, request1));

  }
}