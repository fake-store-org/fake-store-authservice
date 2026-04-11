package se.jensen.johanna.fakestoreapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import se.jensen.johanna.fakestoreapi.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


  @Transactional
  @Modifying
  void deleteByUser_UserId(Long userId);

}
