package com.example.thuctap.services.product;

import com.example.thuctap.models.Product;
import com.example.thuctap.models.ProductType;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.ProductImageRepository;
import com.example.thuctap.repository.ProductRepository;
import com.example.thuctap.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices implements IProductServices{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public ProductResponses<Product> addProduct(Product product) {

        ProductResponses<Product> responses = new ProductResponses<>();

        Optional<ProductType> optionalProductType = productTypeRepository.findById(product.getProductType().getProduct_type_id());

        if (optionalProductType.isEmpty()){
            responses.setStatus(5);
            responses.setError("Loai san pham khong ton tai");
            return responses;
        }

        product.setCreated_at(new Date());

        productRepository.save(product);
        responses.setStatus(1);
        responses.setError("Them san pham thanh cong");
        responses.setData(product);
        return responses;

    }

    @Override
    public ProductResponses<Product> editProduct(Product product) {

        ProductResponses<Product> respones = new ProductResponses<>();

        Product currentProduct = productRepository.findById(product.getProduct_id()).get();

        currentProduct.setName_product(product.getName_product());
        currentProduct.setAvatar_image_product(product.getAvatar_image_product());
        currentProduct.setUpdate_at(new Date());
        currentProduct.setDiscount(product.getDiscount());
        currentProduct.setNumber_of_views(product.getNumber_of_views());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setStatus(product.getStatus());
        currentProduct.setTitle(product.getTitle());

        productRepository.save(currentProduct);
        respones.setStatus(1);
        respones.setError("Sua san pham thanh cong");
        respones.setData(currentProduct);
        return respones;

    }

    @Override
    public ProductResponses<Product> deleteProduct(int product_id) {

        ProductResponses<Product> respones = new ProductResponses<>();

        Optional<Product> optionalProduct = productRepository.findById(product_id);

        if (optionalProduct.isEmpty()){
            respones.setStatus(5);
            respones.setError("San pham khong ton tai");
            return respones;
        }

        productRepository.delete(optionalProduct.get());
        respones.setStatus(1);
        respones.setError("Xoa san pham thanh cong");
        respones.setData(optionalProduct.get());
        return respones;

    }

    @Override
    public List<Product> showProducts() {

        return productRepository.findAll();
    }
}
