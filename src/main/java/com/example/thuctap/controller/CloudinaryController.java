package com.example.thuctap.controller;

import com.example.thuctap.models.Product;
import com.example.thuctap.models.ProductImage;
import com.example.thuctap.models.ProductType;
import com.example.thuctap.repository.ProductRepository;
import com.example.thuctap.repository.ProductTypeRepository;
import com.example.thuctap.services.cloudinary.CloudinaryServices;
import com.example.thuctap.services.productimage.ProductImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class CloudinaryController {

    @Autowired
    private CloudinaryServices cloudinaryServices;

    @Autowired
    private ProductImageServices productImageServices;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @PostMapping(value = "cloudinary/upload")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("product_id") int product_id,
            @RequestParam("title_image") int title_image,
            @RequestParam("title_product") String title_product
            ) throws IOException {

        Map data = cloudinaryServices.upload(file);

        String url = null;
        for (Object key : data.keySet()) {
            if (key.toString().equals("url")) {
                url = data.get(key).toString();
            }
        }

        ProductImage image = new ProductImage();
        Product product = productRepository.findById(product_id).get();
        ProductType product_type = product.getProductType();

        image.setImage_product(url);
        image.setCreated_at(new Date());
        image.setProduct(product);
        image.setTitle(title_image);
        image.setStatus(1);

        // Them avatar cho product do, 1 dc danh dau la avatar cua product do
        if (image.getTitle() == 1) {
            product.setAvatar_image_product(url);
            product.setTitle(title_product);
        }

        // Them avatar cho product_type do
        if (product.getTitle().equals("avatar")){
            product_type.setImage_type_product(url);
        }

        productImageServices.addProductImage(image);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "cloudinary/uploadImages")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String uploadImages(@RequestParam("images") MultipartFile[] files) {
        cloudinaryServices.uploadMultipleFiles(files);
        return "upload images successfully";
    }

}
