package com.study.botsomp.service.implementations.main;

import com.study.botsomp.domain.ContactDetails;
import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.repository.ContactDetailsRepository;
import com.study.botsomp.repository.ManufacturerRepository;
import com.study.botsomp.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ManufacturerRepository manufacturerRepository;

    private final ContactDetailsRepository contactDetailsRepository;


    private ContactDetails fromDTO(ContactDetailsDTO contactDetailsDTO) {
        return ContactDetails.builder()
                .id(contactDetailsDTO.getId())
                .firstName(contactDetailsDTO.getFirstName())
                .lastName(contactDetailsDTO.getLastName())
                .position(contactDetailsDTO.getPosition())
                .phone(contactDetailsDTO.getPhone())
                .email(contactDetailsDTO.getEmail())
                .manufacturer(contactDetailsDTO.getManufacturer() == 0
                        ? null
                        : manufacturerRepository.getOne(contactDetailsDTO.getManufacturer()))
                .build();
    }

    private ContactDetailsDTO toDTO(ContactDetails contactDetails) {
        return ContactDetailsDTO.builder()
                .id(contactDetails.getId())
                .firstName(contactDetails.getFirstName())
                .lastName(contactDetails.getLastName())
                .position(contactDetails.getPosition())
                .phone(contactDetails.getPhone())
                .email(contactDetails.getEmail())
                .manufacturer(contactDetails.getManufacturer().getId())
                .build();
    }

    public boolean addOrUpdate(ContactDetailsDTO contactDetailsDTO) {
        if(contactDetailsDTO.getManufacturer() > 0L) {
            contactDetailsRepository.save(fromDTO(contactDetailsDTO));
            return true;
        } else return false;
    }

    public void delete(long id) {
        contactDetailsRepository.deleteById(id);
    }

    public ContactDetailsDTO getOne(long id) {
        return toDTO(contactDetailsRepository.getOne(id));
    }

    public boolean existsById(long id) {
        return contactDetailsRepository.existsById(id);
    }

    public List<ContactDetailsDTO> findAll() {
        return contactDetailsRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContactDetailsDTO findByManufacturerId(long id) {
        if(contactDetailsRepository.findByManufacturerId(id) != null) {
            return toDTO(contactDetailsRepository.findByManufacturerId(id));
        } else return null;
    }

    public ContactDetailsDTO findByManufacturerName(String name) {
        if(contactDetailsRepository.findByManufacturerName(name) != null) {
            return toDTO(contactDetailsRepository.findByManufacturerName(name));
        } else return null;
    }

}
