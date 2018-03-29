package com.study.botsomp.service;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.SteelGrade;
import com.study.botsomp.dto.SteelGradeDTO;
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
public class SteelGradeService {

    private final SteelGradeRepository steelGradeRepository;

    private final StandardRepository standardRepository;

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private SteelGrade fromDTO(SteelGradeDTO steelGradeDTO) {
        if(steelGradeDTO == null) {
            return null;
        } else {
            return SteelGrade.builder()
                    .id(steelGradeDTO.getId())
                    .designation(steelGradeDTO.getDesignation())
                    .products(steelGradeDTO.getProducts() == null
                            ? null
                            : productRepository.findAllById(steelGradeDTO.getProducts()))
                    .gradeStandard(steelGradeDTO.getGradeStandard() == null
                            ? null
                            : standardRepository.findByDesignation(steelGradeDTO.getGradeStandard()))
                    .build();
        }
    }

    private SteelGradeDTO toDTO(SteelGrade steelGrade) {
        if(steelGrade == null) {
            return null;
        } else {
            return SteelGradeDTO.builder()
                    .id(steelGrade.getId())
                    .designation(steelGrade.getDesignation())
                    .products(steelGrade.getProducts() == null
                            ? null
                            : steelGrade.getProducts().stream().map(Product::getId).collect(Collectors.toList()))
                    .gradeStandard(steelGrade.getGradeStandard() == null
                            ? null
                            : steelGrade.getGradeStandard().getDesignation())
                    .build();
        }
    }

    @Transactional
    public SteelGradeDTO add(SteelGradeDTO steelGradeDTO) {
        if(!steelGradeRepository.existsById(steelGradeDTO.getId())) {
            return toDTO(steelGradeRepository.saveAndFlush(fromDTO(steelGradeDTO)));
        } else return null;
    }

    @Transactional
    public SteelGradeDTO update(SteelGradeDTO steelGradeDTO) {
        if(steelGradeRepository.existsById(steelGradeDTO.getId())) {
            return toDTO(steelGradeRepository.saveAndFlush(
                    steelGradeRepository.getOne(steelGradeDTO.getId()).toBuilder().designation(steelGradeDTO.getDesignation())
                            .products(steelGradeDTO.getProducts() == null
                                    ? null
                                    : productRepository.findAllById(steelGradeDTO.getProducts()))
                            .gradeStandard(steelGradeDTO.getGradeStandard() == null
                                    ? null
                                    : standardRepository.findByDesignation(steelGradeDTO.getGradeStandard()))
                            .build()));
        } else return null;
    }

    @Transactional
    public boolean delete(long id) {
        if(steelGradeRepository.existsById(id)) {
            SteelGrade steelGrade = steelGradeRepository.getOne(id);
            for(Product product : productRepository.findAll()) {
                if (product.getSteelGrade() == steelGrade) {
                    for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
                        manufacturer.getManufacturedProducts().remove(product);
                        manufacturerRepository.saveAndFlush(manufacturer);
                    }
                    productRepository.deleteById(product.getId());
                }
            }
            steelGradeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public SteelGradeDTO getOne(Long id) {
        return toDTO(steelGradeRepository.getOne(id));
    }

    public List<SteelGradeDTO> findAll() {
        return steelGradeRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SteelGradeDTO findByDesignation(String designation) {
        return toDTO(steelGradeRepository.findByDesignation(designation));
    }

}
