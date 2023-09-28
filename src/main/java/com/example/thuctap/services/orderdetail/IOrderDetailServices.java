package com.example.thuctap.services.orderdetail;

import com.example.thuctap.models.OrderDetail;
import com.example.thuctap.models.responseobject.ProductResponses;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IOrderDetailServices {

    public ProductResponses<OrderDetail> addOrderDetail(OrderDetail orderDetail);
    public ProductResponses<OrderDetail> editOrderDetail(OrderDetail orderDetail);
    public ProductResponses<OrderDetail> deleteOrderDetail(int order_detail_id);
    public List<OrderDetail> showOrderDetails();
}
