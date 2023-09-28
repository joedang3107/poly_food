package com.example.thuctap.services.productreview;

import com.example.thuctap.models.ProductReview;
import com.example.thuctap.models.responseobject.ProductResponses;

import java.util.List;

public interface IProductReviewServices {

    public ProductResponses<ProductReview> addProductReview(ProductReview productReview);
    public ProductResponses<ProductReview> editProductReview(ProductReview productReview);
    public ProductResponses<ProductReview> deleteProductReview(int product_review_id);
    public List<ProductReview> showProductReviews();
}
