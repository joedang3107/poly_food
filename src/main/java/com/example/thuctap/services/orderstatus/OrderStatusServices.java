package com.example.thuctap.services.orderstatus;

import com.example.thuctap.models.OrderStatus;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusServices implements IOrderStatusServices{

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    public ProductResponses<OrderStatus> addOrderStatus(OrderStatus orderStatus) {

        ProductResponses<OrderStatus> respones = new ProductResponses<>();

        orderStatusRepository.save(orderStatus);
        respones.setStatus(1);
        respones.setError("Them order status thanh cong");
        respones.setData(orderStatus);
        return respones;
    }

    @Override
    public ProductResponses<OrderStatus> editOrderStatus(OrderStatus orderStatus) {

        ProductResponses<OrderStatus> respones = new ProductResponses<>();

        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(orderStatus.getOrder_status_id());

        if ((optionalOrderStatus.isEmpty())){
            respones.setStatus(5);
            respones.setError("Order status khong ton tai");
            return respones;
        }

        OrderStatus currentOrderStatus = optionalOrderStatus.get();
        currentOrderStatus.setStatus_name(orderStatus.getStatus_name());

        orderStatusRepository.save(currentOrderStatus);
        respones.setStatus(1);
        respones.setError("Sua order status thanh cong");
        respones.setData(currentOrderStatus);
        return respones;

    }

    @Override
    public ProductResponses<OrderStatus> deleteOrderStatus(int order_status_id) {

        ProductResponses<OrderStatus> respones = new ProductResponses<>();

        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(order_status_id);

        if ((optionalOrderStatus.isEmpty())){
            respones.setStatus(5);
            respones.setError("Order status khong ton tai");
            return respones;
        }

        orderStatusRepository.delete(optionalOrderStatus.get());
        respones.setStatus(1);
        respones.setError("Xoa order status thanh cong");
        return respones;
    }

    @Override
    public List<OrderStatus> showOrderStatuses() {
        return orderStatusRepository.findAll();
    }
}
