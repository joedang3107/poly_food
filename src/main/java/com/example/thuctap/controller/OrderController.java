package com.example.thuctap.controller;

import com.example.thuctap.models.Order;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.order.OrderServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @RequestMapping(value = "add_order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Order> addOrder(@RequestBody String order) {

        Gson gson = new Gson();

        Order result = gson.fromJson(order, Order.class);
        return orderServices.addOrder(result);
    }

    @RequestMapping(value = "edit_order", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Order> editOrder(@RequestBody String order) {

        Gson gson = new Gson();
        Order result = gson.fromJson(order, Order.class);
        return orderServices.editOrder(result);
    }

    @RequestMapping(value = "delete_order", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Order> deleteOrder(@RequestParam int order_id) {

        return orderServices.deleteOrder(order_id);
    }

    @RequestMapping(value = "show_orders", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<Order> showOrders() {

        return orderServices.showOrders();
    }

}
