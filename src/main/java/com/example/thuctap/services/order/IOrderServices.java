package com.example.thuctap.services.order;

import com.example.thuctap.models.Order;
import com.example.thuctap.models.responseobject.ProductResponses;
import org.springframework.data.domain.Page;

public interface IOrderServices {

    public ProductResponses<Order> addOrder(Order order);
    public ProductResponses<Order> editOrder(Order order);
    public ProductResponses<Order> deleteOrder(int order_id);
    public Page<Order> showOrders();

}
