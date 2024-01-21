package com.commerce.product.model.entity;

import com.commerce.product.model.enums.QuantityType;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Indexed(index = "idx_product")
public class Product extends BaseEntity implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @FullTextField(analyzer = "english")
    private String productName;
    private String description;
    private double price;
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;
    @FullTextField(analyzer = "english")
    private String category;
}