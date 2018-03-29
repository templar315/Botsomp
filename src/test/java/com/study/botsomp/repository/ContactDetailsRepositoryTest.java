package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.ContactDetails;
import com.study.botsomp.domain.Manufacturer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactDetailsRepositoryTest extends BaseDomainTest {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void add() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .build();

        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("dmz@com.ua")
                .manufacturer(manufacturer)
                .build();

        manufacturer.setContactDetails(contactDetails);
        contactDetailsRepository.saveAndFlush(contactDetails);

        assertThat(contactDetailsRepository.findByManufacturerName("Ilyich Plant").getId()).isNotNull();
    }

    @Test
    public void update() {
        ContactDetails contactDetails = contactDetailsRepository.findByManufacturerName("Azovstal");
        contactDetails.setFirstName("Valery");
        contactDetails.setLastName("Rusty");
        System.out.println(contactDetails);
        contactDetailsRepository.save(contactDetails);

        assertThat(contactDetailsRepository.findByManufacturerName("Azovstal").getFirstName()).isEqualTo("Valery");
        assertThat(contactDetailsRepository.findByManufacturerName("Azovstal").getLastName()).isEqualTo("Rusty");
    }

    @Test
    public void addAll() {
        List<ContactDetails> contactDetails = new ArrayList<>();

        ContactDetails contact = ContactDetails.builder()
                .id(100L)
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("dmz@com.ua")
                .build();
        contactDetails.add(contact);

        ContactDetails contact2 = ContactDetails.builder()
                .id(101L)
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("dmz@com.ua")
                .build();
        contactDetails.add(contact2);
        contactDetailsRepository.saveAll(contactDetails);

        assertThat(contactDetailsRepository.getOne(100L)).isNotNull();
        assertThat(contactDetailsRepository.getOne(101L)).isNotNull();
    }

    @Test
    public void findById() {
        assertThat(contactDetailsRepository.findById(
                contactDetailsRepository.findByManufacturerName("Azovstal").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(contactDetailsRepository.existsById(
                contactDetailsRepository.findByManufacturerName("Azovstal").getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        System.out.println(contactDetailsRepository.findAll());
        assertThat(contactDetailsRepository.findAll()).hasSize(1);
    }

    @Test
    public void findAllById() {
        List<Long> ids = new ArrayList<>(Arrays.asList(contactDetailsRepository
                .findByManufacturerName("Azovstal")
                .getId()));

        assertThat(contactDetailsRepository.findAllById(ids)).hasSize(1);
    }

    @Test
    public void count() {
        assertThat(contactDetailsRepository.count()).isEqualTo(1);
    }

    @Test
    public void getOne() {
        assertThat(contactDetailsRepository.getOne(
                contactDetailsRepository.findByManufacturerName("Azovstal").getId()).getFirstName())
                .isEqualTo("Vladimir")
                .isNotNull();
    }

    @Test
    public void findByManufacturerId() {
        ContactDetails contactDetails = contactDetailsRepository
                .findByManufacturerId(manufacturerRepository
                        .findByName("Azovstal")
                        .getId());

        assertThat(contactDetails.getFirstName()).isEqualTo("Vladimir");
        assertThat(contactDetails.getLastName()).isEqualTo("Drobyshev");
        assertThat(contactDetails.getPhone()).isEqualTo(380998541212L);
    }

    @Test
    public void findByManufacturerName() {
        ContactDetails contactDetails = contactDetailsRepository.findByManufacturerName("Azovstal");

        assertThat(contactDetails.getFirstName()).isEqualTo("Vladimir");
        assertThat(contactDetails.getLastName()).isEqualTo("Drobyshev");
        assertThat(contactDetails.getPhone()).isEqualTo(380998541212L);
    }

}
