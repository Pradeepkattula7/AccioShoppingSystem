package com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO;

import com.ShoppingSystem.AccioShopSystem.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUsersDTO {


    String userName;

    String password;

    String address;

    String emailId;

    Long phoneNumber;

    Role role;
}
