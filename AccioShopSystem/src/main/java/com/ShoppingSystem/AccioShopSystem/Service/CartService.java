package com.ShoppingSystem.AccioShopSystem.Service;

import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.ShowCartDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Cart;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import com.ShoppingSystem.AccioShopSystem.Exception.ProductNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Exception.UserNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Exception.WrongAccess;
import com.ShoppingSystem.AccioShopSystem.Repository.CartRepo;
import com.ShoppingSystem.AccioShopSystem.Repository.UsersRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductService productService;

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepo usersRepo;

    public  void  addProductsInCart(int productId,int userId){

        if(productService.getProductById(productId)==null){

            throw new ProductNotFoundException("product not found");
        }
        if(usersService.getUserById(userId)==null){

            throw new UserNotFoundException("user not found");
        }

        int cartId=cartRepo.getCartByUserID(userId);


        Product product=productService.getProductById(productId);

        int CartTotalPrice=cartRepo.cartPrice(cartId)+product.getPrice();

        int CartTotalItems=cartRepo.cartItems(cartId)+1;

        cartRepo.insert(cartId,productId);

        cartRepo.updateItems(cartId,CartTotalItems);

        cartRepo.updatePrice(cartId,CartTotalPrice);

    }

    public  void createCart(Cart c){
        cartRepo.save(c);
    }

    public ShowCartDTO showUserCart(int userId){

        if(usersRepo.findByUserId(userId)==null){

            throw new UserNotFoundException("user not found");
        }

        if(usersRepo.userRole(userId).equals("Admin")){
            throw new WrongAccess("user does not have cart");
        }
        int cartId=cartRepo.getCartByUserID(userId);

        List<Object []> productDb=cartRepo.showCart(cartId);

        List<Product> list=new ArrayList<>();

        for(Object [] obj:productDb){
            Product p=productService.getProductById(Integer.parseInt(obj[0].toString()));
            list.add(p);
        }

        ShowCartDTO showCartDTO=new ShowCartDTO();

        showCartDTO.setProductList(list);
        showCartDTO.setTotalItems(cartRepo.cartItems(cartId));
        showCartDTO.setTotalPrice(cartRepo.cartPrice(cartId));

        return showCartDTO;
    }


    public  void removeItems(int userId,int productId){

        if(usersRepo.findByUserId(userId)==null){

            throw new UserNotFoundException("user not found");
        }

        if(usersRepo.userRole(userId).equals("Admin")){
            throw new WrongAccess("user does not have cart");
        }

        if(productService.getProductById(productId)==null){
            throw new WrongAccess("product not found in cart");
        }

        Product product=productService.getProductById(productId);

        int cartId=cartRepo.getCartByUserID(userId);

        cartRepo.updatePrice(cartId,cartRepo.cartPrice(cartId)-product.getPrice());

        cartRepo.updateItems(cartId,cartRepo.cartItems(cartId)-1);

        cartRepo.removeItems(cartId,productId);
    }
}
