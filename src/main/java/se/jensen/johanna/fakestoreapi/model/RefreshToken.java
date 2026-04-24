package se.jensen.johanna.fakestoreapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class RefreshToken {

  @Id
  @GeneratedValue
  private UUID tokenId;

  @Column(nullable = false, unique = true)
  private String token;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private AppUser user;

  @Column(nullable = false)
  Instant expiryDate;

  Instant createdAt;

  public static RefreshToken create(AppUser user, long durationSeconds) {
    return RefreshToken.builder().user(user).token(UUID.randomUUID().toString())
        .expiryDate(Instant.now().plusSeconds(durationSeconds)).build();

  }

  public boolean isExpired() {
    return Instant.now().isAfter(expiryDate);
  }


}
