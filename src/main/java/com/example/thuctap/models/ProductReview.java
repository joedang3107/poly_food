package com.example.thuctap.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product_review")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id")
    private int product_review_id;

    @Column(name = "product_id", insertable = false, updatable = false)
    private int product_id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int user_id;

    @Column(name = "content_rated")
    private String content_rated;

    @Column(name = "point_evaluation")
    private int point_evaluation;

    @Column(name = "content_seen")
    private String content_seen;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "update_at")
    private Date update_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public int getProduct_review_id() {
        return product_review_id;
    }

    public void setProduct_review_id(int product_review_id) {
        this.product_review_id = product_review_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getContent_rated() {
        return content_rated;
    }

    public void setContent_rated(String content_rated) {
        this.content_rated = content_rated;
    }

    public int getPoint_evaluation() {
        return point_evaluation;
    }

    public void setPoint_evaluation(int point_evaluation) {
        this.point_evaluation = point_evaluation;
    }

    public String getContent_seen() {
        return content_seen;
    }

    public void setContent_seen(String content_seen) {
        this.content_seen = content_seen;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
