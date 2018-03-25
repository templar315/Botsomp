package com.study.botsomp.repository;

import com.study.botsomp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findByType(String type);
    Product findByNameAndSteelGradeDesignation(String productName, String steelGradeDesignation);
    List<Product> findByTypeAndSteelGradeDesignation(String productType, String steelGradeDesignation);
    List<Product> findBySteelGradeDesignationAndProductStandardDesignation(String steelGradeDesignation,
                                                                                   String standardDesignation);

}
