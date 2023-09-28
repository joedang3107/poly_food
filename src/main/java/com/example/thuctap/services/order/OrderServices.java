package com.example.thuctap.services.order;

import com.example.thuctap.models.*;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.OrderRepository;
import com.example.thuctap.repository.OrderStatusRepository;
import com.example.thuctap.repository.PaymentRepository;
import com.example.thuctap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServices implements IOrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private OrderStatusRepository orderStatusRepository;


    @Override
    public ProductResponses<Order> addOrder(Order order) {

        ProductResponses<Order> responses = new ProductResponses<>();

        Optional<User> optionalUser = userRepository.findById(order.getUser().getUserId());

        if (optionalUser.isEmpty()) {
            responses.setStatus(5);
            responses.setError("User ko ton tai");
            return responses;
        }
        order.setCreated_at(new Date());
        orderRepository.save(order);
        responses.setStatus(1);
        responses.setError("Them Order thanh cong");
        responses.setData(order);
        return responses;
    }

    @Override
    public ProductResponses<Order> editOrder(Order order) {

        ProductResponses<Order> responses = new ProductResponses<>();

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrder_id());

        if (optionalOrder.isEmpty()) {
            responses.setStatus(5);
            responses.setError("Order khong ton tai");
            return responses;
        }
        Order currentOrder = optionalOrder.get();
//        currentOrder.setActual_price(order.getActual_price());
        currentOrder.setOriginal_price(order.getOriginal_price());
        currentOrder.setFullName(order.getFullName());
        currentOrder.setEmail(order.getEmail());
        currentOrder.setPhone(order.getPhone());
        currentOrder.setAddress(order.getAddress());
        currentOrder.setUpdate_at(new Date());

        orderRepository.save(currentOrder);
        responses.setStatus(1);
        responses.setError("Sua order thanh cong");
        responses.setData(currentOrder);
        return responses;

    }

    @Override
    public ProductResponses<Order> deleteOrder(int order_id) {

        ProductResponses<Order> respones = new ProductResponses<>();

        Optional<Order> optionalOrder = orderRepository.findById(order_id);

        if (optionalOrder.isEmpty()) {
            respones.setStatus(5);
            respones.setError("Order khong ton tai");
            return respones;
        }

        orderRepository.delete(optionalOrder.get());
        respones.setStatus(1);
        respones.setError("Xoa order thanh cong");
        return respones;
    }

    @Override
    public Page<Order> showOrders() {

        Pageable showOrders = PageRequest.of(0, orderRepository.findAll().size(), Sort.by("fullName"));

        return orderRepository.findAll(showOrders);
    }
}
