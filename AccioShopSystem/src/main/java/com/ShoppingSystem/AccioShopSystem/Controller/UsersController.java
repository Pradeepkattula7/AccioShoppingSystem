package com.ShoppingSystem.AccioShopSystem.Controller;

import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.AddUsersDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.LoginRequestDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.PlaceOrderDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.LoginResponseDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.PlaceOrderResponseDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.ShowCartDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.UserOrdersResponseDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Cart;
import com.ShoppingSystem.AccioShopSystem.Entity.Orders;
import com.ShoppingSystem.AccioShopSystem.Entity.Users;
import com.ShoppingSystem.AccioShopSystem.Exception.*;
import com.ShoppingSystem.AccioShopSystem.Service.CartService;
import com.ShoppingSystem.AccioShopSystem.Service.OrdersService;
import com.ShoppingSystem.AccioShopSystem.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.LockInfo;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    CartService cartService;

    @Autowired
    OrdersService ordersService;


    @PostMapping("/shop/signup")
    public ResponseEntity signup(@RequestBody AddUsersDTO addUsersDTO){

        try {
            Users user = usersService.signUp(addUsersDTO);
            return new ResponseEntity(user,HttpStatus.CREATED);
        }
        catch (AdminNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shop/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){

        try{
            LoginResponseDTO loginResponseDTO=usersService.logIn(loginRequestDTO);
            return  new ResponseEntity(loginResponseDTO,HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (WrongCredentialsException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/shop/addProductInCart")
    public ResponseEntity addProductIntoCart(@RequestParam int productId,
                                    @RequestParam int userId){

        try{
            cartService.addProductsInCart(productId,userId);
            return new ResponseEntity("product added successfully",HttpStatus.ACCEPTED);
        }
        catch (ProductNotFoundException e){
              return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/shop/userCart/{userId}")
    public ResponseEntity userCart(@PathVariable int userId){

        try{
           ShowCartDTO showCartDTO= cartService.showUserCart(userId);
            return new ResponseEntity(showCartDTO,HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (WrongAccess e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/shop/removeProductInCart")
    public ResponseEntity removeProduct(@RequestParam int userId,
                                        @RequestParam int productId){
        try{
            cartService.removeItems(userId,productId);
            return new ResponseEntity("removed succesfully",HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (WrongAccess e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/shop/user/placeOrder")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){

        try{
            PlaceOrderResponseDTO placeOrderResponseDTO=ordersService.placeOrder(placeOrderDTO);
            return new ResponseEntity(placeOrderResponseDTO,HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shop/userOrders/{userId}")
    public  ResponseEntity userOrder( @PathVariable int userId){
        try{
            UserOrdersResponseDTO userOrdersResponseDTO =usersService.userOrders(userId);
            return new ResponseEntity(userOrdersResponseDTO,HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (WrongAccess e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }
}
