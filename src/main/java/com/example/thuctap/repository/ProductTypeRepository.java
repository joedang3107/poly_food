package com.example.thuctap.repository;

import com.example.thuctap.models.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    public List<ProductType> findByNameProductType(String nameProductType, Pageable pageable);
}
