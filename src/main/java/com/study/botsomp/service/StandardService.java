package com.study.botsomp.service;

import com.study.botsomp.dto.StandardDTO;

import java.util.List;

public interface StandardService {

    void addOrUpdate(StandardDTO standardDTO);
    boolean delete(Long id);
    boolean delete(String designation);
    boolean existsById(Long id);
    StandardDTO getOne(Long id);
    List<StandardDTO> findAll();
    StandardDTO findByDesignation(String designation);

}
