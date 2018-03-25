package com.study.botsomp.service.implementations.main;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.repository.ProductRepository;
import com.study.botsomp.repository.StandardRepository;
import com.study.botsomp.repository.SteelGradeRepository;
import com.study.botsomp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final SteelGradeRepository steelGradeRepository;

    private final StandardRepository standardRepository;

    private Product fromDTO(ProductDTO productDTO) {
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

    private ProductDTO toDTO(Product product) {
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

    public boolean addOrUpdate(ProductDTO productDTO) {
        Product product = productRepository
                .findByNameAndSteelGradeDesignation(productDTO.getName(), productDTO.getSteelGrade());
        if (product == null || product.getId() == productDTO.getId()) {
            productRepository.save(fromDTO(productDTO));
            return true;
        } else return false;
    }

    public boolean delete(Long id) {
        Product product = productRepository.getOne(id);
        if(product != null) {
            for (Manufacturer manufacturer : manufacturerRepository.findAll()) {
                if (manufacturer.getManufacturedProducts().contains(product)) {
                    manufacturer.getManufacturedProducts().remove(product);
                }
            }
            productRepository.deleteById(id);
            return true;
        } else return false;
    }

    public ProductDTO getOne(Long id) {
        return toDTO(productRepository.getOne(id));
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
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
        Product product = productRepository.findByNameAndSteelGradeDesignation(name, steelGradeDesignation);
        if(product != null) return toDTO(product);
        else return null;
    }

    public List<ProductDTO> findByTypeAndSteelGradeDesignation(String type, String steelGradeDesignation) {
        List<Product> products = productRepository.findByTypeAndSteelGradeDesignation(type, steelGradeDesignation);
        if(!products.isEmpty()) return
                products
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        else return null;
    }

    public List<ProductDTO> findBySteelGradeAndProductStandard(String steelGradeDesignation,
                                                                                     String standardDesignation) {
        List<Product> products = productRepository
                .findBySteelGradeDesignationAndProductStandardDesignation(steelGradeDesignation, standardDesignation);
        if(!products.isEmpty()) return
                products
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        else return null;
    }

}
