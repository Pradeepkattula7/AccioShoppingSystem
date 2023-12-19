package com.ShoppingSystem.AccioShopSystem.Repository;

import com.ShoppingSystem.AccioShopSystem.Entity.Cart;
import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

    @Query(value ="select cart_cart_id from users where user_id=:userId",nativeQuery = true)
    public int getCartByUserID(int userId);


    @Transactional
    @Modifying
    @Query(value = "insert into cart_product_list(cart_cart_id,product_list_product_id) values(:cartId,:productId)",nativeQuery = true)
    public void insert(int cartId,int productId);


    @Transactional
    @Modifying
    @Query(value = "update cart set total_items=:totalItems where cart_id=:cartId",nativeQuery = true)
    public void updateItems(int cartId,int totalItems);


    @Transactional
    @Modifying
    @Query(value = "update cart set total_price=:price where cart_id=:cartId",nativeQuery = true)
    public void  updatePrice(int cartId,int price);


    @Query(value = "select total_price from cart where cart_id=:cartId",nativeQuery = true)
    public int cartPrice(int cartId);


    @Query(value = "select total_items from cart where cart_id=:cartId",nativeQuery = true)
    public int cartItems(int cartId);


    @Query(value = "select product_list_product_id from cart_product_list where cart_cart_id=:cartId",nativeQuery = true)
    public List<Object []> showCart(int cartId);

    @Transactional
    @Modifying
    @Query(value = "delete from cart_product_list where cart_cart_id=:cartId and product_list_product_id=:productId",nativeQuery = true)
    public  void removeItems(int cartId,int productId);
}
