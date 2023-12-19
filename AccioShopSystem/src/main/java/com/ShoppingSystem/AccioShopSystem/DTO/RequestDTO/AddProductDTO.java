package com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddProductDTO {

    String userName;

    String productName;

    String category;

    String description;

    String quantity;

    int price;

}
