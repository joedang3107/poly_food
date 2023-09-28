package com.example.thuctap.controller;

import com.example.thuctap.models.ProductReview;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.productreview.ProductReviewServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class ProductReviewController {

    @Autowired
    private ProductReviewServices productReviewServices;

    @RequestMapping(value = "add_product_review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponses<ProductReview> addProductReview(@RequestBody String productReview) {

        Gson gson = new Gson();

        ProductReview result = gson.fromJson(productReview, ProductReview.class);
        return productReviewServices.addProductReview(result);
    }

    @RequestMapping(value = "edit_product_review", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponses<ProductReview> editProductReview(@RequestBody String productReview) {

        Gson gson = new Gson();

        ProductReview result = gson.fromJson(productReview, ProductReview.class);
        return productReviewServices.editProductReview(result);
    }

    @RequestMapping(value = "delete_product_review", method = RequestMethod.DELETE)
    public ProductResponses<ProductReview> deleteProductReview(@RequestParam int product_review_id) {

        return productReviewServices.deleteProductReview(product_review_id);
    }

    @RequestMapping(value = "show_product_reviews", method = RequestMethod.GET)
    public List<ProductReview> showProductReviews() {

        return productReviewServices.showProductReviews();
    }
}
