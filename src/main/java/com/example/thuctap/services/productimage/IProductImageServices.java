package com.example.thuctap.services.productimage;

import com.example.thuctap.models.ProductImage;
import com.example.thuctap.models.responseobject.ProductResponses;

import java.util.List;

public interface IProductImageServices {

    public ProductResponses<ProductImage> addProductImage(ProductImage productImage);
    public ProductResponses<ProductImage> editProductImage(ProductImage productImage);
    public ProductResponses<ProductImage> deleteImage(int product_image_id);
    public List<ProductImage> showProductImages();

}
