package com.ShoppingSystem.AccioShopSystem.Repository;

import com.ShoppingSystem.AccioShopSystem.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    public List<Product> findAll();

    public Product findById(int productId);
}
