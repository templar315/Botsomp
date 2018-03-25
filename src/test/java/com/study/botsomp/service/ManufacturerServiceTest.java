package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ManufacturerDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class ManufacturerServiceTest extends BaseDomainTest {

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

        assertThat(manufacturerService.findByName("Ilyich Plant")).isNotNull();

        manufacturerService.addOrUpdate(manufacturerService
                .findByName("Ilyich Plant")
                .toBuilder()
                .address("Str. Lenina, 10")
                .build());

        assertThat(manufacturerService.findByName("Ilyich Plant").getAddress()).isEqualTo("Str. Lenina, 10");
    }

    @Test
    public void deleteById() {
        manufacturerService.delete(manufacturerService.findByName("Azovstal").getId());
        assertThat(manufacturerService.findByName("Azovstal")).isNull();
    }

    @Test
    public void deleteByName() {
        manufacturerService.delete("Azovstal");
        assertThat(manufacturerService.findByName("Azovstal")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(manufacturerService.getOne(manufacturerService.findByName("Azovstal").getId())
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void existsById() {
        assertThat(manufacturerService.existsById(manufacturerService.findByName("Azovstal").getId())).isTrue();
    }

    @Test
    public void findAll() {
        assertThat(manufacturerService.findAll()).hasSize(2);
    }

    @Test
    public void findByCountry() {
        List<ManufacturerDTO> manufacturers = manufacturerService.findByCountry("Ukraine");
        assertThat(manufacturers).hasSize(2);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Donetsk Metallurgical Plant");
        assertThat(manufacturers.get(1).getName()).isEqualTo("Azovstal");
    }

    @Test
    public void findByCountryAndRegion() {
        List<ManufacturerDTO> manufacturers =
                manufacturerService.findByCountryAndRegion("Ukraine", "Donetsk region");
        assertThat(manufacturers).hasSize(2);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Donetsk Metallurgical Plant");
        assertThat(manufacturers.get(1).getName()).isEqualTo("Azovstal");
    }

    @Test
    public void findByCity() {
        List<ManufacturerDTO> manufacturers =
                manufacturerService.findByCity("Mariupol");
        assertThat(manufacturers).hasSize(1);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Azovstal");
    }
}
