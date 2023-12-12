package com.ShoppingSystem.AccioShopSystem.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    String orderName;

    @ManyToOne
    Users users;

    Date deleiveryDate;


    int totalPrice;

    @OneToMany
    List<Product> productList;

    boolean isDelivered;

    int totalOrderItems;

}
