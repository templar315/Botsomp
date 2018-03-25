package com.study.botsomp.service;

import com.study.botsomp.dto.ManufacturerDTO;

import java.util.List;

public interface ManufacturerService {

    void addOrUpdate(ManufacturerDTO manufacturerDTO);
    void delete(Long id);
    void delete(String name);
    ManufacturerDTO getOne(Long id);
    boolean existsById(Long id);
    List<ManufacturerDTO> findAll();
    ManufacturerDTO findByName(String name);
    List<ManufacturerDTO> findByCountry(String country);
    List<ManufacturerDTO> findByCountryAndRegion(String country, String region);
    List<ManufacturerDTO> findByCity(String city);

}
