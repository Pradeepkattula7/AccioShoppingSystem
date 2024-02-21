package com.ShoppingSystem.AccioShopSystem.Repository;

import com.ShoppingSystem.AccioShopSystem.Entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {

    public  Users findByUserName(String userName);

    public  Users findByUserId(Integer userId);

    @Query(value = "select count(*) from users where is_admin_approved=true",nativeQuery = true)
    public List<Object []> countOfAdmin();



    @Transactional
    @Modifying
    @Query(value = "update users set is_admin_approved=true where user_id=:userId",nativeQuery = true)
    public void setAsAdmin(int userId);


    @Query(value = "select role from  users where user_id=:userId",nativeQuery = true)
    public String userRole(int userId);


    @Transactional
    @Modifying
    @Query(value = "insert into users_orders_list(users_user_id,orders_list_order_id) values(:userId,:orderId)",nativeQuery = true)
    public void insert(int userId,int orderId);


    @Query(value = "select orders_list_order_id from users_orders_list where users_user_id=:userId",nativeQuery = true)
    public List<Object []> userOrders(int userId);
}