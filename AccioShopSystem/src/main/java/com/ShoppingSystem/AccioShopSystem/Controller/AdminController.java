package com.ShoppingSystem.AccioShopSystem.Controller;

import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.AddProductDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import com.ShoppingSystem.AccioShopSystem.Exception.UserNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Exception.WrongAccess;
import com.ShoppingSystem.AccioShopSystem.Service.ProductService;
import com.ShoppingSystem.AccioShopSystem.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    ProductService productService;


    @Autowired
    UsersService usersService;


    @PostMapping("/shop/addProduct")
    public  ResponseEntity addProduct(@RequestBody AddProductDTO addProductDTO){

        try{
           String msg= productService.addProduct(addProductDTO);
           return new ResponseEntity(msg,HttpStatus.CREATED);
        }
        catch (WrongAccess e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/shop/setAsAdmin")
    public ResponseEntity setAsAdmin(@RequestParam String userName,
                                     @RequestParam int userId){
        try {
            usersService.setAsAdmin(userName, userId);
            return new ResponseEntity("done",HttpStatus.OK);
        }
        catch (WrongAccess e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shop/getAllProducts")
    public ResponseEntity getAll(@RequestParam String userName){
        try {
            List<Product> list = productService.getAllProducts(userName);
            return new ResponseEntity(list, HttpStatus.OK);
        }
        catch (WrongAccess e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
