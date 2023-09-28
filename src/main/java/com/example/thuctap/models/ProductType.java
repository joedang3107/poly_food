package com.example.thuctap.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private int product_type_id;

    @Column(name = "name_product_type")
    private String nameProductType;

    @Column(name = "image_type_product")
    private String image_type_product;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "update_at")
    private Date update_at;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
    @JsonIgnore
    private List<Product> products;

    public int getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(int product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getNameProductType() {
        return nameProductType;
    }

    public void setNameProductType(String nameProductType) {
        this.nameProductType = nameProductType;
    }

    public String getImage_type_product() {
        return image_type_product;
    }

    public void setImage_type_product(String image_type_product) {
        this.image_type_product = image_type_product;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
