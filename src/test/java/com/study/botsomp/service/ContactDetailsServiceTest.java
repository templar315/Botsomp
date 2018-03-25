package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.dto.ManufacturerDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class ContactDetailsServiceTest extends BaseDomainTest {

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Test
    public void addOrUpdate() {
        manufacturerService.addOrUpdate(ManufacturerDTO.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .build());

        contactDetailsService.addOrUpdate(ContactDetailsDTO.builder()
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("ilyichplant@com.ua")
                .manufacturer(manufacturerService.findByName("Ilyich Plant").getId())
                .build());

        assertThat(contactDetailsService.findByManufacturerName("Ilyich Plant")).isNotNull();

        contactDetailsService.addOrUpdate(contactDetailsService
                .findByManufacturerName("Ilyich Plant")
                .toBuilder()
                .email("newemail@gmail.com")
                .build());

        assertThat(contactDetailsService.findByManufacturerName("Ilyich Plant").getEmail())
                .isEqualTo("newemail@gmail.com");
    }

    @Test
    public void addOrUpdateWithoutManufacturer() {
        assertThat(contactDetailsService.addOrUpdate(ContactDetailsDTO.builder()
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("ilyichplant@com.ua")
                .build()))
                .isFalse();
    }

    @Test
    public void delete() {
        contactDetailsService.delete(contactDetailsService.findByManufacturerName("Donetsk Metallurgical Plant").getId());
        assertThat(contactDetailsService.findByManufacturerName("Donetsk Metallurgical Plant")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(contactDetailsService.getOne(contactDetailsService.findByManufacturerName("Azovstal").getId()))
                .isNotNull();
        assertThat(contactDetailsService.getOne(contactDetailsService.findByManufacturerName("Azovstal").getId())
                .getFirstName())
                .isEqualTo("Vladimir");
    }

    @Test
    public void findAll() {
        assertThat(contactDetailsService.findAll()).hasSize(2).isNotEmpty();
    }

    @Test
    public void findByManufacturerId() {
        assertThat(contactDetailsService.findByManufacturerId(manufacturerService.findByName("Azovstal").getId()))
                .isNotNull();
        assertThat(contactDetailsService.findByManufacturerId(manufacturerService.findByName("Azovstal").getId())
                .getFirstName())
                .isEqualTo("Vladimir");
    }

    @Test
    public void findByManufacturerName() {
        assertThat(contactDetailsService.findByManufacturerName("Azovstal")).isNotNull();
        assertThat(contactDetailsService.findByManufacturerName("Azovstal").getFirstName()).isEqualTo("Vladimir");
    }
}
