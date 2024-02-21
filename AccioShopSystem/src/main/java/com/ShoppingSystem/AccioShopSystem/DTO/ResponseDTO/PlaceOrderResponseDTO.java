package com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO;


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
public class PlaceOrderResponseDTO {

    List<Product> productList;

    int totalPrice;

    int totalItems;
}
