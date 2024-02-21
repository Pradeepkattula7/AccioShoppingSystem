package com.ShoppingSystem.AccioShopSystem.Service;


import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.LoginRequestDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.RequestDTO.AddUsersDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.LoginResponseDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.ShowCartDTO;
import com.ShoppingSystem.AccioShopSystem.DTO.ResponseDTO.UserOrdersResponseDTO;
import com.ShoppingSystem.AccioShopSystem.Entity.Cart;
import com.ShoppingSystem.AccioShopSystem.Entity.Orders;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import com.ShoppingSystem.AccioShopSystem.Entity.Users;
import com.ShoppingSystem.AccioShopSystem.Exception.AdminNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Exception.UserNotFoundException;
import com.ShoppingSystem.AccioShopSystem.Exception.WrongAccess;
import com.ShoppingSystem.AccioShopSystem.Exception.WrongCredentialsException;
import com.ShoppingSystem.AccioShopSystem.Repository.CartRepo;
import com.ShoppingSystem.AccioShopSystem.Repository.OrdersRepo;
import com.ShoppingSystem.AccioShopSystem.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    CartService cartService;


    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrdersRepo ordersRepo;

    public  Users signUp(AddUsersDTO addUsersDTO){

        List<Object []> list=usersRepo.countOfAdmin();

        int count=Integer.parseInt(list.get(0)[0].toString());

        String role=addUsersDTO.getRole().toString();

        if(count==0 && role.equals("User")){
            throw new AdminNotFoundException("no admin present please add admin first");
        }



        Users user=new Users();

        user.setUserName(addUsersDTO.getUserName());
        user.setAddress(addUsersDTO.getAddress());
        user.setRole(addUsersDTO.getRole().toString());
        user.setEmailId(addUsersDTO.getEmailId());
        user.setPhoneNumber(addUsersDTO.getPhoneNumber());
        user.setPassword(addUsersDTO.getPassword());
        user.setAdminApproved(false);

        if(role.equals("Admin") && addUsersDTO.getPassword().equals("Accio123")){
            user.setAdminApproved(true);
        }

        if(role.equals("Admin") && !user.isAdminApproved()){
            throw new AdminNotFoundException("admin passwprd is wrong");
        }

        if(role.equals("User")){
            Cart cart=new Cart();
            cartService.createCart(cart);
            user.setCart(cart);
        }
        usersRepo.save(user);

        return user;
    }

    public LoginResponseDTO logIn(LoginRequestDTO loginRequestDTO){

        String userName=loginRequestDTO.getUserName();

       Users user= usersRepo.findByUserName(userName);

       if(user!=null){

           if(user.getPassword().equals(loginRequestDTO.getPassword())) {
               return new  LoginResponseDTO("success");
           }
           else{
               throw new WrongCredentialsException("password is wrong");
           }
       }
       else throw new UserNotFoundException("username is wrong");

    }

    public  boolean isAdmin(String userName){

        Users user= usersRepo.findByUserName(userName);

        if(user==null){
            throw  new UserNotFoundException("user not exist");
        }

        if(user.isAdminApproved()){
            return true;
        }

        return false;
    }

    public void setAsAdmin(String userName,int userId){

        if(isAdmin(userName)){
            usersRepo.setAsAdmin(userId);
        }
        else if(userName==null){
            throw new UserNotFoundException("user not exist");
        }
        else{
            throw new WrongAccess("not possible");
        }
    }

    public Users getUserById(int userId){

        return usersRepo.findByUserId(userId);
    }

//    public UserOrdersResponseDTO userOrders(int userId){
//
//        if(usersRepo.findByUserId(userId)==null){
//            throw new UserNotFoundException("user not found");
//        }
//
//        List<Object []> list=usersRepo.userOrders(userId);
//
//        if(list.size()==0){
//            throw new WrongAccess("user not placed any orders");
//        }
//
//        UserOrdersResponseDTO userOrdersResponseDTO =new UserOrdersResponseDTO();
//
//        List<Orders> list1=new ArrayList<>();
//
//
//            for(Object [] obj:list){
//
//                Orders p=ordersService.getOrder(Integer.parseInt(obj[0].toString()));
//
//                list1.add(p);
//            }
//
//        userOrdersResponseDTO.setList(list1);
//
//        return userOrdersResponseDTO;
//
//    }

    public UserOrdersResponseDTO userOrders(int userId){

        if(usersRepo.findByUserId(userId)==null){
            throw new UserNotFoundException("user not found");
        }

        List<Object []> list=usersRepo.userOrders(userId);

        if(list.size()==0){
            throw new WrongAccess("user not placed any orders");
        }

        UserOrdersResponseDTO userOrdersResponseDTO =new UserOrdersResponseDTO();

        List<Orders> list1=new ArrayList<>();


        for(Object [] obj:list){

            Orders p=ordersService.getOrder(Integer.parseInt(obj[0].toString()));

            list1.add(p);
        }

        userOrdersResponseDTO.setList(list1);

        return userOrdersResponseDTO;

    }
}
