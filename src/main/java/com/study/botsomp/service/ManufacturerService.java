package com.study.botsomp.service;

import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.repository.ContactDetailsRepository;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    private final ContactDetailsRepository contactDetailsRepository;

    private final ProductRepository productRepository;

    private Manufacturer fromDTO(ManufacturerDTO manufacturerDTO) {
        if(manufacturerDTO == null) {
            return null;
        } else {
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
    }

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        if(manufacturer == null) {
            return null;
        } else {
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
    }

    @Transactional
    public ManufacturerDTO add(ManufacturerDTO manufacturerDTO) {
        if (!manufacturerRepository.existsById(manufacturerDTO.getId())) {
            return toDTO(manufacturerRepository.saveAndFlush(fromDTO(manufacturerDTO)));
        } return null;
    }

    @Transactional
    public ManufacturerDTO update(ManufacturerDTO manufacturerDTO) {
        if(manufacturerRepository.existsById(manufacturerDTO.getId())) {
            return toDTO(manufacturerRepository.saveAndFlush(
                    manufacturerRepository.getOne(manufacturerDTO.getId())
                            .toBuilder()
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
                            .build()));
        } return null;
    }

    @Transactional
    public boolean delete(long id) {
        if(manufacturerRepository.existsById(id)) {
            Manufacturer manufacturer = manufacturerRepository.getOne(id);
            for(Product product : manufacturer.getManufacturedProducts()) {
                product.getManufacturers().remove(manufacturer);
                productRepository.saveAndFlush(product);
            }
            manufacturerRepository.deleteById(id);
            return true;
        } else return false;
    }

    public ManufacturerDTO getOne(long id) {
        return toDTO(manufacturerRepository.getOne(id));
    }

    public List<ManufacturerDTO> findAll() {
        return manufacturerRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ManufacturerDTO findByName(String name) {
        return toDTO(manufacturerRepository.findByName(name));
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
