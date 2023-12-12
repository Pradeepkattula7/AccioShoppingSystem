package com.ShoppingSystem.AccioShopSystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int productId;

    String productName;

    String category;

    String description;

    String quantity;

    int price;

    @ManyToOne
    Order order;
}
