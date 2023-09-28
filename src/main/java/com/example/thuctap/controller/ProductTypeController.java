package com.example.thuctap.controller;

import com.example.thuctap.models.ProductType;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.producttype.ProductTypeServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class ProductTypeController {

    @Autowired
    private ProductTypeServices productTypeServices;

    @RequestMapping(value = "add_product_type", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<ProductType> addProductType(@RequestBody String productType) {

        Gson gson = new Gson();

        ProductType result = gson.fromJson(productType, ProductType.class);
        return productTypeServices.addProductType(result);
    }

    @RequestMapping(value = "edit_product_type", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<ProductType> editProductType(@RequestBody String productType) {

        Gson gson = new Gson();

        ProductType result = gson.fromJson(productType, ProductType.class);
        return productTypeServices.editProductType(result);
    }

    @RequestMapping(value = "delete_product_type", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponses<ProductType> deleteProductType(@RequestParam int product_type_id) {

        return productTypeServices.deleteProductType(product_type_id);
    }

    @RequestMapping(value = "show_product_types", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<ProductType> showProductTypes() {

        return productTypeServices.showProductTypes();
    }

}
