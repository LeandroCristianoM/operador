package com.unir.operador.model.pojo;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "reserve")
public class Reserve {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Long id;
	@Basic
	@Column(name = "product_id")
	private Integer productId;
	@Basic
	@Column(name = "reserve_date")
	private LocalDate reserveDate;
	@Basic
	@Column(name = "quantity")
	private Integer quantity;

}

