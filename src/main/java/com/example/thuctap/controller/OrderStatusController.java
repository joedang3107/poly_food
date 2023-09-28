package com.example.thuctap.controller;

import com.example.thuctap.models.OrderStatus;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.orderstatus.OrderStatusServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class OrderStatusController {

    @Autowired
    private OrderStatusServices orderStatusServices;

    @RequestMapping(value = "add_order_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<OrderStatus> addOrderStatus(@RequestBody String orderStatus) {

        Gson gson = new Gson();

        OrderStatus result = gson.fromJson(orderStatus, OrderStatus.class);
        return orderStatusServices.addOrderStatus(result);
    }

    @RequestMapping(value = "edit_order_status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<OrderStatus> editOrderStatus(@RequestBody String orderStatus) {

        Gson gson = new Gson();

        OrderStatus result = gson.fromJson(orderStatus, OrderStatus.class);
        return orderStatusServices.editOrderStatus(result);
    }

    @RequestMapping(value = "delete_order_status", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<OrderStatus> deleteOrderStatus(@RequestParam int order_status_id) {

        return orderStatusServices.deleteOrderStatus(order_status_id);
    }

    @RequestMapping(value = "show_order_statuses", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<OrderStatus> showOrderStatuses() {

        return orderStatusServices.showOrderStatuses();
    }


}
