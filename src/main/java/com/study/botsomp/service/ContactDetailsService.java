package com.study.botsomp.service;

import com.study.botsomp.domain.ContactDetails;
import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.repository.ContactDetailsRepository;
import com.study.botsomp.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactDetailsService {

    private final ManufacturerRepository manufacturerRepository;

    private final ContactDetailsRepository contactDetailsRepository;


    private ContactDetails fromDTO(ContactDetailsDTO contactDetailsDTO) {
        if(contactDetailsDTO == null) {
            return null;
        } else {
            return ContactDetails.builder()
                    .id(contactDetailsDTO.getId())
                    .firstName(contactDetailsDTO.getFirstName())
                    .lastName(contactDetailsDTO.getLastName())
                    .position(contactDetailsDTO.getPosition())
                    .phone(contactDetailsDTO.getPhone())
                    .email(contactDetailsDTO.getEmail())
                    .manufacturer(contactDetailsDTO.getManufacturer() <= 0L
                            ? null
                            : manufacturerRepository.getOne(contactDetailsDTO.getManufacturer()))
                    .build();
        }
    }

    private ContactDetailsDTO toDTO(ContactDetails contactDetails) {
        if(contactDetails == null) {
            return null;
        } else {
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
    }

    @Transactional
    public ContactDetailsDTO add(ContactDetailsDTO contactDetailsDTO) {
        return toDTO(contactDetailsRepository.saveAndFlush(fromDTO(contactDetailsDTO)));
    }

    @Transactional
    public ContactDetailsDTO update(ContactDetailsDTO contactDetailsDTO) {
        long id = contactDetailsDTO.getId();
        if(id > 0L) {
            contactDetailsDTO.setManufacturer(contactDetailsRepository
                    .getOne(id)
                    .getManufacturer()
                    .getId());
            return (toDTO(contactDetailsRepository.saveAndFlush(fromDTO(contactDetailsDTO))));
        } else return null;
    }

    @Transactional
    public void delete(long id) {
        contactDetailsRepository.deleteById(id);
    }

    public ContactDetailsDTO getOne(long id) {
        return toDTO(contactDetailsRepository.getOne(id));
    }

    public List<ContactDetailsDTO> findAll() {
        return contactDetailsRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
