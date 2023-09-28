package com.example.thuctap.services.orderdetail;

import com.example.thuctap.models.Order;
import com.example.thuctap.models.OrderDetail;
import com.example.thuctap.models.Product;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.OrderDetailRepository;
import com.example.thuctap.repository.OrderRepository;
import com.example.thuctap.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServices implements IOrderDetailServices{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponses<OrderDetail> addOrderDetail(OrderDetail orderDetail) {

        ProductResponses<OrderDetail> respones = new ProductResponses<>();

        Optional<Order> optionalOrder = orderRepository.findById(orderDetail.getOrder().getOrder_id());

        Optional<Product> optionalProduct = productRepository.findById(orderDetail.getProduct().getProduct_id());

        if ((optionalOrder.isEmpty())){
            respones.setStatus(5);
            respones.setError("Order khong ton tai");
            return respones;
        }

        if ((optionalProduct.isEmpty())){
            respones.setStatus(5);
            respones.setError("Product khong ton tai");
            return respones;
        }

        orderDetail.setPrice_total(optionalProduct.get().getPrice()*orderDetail.getQuantity());
        optionalOrder.get().setOriginal_price(orderDetail.getPrice_total() + optionalOrder.get().getOriginal_price());

        orderDetail.setCreated_at(new Date());
        orderDetailRepository.save(orderDetail);
        respones.setStatus(1);
        respones.setError("Them order detail thanh cong");
        respones.setData(orderDetail);
        return respones;
    }

    @Override
    public ProductResponses<OrderDetail> editOrderDetail(OrderDetail orderDetail) {

        ProductResponses<OrderDetail> respones = new ProductResponses<>();

        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetail.getOrder_detail_id());

        if ((optionalOrderDetail.isEmpty())){
            respones.setStatus(5);
            respones.setError("Order detail khong ton tai");
            return respones;
        }

        OrderDetail currentOrderDetail = optionalOrderDetail.get();
        currentOrderDetail.setQuantity(orderDetail.getQuantity());
        currentOrderDetail.setPrice_total(orderDetail.getQuantity()*optionalOrderDetail.get().getProduct().getPrice());
        currentOrderDetail.setUpdate_at(new Date());
        orderDetailRepository.save(currentOrderDetail);
        respones.setStatus(1);
        respones.setError("Sua order detail thanh cong");
        respones.setData(currentOrderDetail);
        return respones;

    }

    @Override
    public ProductResponses<OrderDetail> deleteOrderDetail(int order_detail_id) {

        ProductResponses<OrderDetail> respones = new ProductResponses<>();

        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(order_detail_id);

        if ((optionalOrderDetail.isEmpty())){
            respones.setStatus(5);
            respones.setError("Order detail khong ton tai");
            return respones;
        }
        orderDetailRepository.delete(optionalOrderDetail.get());
        respones.setStatus(1);
        respones.setError("Xoa order detail thanh cong");
        return respones;
    }

    @Override
    public List<OrderDetail> showOrderDetails() {
        return orderDetailRepository.findAll();
    }
}
