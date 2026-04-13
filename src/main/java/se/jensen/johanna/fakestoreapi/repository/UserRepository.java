package se.jensen.johanna.fakestoreapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johanna.fakestoreapi.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

  Boolean existsByEmail(String email);

  Optional<AppUser> findByEmail(String email);
}
