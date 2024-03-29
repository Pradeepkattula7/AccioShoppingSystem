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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    @ManyToOne
    Users users;

    Date deleiveryDate;

    int totalOrderPrice;

    @ManyToMany
    List<Product> productList;

    boolean isDelivered;

    int totalOrderItems;

}
