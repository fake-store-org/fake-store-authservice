package se.jensen.johanna.fakestoreapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class FakeStoreApiApplicationTests {

  @MockitoBean
  private PasswordEncoder passwordEncoder;

  @Test
  void contextLoads() {
  }

}
