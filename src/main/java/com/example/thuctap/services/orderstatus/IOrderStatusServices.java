package com.example.thuctap.services.orderstatus;

import com.example.thuctap.models.OrderStatus;
import com.example.thuctap.models.responseobject.ProductResponses;

import java.util.List;

public interface IOrderStatusServices {

    public ProductResponses<OrderStatus> addOrderStatus(OrderStatus orderStatus);
    public ProductResponses<OrderStatus> editOrderStatus(OrderStatus orderStatus);
    public ProductResponses<OrderStatus> deleteOrderStatus(int order_status_id);
    public List<OrderStatus> showOrderStatuses();

}
