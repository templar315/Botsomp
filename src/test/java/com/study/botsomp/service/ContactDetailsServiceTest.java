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

    private void addUp() {
        ManufacturerDTO manufacturer = ManufacturerDTO.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .build();

        manufacturerService.add(manufacturer);

        ContactDetailsDTO contactDetails = ContactDetailsDTO.builder()
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("dmz@com.ua")
                .manufacturer(manufacturerService.findByName("Ilyich Plant").getId())
                .build();

        contactDetailsService.add(contactDetails);
    }

    @Test
    public void add() {
        addUp();

        assertThat(contactDetailsService.findAll()).hasSize(2);
        assertThat(contactDetailsService.findAll().get(1).getLastName()).isEqualTo("Menshikov");
    }

    @Test
    public void update() {
        addUp();

        ContactDetailsDTO contactDetailsDTO = contactDetailsService.findAll().get(1);

        contactDetailsDTO.setFirstName("Bogdan");
        contactDetailsService.update(contactDetailsDTO);

        assertThat(contactDetailsService.findAll().get(1).getFirstName()).isEqualTo("Bogdan");
        assertThat(contactDetailsService.findAll()).hasSize(2);
    }

    @Test
    public void delete() {
        contactDetailsService.delete(contactDetailsService.findAll().get(0).getId());
        assertThat(contactDetailsService.findAll()).isEmpty();
    }

    @Test
    public void getOne() {
        ContactDetailsDTO contactDetailsDTO = contactDetailsService.getOne(manufacturerService
                .findByName("Azovstal")
                .getContactDetails());
        assertThat(contactDetailsDTO).isNotNull();
        assertThat(contactDetailsDTO.getFirstName()).isEqualTo("Vladimir");
    }

    @Test
    public void findAll() {
        assertThat(contactDetailsService.findAll()).hasSize(1);
    }

}
