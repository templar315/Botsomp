package com.study.botsomp.service;

import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.Standard;
import com.study.botsomp.domain.SteelGrade;
import com.study.botsomp.dto.StandardDTO;
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
public class StandardService {

    private final StandardRepository standardRepository;

    private final ProductRepository productRepository;

    private final SteelGradeRepository steelGradeRepository;

    private Standard fromDTO(StandardDTO standardDTO) {
        if(standardDTO == null) {
            return null;
        } else {
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
    }

    private StandardDTO toDTO(Standard standard) {
        if(standard == null) {
            return null;
        } else {
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
    }

    @Transactional
    public StandardDTO add(StandardDTO standardDTO) {
        return toDTO(standardRepository.saveAndFlush(fromDTO(standardDTO)));
    }

    @Transactional
    public StandardDTO update(StandardDTO standardDTO) {
        long id = standardDTO.getId();
        if(id > 0L) {
            return toDTO(standardRepository
                    .saveAndFlush(standardRepository
                            .getOne(id)
                            .toBuilder()
                            .designation(standardDTO.getDesignation())
                            .build()));
        } else return null;
    }

    @Transactional
    public void delete(long id) {
        Standard standard = standardRepository.getOne(id);
        if(standard != null) {
            for (Product product : productRepository.findAll()) {
                if (product.getProductStandard() == standard) {
                    productRepository.save(product.toBuilder().productStandard(null).build());
                }
            }
            for (SteelGrade steelGrade : steelGradeRepository.findAll()) {
                if (steelGrade.getGradeStandard() == standard) {
                    steelGradeRepository.save(steelGrade.toBuilder().gradeStandard(null).build());
                }
            }
        }
        standardRepository.deleteById(id);
    }

    public StandardDTO getOne(long id) {
        return toDTO(standardRepository.getOne(id));
    }

    public List<StandardDTO> findAll() {
        return standardRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StandardDTO findByDesignation(String designation) {
        return toDTO(standardRepository.findByDesignation(designation));
    }

}
