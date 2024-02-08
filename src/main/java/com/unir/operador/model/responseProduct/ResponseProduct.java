package com.unir.operador.model.responseProduct;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ResponseProduct {
    private Long id;
    private String name;
    private String description;
    private String currency;
    private BigDecimal price;
    private String urlImage;
    private Integer score;
}
