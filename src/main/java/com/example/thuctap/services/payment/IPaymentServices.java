package com.example.thuctap.services.payment;

import com.example.thuctap.models.Cart;
import com.example.thuctap.models.Order;
import com.example.thuctap.models.Payment;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.payload.response.CartResponse;

import java.util.HashMap;

public interface IPaymentServices {

    public ProductResponses<Payment> addPayment(Payment payment);
    public ProductResponses<Payment> deletePayment(int payment_id);
    public ProductResponses<Order> payment_COD(int order_id);
    public CartResponse<HashMap<Integer, Cart>> payment_COD_no_user(HashMap<Integer, Cart> cart);
}
