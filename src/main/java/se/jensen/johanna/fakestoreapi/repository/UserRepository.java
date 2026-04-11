package se.jensen.johanna.fakestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johanna.fakestoreapi.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

  Boolean existsByEmail(String email);
}
