package com.example.thuctap.services.cart;

import com.example.thuctap.models.Cart;

import java.util.HashMap;

public interface ICartServices {
    HashMap<Integer, Cart> addCart(int product_id, HashMap<Integer, Cart> cart);
    HashMap<Integer, Cart> editCart(int product_id, int quantity, HashMap<Integer, Cart> cart);
    HashMap<Integer, Cart> deleteCart(int product_id, HashMap<Integer, Cart> cart);
    double totalPrice(HashMap<Integer, Cart> cart);
    int totalQuantity(HashMap<Integer, Cart> cart);


}
