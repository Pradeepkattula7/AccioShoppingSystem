package com.ShoppingSystem.AccioShopSystem.Service;

import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.PlaceOrderDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.PlaceOrderResponseDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Orders;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import com.ShoppingSystem.AccioShopSystem.Exception.UserNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Repository.OrdersRepo;
import com.ShoppingSystem.AccioShopSystem.Repository.UsersRepo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {


    @Autowired
    UsersService usersService;

    @Autowired
    ProductService productService;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    OrdersRepo ordersRepo;


    public PlaceOrderResponseDTO placeOrder(PlaceOrderDTO placeOrderDTO){

        List<Integer> productIds=placeOrderDTO.getProductIds();

        int userId=placeOrderDTO.getUserId();


        if(usersService.getUserById(userId)==null){
            throw new UserNotFoundException("user not found");
        }

        List<Product> productList=new ArrayList<>();

        for(int pId:productIds){

            Product product=productService.getProductById(pId);

            productList.add(product);
        }

        Orders orders=new Orders();

        Date date=new Date();

        date=DateUtils.addDays(date,7);

        orders.setDeleiveryDate(date);
        orders.setDelivered(false);
        orders.setTotalOrderItems(productList.size());

        int totalPrice=0;

        for(Product product:productList){
            totalPrice+=product.getPrice();
        }
        orders.setTotalOrderPrice(totalPrice);
        orders.setUsers(usersService.getUserById(userId));

        ordersRepo.save(orders);

        for(Product product:productList){
            ordersRepo.insert(orders.getOrderId(),product.getProductId());
        }

        usersRepo.insert(userId,orders.getOrderId());

        PlaceOrderResponseDTO placeOrderResponseDTO=new PlaceOrderResponseDTO();

        placeOrderResponseDTO.setProductList(productList);
        placeOrderResponseDTO.setTotalItems(productList.size());
        placeOrderResponseDTO.setTotalPrice(totalPrice);


        return placeOrderResponseDTO;

    }


    public Orders getOrder(int orderId){

        return ordersRepo.getOrder(orderId);
    }
}
