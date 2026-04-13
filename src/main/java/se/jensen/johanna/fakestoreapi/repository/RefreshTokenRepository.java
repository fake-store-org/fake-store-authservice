package se.jensen.johanna.fakestoreapi.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import se.jensen.johanna.fakestoreapi.model.AppUser;
import se.jensen.johanna.fakestoreapi.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


  @Transactional
  @Modifying
  void deleteByUser(AppUser user);

  Optional<RefreshToken> findByToken(String token);

}
