package com.example.thuctap.controller;

import com.example.thuctap.models.OrderDetail;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.orderdetail.OrderDetailServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class OrderDetailController {

    @Autowired
    private OrderDetailServices orderDetailServices;

    @RequestMapping(value = "/add_order_detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponses<OrderDetail> addOrderDetail(@RequestBody String orderDetail) {

        Gson gson = new Gson();

        OrderDetail result = gson.fromJson(orderDetail, OrderDetail.class);
        return orderDetailServices.addOrderDetail(result);
    }

    @RequestMapping(value = "/edit_order_detail", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponses<OrderDetail> editOrderDetail(@RequestBody String orderDetail) {

        Gson gson = new Gson();

        OrderDetail result = gson.fromJson(orderDetail, OrderDetail.class);
        return orderDetailServices.editOrderDetail(result);
    }

    @RequestMapping(value = "/delete_order_detail", method = RequestMethod.DELETE)
    public ProductResponses<OrderDetail> deleteOrderDetail(@RequestParam int order_detail_id) {

        return orderDetailServices.deleteOrderDetail(order_detail_id);
    }

    @RequestMapping(value = "/show_order_details", method = RequestMethod.GET)
    public List<OrderDetail> showOrderDetails() {

        return orderDetailServices.showOrderDetails();
    }
}
