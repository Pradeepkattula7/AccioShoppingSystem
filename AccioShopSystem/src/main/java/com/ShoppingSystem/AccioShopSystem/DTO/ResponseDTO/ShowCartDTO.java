package com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO;

import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShowCartDTO {

    List<Product> productList;

    int totalPrice;

    int totalItems;
}
