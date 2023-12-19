package com.ShoppingSystem.AccioShopSystem.Service;

import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.AddProductDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import com.ShoppingSystem.AccioShopSystem.Exception.WrongAccess;
import com.ShoppingSystem.AccioShopSystem.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;


    @Autowired
    UsersService usersService;

    public String addProduct(AddProductDTO addProductDTO){

        if(usersService.isAdmin(addProductDTO.getUserName())) {

            Product product = new Product();

            product.setProductName(addProductDTO.getProductName());
            product.setCategory(addProductDTO.getCategory());
            product.setDescription(addProductDTO.getDescription());
            product.setQuantity(addProductDTO.getQuantity());
            product.setPrice(addProductDTO.getPrice());

            productRepo.save(product);
        }
        else{
            throw new WrongAccess("user not have admin access");
        }

        return "successfully done";
    }

    public List<Product> getAllProducts(String userName){

        if(usersService.isAdmin(userName)){
          return   productRepo.findAll();
        }
        else{
            throw new WrongAccess("not possible");
        }
    }

    public Product getProductById(int productId){

        return productRepo.findById(productId);
    }
}
