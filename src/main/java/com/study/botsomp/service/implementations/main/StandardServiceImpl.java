package com.study.botsomp.service.implementations.main;

import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.Standard;
import com.study.botsomp.domain.SteelGrade;
import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.repository.ProductRepository;
import com.study.botsomp.repository.StandardRepository;
import com.study.botsomp.repository.SteelGradeRepository;
import com.study.botsomp.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class StandardServiceImpl implements StandardService {

    private final StandardRepository standardRepository;

    private final ProductRepository productRepository;

    private final SteelGradeRepository steelGradeRepository;

    private Standard fromDTO(StandardDTO standardDTO) {
        return Standard.builder()
                .id(standardDTO.getId())
                .designation(standardDTO.getDesignation())
                .products(standardDTO.getProducts() == null
                        ? null
                        : productRepository.findAllById(standardDTO.getProducts()))
                .steelGrades(standardDTO.getSteelGrades() == null
                        ? null
                        : steelGradeRepository.findAllById(standardDTO.getSteelGrades()))
                .build();
    }

    private StandardDTO toDTO(Standard standard) {
        return StandardDTO.builder()
                .id(standard.getId())
                .designation(standard.getDesignation())
                .products(standard.getProducts() == null
                        ? null
                        : standard.getProducts()
                            .stream()
                            .map(Product::getId)
                            .collect(Collectors.toList()))
                .steelGrades(standard.getSteelGrades() == null
                        ? null
                        : standard.getSteelGrades()
                            .stream()
                            .map(SteelGrade::getId)
                            .collect(Collectors.toList()))
                .build();
    }

    public void addOrUpdate(StandardDTO standardDTO) {
        standardRepository.save(fromDTO(standardDTO));
    }

    public boolean delete(Long id) {
        Standard standard = standardRepository.getOne(id);
        if(standard != null) {
            clearStandard(standard);
            standardRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public boolean delete(String designation) {
        Standard standard = standardRepository.findByDesignation(designation);
        if(standard != null) {
            clearStandard(standard);
            standardRepository.delete(standard);
            return true;
        }
        else return false;
    }

    public StandardDTO getOne(Long id) {
        return toDTO(standardRepository.getOne(id));
    }

    public boolean existsById(Long id) {
        return standardRepository.existsById(id);
    }

    public List<StandardDTO> findAll() {
        return standardRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StandardDTO findByDesignation(String designation) {
        Standard standard = standardRepository.findByDesignation(designation);
        if(standard != null) return toDTO(standard);
        else return null;
    }

    private void clearStandard(Standard standard) {
        for(Product product : productRepository.findAll()) {
            if(product.getProductStandard() == standard) {
                productRepository.save(product.toBuilder().productStandard(null).build());
            }
        }
        for(SteelGrade steelGrade : steelGradeRepository.findAll()) {
            if(steelGrade.getGradeStandard() == standard) {
                steelGradeRepository.save(steelGrade.toBuilder().gradeStandard(null).build());
            }
        }
    }

}
