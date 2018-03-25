package com.study.botsomp.service.implementations.main;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.repository.ContactDetailsRepository;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.repository.ProductRepository;
import com.study.botsomp.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    private final ContactDetailsRepository contactDetailsRepository;

    private final ProductRepository productRepository;

    private Manufacturer fromDTO(ManufacturerDTO manufacturerDTO) {
        return Manufacturer.builder()
                .id(manufacturerDTO.getId())
                .name(manufacturerDTO.getName())
                .country(manufacturerDTO.getCountry())
                .region(manufacturerDTO.getRegion())
                .city(manufacturerDTO.getCity())
                .address(manufacturerDTO.getAddress())
                .manufacturedProducts(manufacturerDTO.getManufacturedProducts() == null
                        ? null
                        : productRepository.findAllById(manufacturerDTO.getManufacturedProducts()))
                .contactDetails(manufacturerDTO.getContactDetails() == null
                        ? null
                        : contactDetailsRepository.getOne(manufacturerDTO.getContactDetails()))
                .build();
    }

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        return ManufacturerDTO.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .country(manufacturer.getCountry())
                .region(manufacturer.getRegion())
                .city(manufacturer.getCity())
                .address(manufacturer.getAddress())
                .manufacturedProducts(manufacturer.getManufacturedProducts() == null
                        ? null
                        : manufacturer
                        .getManufacturedProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList()))
                .contactDetails(manufacturer.getContactDetails() == null
                        ? null
                        : manufacturer
                        .getContactDetails()
                        .getId())
                .build();
    }

    public void addOrUpdate(ManufacturerDTO manufacturerDTO) {
        manufacturerRepository.save(fromDTO(manufacturerDTO));
    }

    public void delete(Long id) {
        manufacturerRepository.save(manufacturerRepository.getOne(id).toBuilder().contactDetails(null).build());
        contactDetailsRepository.delete(contactDetailsRepository.findByManufacturerId(id));
        manufacturerRepository.deleteById(id);
    }

    public void delete(String name) {
        manufacturerRepository.save(manufacturerRepository.findByName(name).toBuilder().manufacturedProducts(null).build());
        contactDetailsRepository.delete(contactDetailsRepository.findByManufacturerName(name));
        manufacturerRepository.delete(manufacturerRepository.findByName(name));
    }

    public ManufacturerDTO getOne(Long id) {
        return toDTO(manufacturerRepository.getOne(id));
    }

    public boolean existsById(Long id) {
        return manufacturerRepository.existsById(id);
    }

    public List<ManufacturerDTO> findAll() {
        return manufacturerRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ManufacturerDTO findByName(String name) {
        Manufacturer manufacturer = manufacturerRepository.findByName(name);
        if(manufacturer != null) {
            return toDTO(manufacturerRepository.findByName(name));
        } else return null;
    }

    public List<ManufacturerDTO> findByCountry(String country) {
        return manufacturerRepository
                .findByCountry(country)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ManufacturerDTO> findByCountryAndRegion(String country, String region) {
        return manufacturerRepository
                .findByCountryAndRegion(country, region)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ManufacturerDTO> findByCity(String city) {
        return manufacturerRepository
                .findByCity(city)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
