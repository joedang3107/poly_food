package com.example.thuctap.services.payment;

import com.example.thuctap.models.Cart;
import com.example.thuctap.models.Order;
import com.example.thuctap.models.Payment;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.payload.response.CartResponse;
import com.example.thuctap.repository.OrderRepository;
import com.example.thuctap.repository.PaymentRepository;
import com.example.thuctap.services.cart.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class PaymentServices implements IPaymentServices{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartServices cartServices;

    @Override
    public ProductResponses<Payment> addPayment(Payment payment) {

        ProductResponses<Payment> responses = new ProductResponses<>();

        payment.setCreated_at(new Date());
        paymentRepository.save(payment);
        responses.setStatus(1);
        responses.setError("Them payment thanh cong");
        responses.setData(payment);
        return responses;
    }

    @Override
    public ProductResponses<Payment> deletePayment(int payment_id) {

        ProductResponses<Payment> responses = new ProductResponses<>();

        paymentRepository.deleteById(payment_id);
        responses.setStatus(1);
        responses.setError("Xoa payment thanh cong");
        responses.setData(null);
        return responses;
    }

    @Override
    public ProductResponses<Order> payment_COD(int order_id) {
        ProductResponses<Order> responses = new ProductResponses<>();

        Order order = orderRepository.findById(order_id).get();

        responses.setStatus(1);
        responses.setError("Đặt đơn hàng thành công");
        responses.setData(order);

        return responses;
    }

    @Override
    public CartResponse<HashMap<Integer, Cart>> payment_COD_no_user(HashMap<Integer, Cart> cart) {

        CartResponse<HashMap<Integer, Cart>> responses = new CartResponse<>();

        responses.setData(cart);
        responses.setMessage("Đặt đơn hàng thành công");
        responses.setTotalPrice(cartServices.totalPrice(cart));
        responses.setTotalQuantity(cartServices.totalQuantity(cart));

        return responses;
    }




}
