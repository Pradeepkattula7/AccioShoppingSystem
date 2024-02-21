package com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO;


import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaceOrderDTO {

    List<Integer> productIds;

    int userId;
}
