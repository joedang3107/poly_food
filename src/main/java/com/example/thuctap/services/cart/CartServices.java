package com.example.thuctap.services.cart;

import com.example.thuctap.models.Cart;
import com.example.thuctap.models.Product;
import com.example.thuctap.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartServices implements ICartServices{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public HashMap<Integer, Cart> addCart(int product_id, HashMap<Integer, Cart> cart) {

        Cart itemCart = new Cart();
        Product product = productRepository.findById(product_id).get();

        if (product != null){
            itemCart.setProduct(product);
            itemCart.setQuantity(1);
            itemCart.setTotalPrice(product.getPrice());
            cart.put(product_id, itemCart);
        }
        return cart;
    }

    @Override
    public HashMap<Integer, Cart> editCart(int product_id, int quantity, HashMap<Integer, Cart> cart) {

        Cart itemCart;

        if (cart == null) {
            return cart;
        }
        if (cart.containsKey(product_id)) {
            itemCart = cart.get(product_id);
            itemCart.setQuantity(quantity);
            itemCart.setTotalPrice(itemCart.getProduct().getPrice()*quantity);
            cart.put(product_id, itemCart);
        }
        return cart;
    }

    @Override
    public HashMap<Integer, Cart> deleteCart(int product_id, HashMap<Integer, Cart> cart) {

        if (cart == null) {
            return cart;
        }
        if (cart.containsKey(product_id)) {
            cart.remove(product_id);
        }

        return cart;
    }

    @Override
    public int totalQuantity(HashMap<Integer, Cart> cart) {

        int total = 0;
        for (Map.Entry<Integer, Cart> itemCart: cart.entrySet()) {

            total += itemCart.getValue().getQuantity();
        }
        return total;
    }
    @Override
    public double totalPrice(HashMap<Integer, Cart> cart) {

        double total = 0;
        for (Map.Entry<Integer, Cart> itemCart: cart.entrySet()) {

            total += itemCart.getValue().getTotalPrice();
        }
        return total;
    }
}
