package se.jensen.johanna.fakestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johanna.fakestoreapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  

}
