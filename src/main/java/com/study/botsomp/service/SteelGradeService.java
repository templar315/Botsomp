package com.study.botsomp.service;

import com.study.botsomp.dto.SteelGradeDTO;

import java.util.List;

public interface SteelGradeService {

    void addOrUpdate(SteelGradeDTO steelGradeDTO);
    boolean delete(Long id);
    boolean delete(String designation);
    SteelGradeDTO getOne(Long id);
    boolean existsById(Long id);
    List<SteelGradeDTO> findAll();
    SteelGradeDTO findByDesignation(String designation);

}
