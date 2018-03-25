package com.study.botsomp.service;

import com.study.botsomp.dto.ContactDetailsDTO;

import java.util.List;

public interface ContactDetailsService {

    boolean addOrUpdate(ContactDetailsDTO contactDetailsDTO);
    void delete(long id);
    ContactDetailsDTO getOne(long id);
    boolean existsById(long id);
    List<ContactDetailsDTO> findAll();
    ContactDetailsDTO findByManufacturerId(long id);
    ContactDetailsDTO findByManufacturerName(String name);

}
