package se.jensen.johanna.fakestoreapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "hashed_pw", nullable = false)
  private String hashedPw;

  @Embedded
  private Address address;


  private Instant createdAt;

  private Instant updatedAt;


  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
  }

  public static AppUser create(String email, String hashedPw, Role role) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email required.");
    }
    if (hashedPw == null || hashedPw.isBlank()) {
      throw new IllegalArgumentException("Password required.");
    }
    if (role == null) {
      throw new IllegalArgumentException("Role required.");
    }
    return AppUser.builder().email(email).hashedPw(hashedPw).role(role).build();

  }

  public void updateAddress(Address address) {
    this.address = address;
  }


}
