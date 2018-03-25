package com.study.botsomp.service.implementations.main;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.SteelGrade;
import com.study.botsomp.dto.SteelGradeDTO;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.repository.ProductRepository;
import com.study.botsomp.repository.StandardRepository;
import com.study.botsomp.repository.SteelGradeRepository;
import com.study.botsomp.service.SteelGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class SteelGradeServiceImpl implements SteelGradeService {

    private final SteelGradeRepository steelGradeRepository;

    private final StandardRepository standardRepository;

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private SteelGrade fromDTO(SteelGradeDTO steelGradeDTO) {
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

    private SteelGradeDTO toDTO(SteelGrade steelGrade) {
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

    public void addOrUpdate(SteelGradeDTO steelGradeDTO) {
        steelGradeRepository.save(fromDTO(steelGradeDTO));
    }

    public boolean delete(Long id) {
        SteelGrade steelGrade = steelGradeRepository.getOne(id);
        if(steelGrade != null) {
            clearSteelGrade(steelGrade);
            steelGradeRepository.deleteById(id);
            return true;
        } else return false;
    }

    public boolean delete(String designation) {
        SteelGrade steelGrade = steelGradeRepository.findByDesignation(designation);
        if(steelGrade != null) {
            clearSteelGrade(steelGrade);
            steelGradeRepository.delete(steelGrade);
            return true;
        } else return false;
    }

    public SteelGradeDTO getOne(Long id) {
        return toDTO(steelGradeRepository.getOne(id));
    }

    public boolean existsById(Long id) {
        return steelGradeRepository.existsById(id);
    }

    public List<SteelGradeDTO> findAll() {
        return steelGradeRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SteelGradeDTO findByDesignation(String designation) {
        SteelGrade steelGrade = steelGradeRepository.findByDesignation(designation);
        if(steelGrade != null) return toDTO(steelGrade);
        else return null;
    }

    private void clearSteelGrade(SteelGrade steelGrade) {
        for(Product product : productRepository.findAll()) {
            for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
                if(manufacturer.getManufacturedProducts().contains(product)) {
                    manufacturer.getManufacturedProducts().remove(product);
                }
            }
            productRepository.delete(product);
        }
    }
}
