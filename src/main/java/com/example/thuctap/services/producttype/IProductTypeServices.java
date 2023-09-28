package com.example.thuctap.services.producttype;

import com.example.thuctap.models.ProductType;
import com.example.thuctap.models.responseobject.ProductResponses;
import org.springframework.data.domain.Page;


public interface IProductTypeServices {
    public ProductResponses<ProductType> addProductType(ProductType productType);
    public ProductResponses<ProductType> editProductType(ProductType productType);
    public ProductResponses<ProductType> deleteProductType(int product_type_id);
    public Page<ProductType> showProductTypes();

}
