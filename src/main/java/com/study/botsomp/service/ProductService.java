package com.study.botsomp.service;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.repository.ProductRepository;
import com.study.botsomp.repository.StandardRepository;
import com.study.botsomp.repository.SteelGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ProductService {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final SteelGradeRepository steelGradeRepository;

    private final StandardRepository standardRepository;

    private Product fromDTO(ProductDTO productDTO) {
        if(productDTO == null) {
            return null;
        } else {
            return Product.builder()
                    .id(productDTO.getId())
                    .name(productDTO.getName())
                    .type(productDTO.getType())
                    .manufacturers(productDTO.getManufacturers() == null
                            ? null
                            : manufacturerRepository.findAllById(productDTO.getManufacturers()))
                    .steelGrade(productDTO.getSteelGrade() == null
                            ? null
                            : steelGradeRepository.findByDesignation(productDTO.getSteelGrade()))
                    .productStandard(productDTO.getStandard() == null
                            ? null
                            : standardRepository.findByDesignation(productDTO.getStandard()))
                    .build();
        }
    }

    private ProductDTO toDTO(Product product) {
        if(product == null) {
            return null;
        } else {
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .type(product.getType())
                    .manufacturers(product.getManufacturers() == null
                            ? null
                            : product.getManufacturers()
                            .stream()
                            .map(Manufacturer::getId)
                            .collect(Collectors.toList()))
                    .steelGrade(product.getSteelGrade() == null
                            ? null
                            : product.getSteelGrade().getDesignation())
                    .standard(product.getProductStandard() == null
                            ? null
                            : product.getProductStandard().getDesignation())
                    .build();
        }
    }

    @Transactional
    public ProductDTO add(ProductDTO productDTO) {
        boolean check = true;

        for (Product product : productRepository.findAll()) {
            if (product.getName().equals(productDTO.getName())
                    && product.getSteelGrade().getDesignation().equals(productDTO.getSteelGrade()))
                check = false;
        }

        if (check) {
            return toDTO(productRepository.saveAndFlush(fromDTO(productDTO)));
        } else return null;
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        long id = productDTO.getId();
        if(id > 0L) {
            boolean check = true;

            for (Product product : productRepository.findAll()) {
                if (product.getName().equals(productDTO.getName())
                        && product.getSteelGrade().getDesignation().equals(productDTO.getSteelGrade()))
                    check = false;
            }

            if (check) {
                productDTO.setManufacturers(productRepository
                        .getOne(id)
                        .getManufacturers()
                        .stream()
                        .map(Manufacturer::getId)
                        .collect(Collectors.toList()));
                return toDTO(productRepository.saveAndFlush(fromDTO(productDTO)));
            } else return null;
        } else return null;
    }

    @Transactional
    public void delete(long id) {
        Product product = productRepository.getOne(id);
        for (Manufacturer manufacturer : manufacturerRepository.findAll()) {
            if (manufacturer.getManufacturedProducts().contains(product)) {
                manufacturer.getManufacturedProducts().remove(product);
                manufacturerRepository.saveAndFlush(manufacturer);
            }
        }
        productRepository.deleteById(id);
    }

    public ProductDTO getOne(long id) {
        return toDTO(productRepository.getOne(id));
    }

    public List<ProductDTO> findAll() {
        return productRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findByName(String name) {
        return productRepository
                .findByName(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findByType(String type) {
        return productRepository
                .findByType(type)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findByNameAndSteelGradeDesignation(String name, String steelGradeDesignation) {
        return toDTO(productRepository.findByNameAndSteelGradeDesignation(name, steelGradeDesignation));
    }

    public List<ProductDTO> findByTypeAndSteelGradeDesignation(String type, String steelGradeDesignation) {
        return productRepository.findByTypeAndSteelGradeDesignation(type, steelGradeDesignation)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findBySteelGradeAndProductStandard(String steelGradeDesignation,
                                                                                     String standardDesignation) {
        return productRepository
                .findBySteelGradeDesignationAndProductStandardDesignation(steelGradeDesignation, standardDesignation)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
