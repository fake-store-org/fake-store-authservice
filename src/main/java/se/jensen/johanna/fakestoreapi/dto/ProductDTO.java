package se.jensen.johanna.fakestoreapi.dto;

import java.math.BigDecimal;

public record ProductDTO(
    Long id,
    String title,
    BigDecimal price,
    String description,
    String category,
    String image

) {


}
