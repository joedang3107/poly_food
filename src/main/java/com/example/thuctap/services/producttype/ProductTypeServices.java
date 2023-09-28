package com.example.thuctap.services.producttype;

import com.example.thuctap.models.ProductType;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProductTypeServices implements IProductTypeServices{

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public ProductResponses<ProductType> addProductType(ProductType productType) {

        ProductResponses<ProductType> respones = new ProductResponses<>();

        productType.setCreated_at(new Date());
        productTypeRepository.save(productType);
        respones.setStatus(1);
        respones.setError("Them loai san pham thanh cong");
        respones.setData(productType);
        return respones;
    }

    @Override
    public ProductResponses<ProductType> editProductType(ProductType productType) {

        ProductResponses<ProductType> respones = new ProductResponses<>();

        Optional<ProductType> optionalProductType = productTypeRepository.findById(productType.getProduct_type_id());

        if (optionalProductType.isEmpty()){
            respones.setStatus(5);
            respones.setError("Loai san pham khong ton tai");
            return respones;
        }

        ProductType currenProductType = optionalProductType.get();

        currenProductType.setNameProductType(productType.getNameProductType());
        currenProductType.setImage_type_product(productType.getImage_type_product());
        currenProductType.setUpdate_at(new Date());

        productTypeRepository.save(currenProductType);
        respones.setStatus(5);
        respones.setError("Sua loai san pham thanh cong ");
        respones.setData(currenProductType);
        return respones;
    }

    @Override
    public ProductResponses<ProductType> deleteProductType(int product_type_id) {

        ProductResponses<ProductType> respones = new ProductResponses<>();

        Optional<ProductType> optionalProductType = productTypeRepository.findById(product_type_id);

        if (optionalProductType.isEmpty()){
            respones.setStatus(5);
            respones.setError("Loai san pham khong ton tai");
            return respones;
        }

        productTypeRepository.delete(optionalProductType.get());
        respones.setStatus(1);
        respones.setError("Xoa loai san pham thanh cong");
        respones.setData(optionalProductType.get());
        return respones;
    }

    @Override
    public Page<ProductType> showProductTypes() {
        Pageable showProductTypes = PageRequest.of(0, productTypeRepository.findAll().size(), Sort.by("nameProductType"));
        return productTypeRepository.findAll(showProductTypes);

    }
}
