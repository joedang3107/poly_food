package com.example.thuctap.controller;

import com.example.thuctap.models.Product;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.cloudinary.CloudinaryServices;
import com.example.thuctap.services.product.ProductServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @Autowired
    private CloudinaryServices cloudinaryServices;

    @RequestMapping(value = "add_product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Product> addProduct(@RequestBody String product) {

        Gson gson = new Gson();
        Product result = gson.fromJson(product, Product.class);

        return productServices.addProduct(result);
    }

    @RequestMapping(value = "edit_product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Product> editProduct(@RequestBody String product) {

        Gson gson = new Gson();
        Product result = gson.fromJson(product, Product.class);
        return productServices.editProduct(result);
    }

    @RequestMapping(value = "delete_product", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<Product> deleteProduct(@RequestParam int product_id) {

        return productServices.deleteProduct(product_id);
    }

    @RequestMapping(value = "show_products", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Product> showProducts() {

        return productServices.showProducts();
    }

}
