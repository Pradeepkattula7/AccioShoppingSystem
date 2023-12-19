package com.ShoppingSystem.AccioShopSystem.Exception;

public class ProductNotFoundException extends  RuntimeException{

    public ProductNotFoundException(String msg){
        super(msg);
    }
}
