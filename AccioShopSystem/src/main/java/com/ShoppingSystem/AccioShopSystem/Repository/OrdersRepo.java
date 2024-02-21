package com.ShoppingSystem.AccioShopSystem.Repository;

import com.ShoppingSystem.AccioShopSystem.Entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Integer> {


    @Transactional
    @Modifying
    @Query(value = "insert into orders_product_list(orders_order_id,product_list_product_id) values(:orderId,:productId)",nativeQuery = true)
    public void insert(int orderId,int productId);


    @Query(value = "select * from orders where order_id=:orderId",nativeQuery = true)
    public Orders getOrder(int orderId);
}
