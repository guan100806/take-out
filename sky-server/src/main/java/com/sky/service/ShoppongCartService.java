package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppongCartService {
    void add(ShoppingCartDTO shoppingCartDto);

    void sub(ShoppingCartDTO shoppingCartDto);

    List<ShoppingCart> showShoppingCart();

    void clean();
}
