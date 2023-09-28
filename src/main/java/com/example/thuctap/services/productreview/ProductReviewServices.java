package com.example.thuctap.services.productreview;

import com.example.thuctap.models.Product;
import com.example.thuctap.models.ProductReview;
import com.example.thuctap.models.User;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.ProductRepository;
import com.example.thuctap.repository.ProductReviewRepository;
import com.example.thuctap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewServices implements IProductReviewServices{

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProductResponses<ProductReview> addProductReview(ProductReview productReview) {

        ProductResponses<ProductReview> responses = new ProductResponses<>();

        Optional<Product> optionalProduct = productRepository.findById(productReview.getProduct().getProduct_id());

        Optional<User> optionalUser = userRepository.findById(productReview.getUser().getUserId());

        if (optionalProduct.isEmpty()) {
            responses.setStatus(5);
            responses.setError("Product khong ton tai");
            return responses;
        }

        if (optionalUser.isEmpty()) {
            responses.setStatus(5);
            responses.setError("User khong ton tai");
            return responses;
        }

        productReview.setCreated_at(new Date());
        productReviewRepository.save(productReview);
        responses.setStatus(1);
        responses.setError("Them product review thanh cong");
        responses.setData(productReview);
        return responses;
    }

    @Override
    public ProductResponses<ProductReview> editProductReview(ProductReview productReview) {

        ProductResponses<ProductReview> responses = new ProductResponses<>();

        Optional<ProductReview> optionalProductReview = productReviewRepository.findById(productReview.getProduct_review_id());

        if (optionalProductReview.isEmpty()) {
            responses.setStatus(5);
            responses.setError("Product review khong ton tai");
            return responses;
        }

        ProductReview currentProductReview = optionalProductReview.get();;
        currentProductReview.setContent_rated(productReview.getContent_rated());
        currentProductReview.setPoint_evaluation(productReview.getPoint_evaluation());
        currentProductReview.setContent_seen(productReview.getContent_seen());
        currentProductReview.setStatus(productReview.getStatus());
        productReview.setUpdate_at(new Date());
        productReviewRepository.save(currentProductReview);
        responses.setStatus(1);
        responses.setError("Sua product review thanh cong");
        responses.setData(productReview);
        return responses;

    }

    @Override
    public ProductResponses<ProductReview> deleteProductReview(int product_review_id) {

        ProductResponses<ProductReview> responses = new ProductResponses<>();

        Optional<ProductReview> optionalProductReview = productReviewRepository.findById(product_review_id);

        if (optionalProductReview.isEmpty()) {
            responses.setStatus(5);
            responses.setError("Product review khong ton tai");
            return responses;
        }

        productReviewRepository.delete(optionalProductReview.get());
        responses.setStatus(1);
        responses.setError("Xoa product review thanh cong");
        return responses;
    }

    @Override
    public List<ProductReview> showProductReviews() {
        return productReviewRepository.findAll();
    }
}
