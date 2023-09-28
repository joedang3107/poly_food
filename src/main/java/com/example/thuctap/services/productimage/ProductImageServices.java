package com.example.thuctap.services.productimage;

import com.example.thuctap.models.ProductImage;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServices implements IProductImageServices{

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public ProductResponses<ProductImage> addProductImage(ProductImage productImage) {

        ProductResponses<ProductImage> responses = new ProductResponses<>();

        productImageRepository.save(productImage);
        responses.setData(productImage);

        return responses;
    }

    @Override
    public ProductResponses<ProductImage> editProductImage(ProductImage productImage) {
        return null;
    }

    @Override
    public ProductResponses<ProductImage> deleteImage(int product_image_id) {
        return null;
    }

    @Override
    public List<ProductImage> showProductImages() {
        return null;
    }
}
