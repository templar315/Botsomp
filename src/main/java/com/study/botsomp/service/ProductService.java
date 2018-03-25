package com.study.botsomp.service;

import com.study.botsomp.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    boolean addOrUpdate(ProductDTO productDTO);
    boolean delete(Long id);
    ProductDTO getOne(Long id);
    boolean existsById(Long id);
    List<ProductDTO> findAll();
    List<ProductDTO> findByName(String name);
    List<ProductDTO> findByType(String type);
    ProductDTO findByNameAndSteelGradeDesignation(String name, String steelGradeDesignation);
    List<ProductDTO> findByTypeAndSteelGradeDesignation(String type, String steelGradeDesignation);
    List<ProductDTO> findBySteelGradeAndProductStandard(String steelGradeDesignation, String standardDesignation);

}
