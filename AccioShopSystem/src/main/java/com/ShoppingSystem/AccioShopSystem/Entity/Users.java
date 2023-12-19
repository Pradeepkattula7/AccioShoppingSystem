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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;

    @Column(unique = true)
    String userName;

    String password;

    String role;

    String address;

    @Column(unique = true)
    String emailId;

    @Column(length = 10,unique = true)
    Long phoneNumber;

    @OneToOne
    Cart cart;

    @OneToMany
    List<Orders> ordersList;

    boolean isAdminApproved;
}
