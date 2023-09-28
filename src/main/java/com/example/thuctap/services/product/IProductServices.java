package com.example.thuctap.services.product;

import com.example.thuctap.models.Product;
import com.example.thuctap.models.responseobject.ProductResponses;

import java.util.List;

public interface IProductServices {

    public ProductResponses<Product> addProduct(Product product);
    public ProductResponses<Product> editProduct(Product product);
    public ProductResponses<Product> deleteProduct(int product_id);
    public List<Product> showProducts();

}
