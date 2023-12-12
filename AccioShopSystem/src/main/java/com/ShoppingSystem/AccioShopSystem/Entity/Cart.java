package com.ShoppingSystem.AccioShopSystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cartId;

    @OneToOne
    Users users;


    int totalPrice;

    int totalItems;


    @OneToMany
    List<Product> productList;
}
