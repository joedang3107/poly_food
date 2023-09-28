package com.example.thuctap.controller;

import com.example.thuctap.models.Cart;
import com.example.thuctap.services.cart.CartServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class CartController {

    @Autowired
    private CartServices cartServices;

    @GetMapping(value = "/add_cart/{product_id}")
    public HashMap<Integer, Cart> addCart(HttpSession session, @PathVariable int product_id) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("Cart");

        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartServices.addCart(product_id, cart);
        session.setAttribute("Cart",cart);

        return cart;
    }

    @GetMapping(value = "/edit_cart/{product_id}/{quantity}")
    public HashMap<Integer, Cart> editCart(HttpSession session, @PathVariable int product_id, @PathVariable int quantity) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("Cart");

        cart = cartServices.editCart(product_id, quantity, cart);
        session.setAttribute("Cart",cart);

        return cart;
    }

    @GetMapping(value = "/delete_cart/{product_id}")
    public HashMap<Integer, Cart> deleteCart(HttpSession session, @PathVariable int product_id) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("Cart");

        cart = cartServices.deleteCart(product_id, cart);
        session.setAttribute("Cart",cart);

        return cart;
    }
}
