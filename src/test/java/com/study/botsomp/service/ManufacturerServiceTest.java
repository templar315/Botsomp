package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.dto.ProductDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class ManufacturerServiceTest extends BaseDomainTest {

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductService productService;

    private void addUp() {
        ProductDTO product = ProductDTO.builder()
                .name("Steel hot-rolled channel #8")
                .type("Channel bars")
                .steelGrade("St3sp")
                .standard("GOST380-2005")
                .build();

        productService.add(product);

        ManufacturerDTO manufacturer = ManufacturerDTO.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .manufacturedProducts(new ArrayList<>(Arrays.asList(productService
                        .findByName("Steel hot-rolled channel #8")
                        .get(0)
                        .getId())))
                .build();

        manufacturerService.add(manufacturer);
    }

    @Test
    public void add() {
        addUp();

        assertThat(manufacturerService.findByName("Ilyich Plant")).isNotNull();
        assertThat(manufacturerService.findByName("Ilyich Plant").getCity()).isEqualTo("Donetsk");
    }

    @Test
    public void update() {
        addUp();

        ManufacturerDTO manufacturerDTO = manufacturerService.findByName("Ilyich Plant");
        manufacturerDTO.setName("Lenin Plant");
        manufacturerService.update(manufacturerDTO);

        assertThat(manufacturerService.findByName("Lenin Plant")).isNotNull();
        assertThat(manufacturerService.findAll()).hasSize(2);
    }

    @Test
    public void delete() {
        manufacturerService.delete(manufacturerService.findByName("Azovstal").getId());
        assertThat(manufacturerService.findByName("Azovstal")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(manufacturerService.getOne(manufacturerService.findByName("Azovstal").getId())
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void findAll() {
        assertThat(manufacturerService.findAll()).hasSize(1);
    }

    @Test
    public void findByCountry() {
        List<ManufacturerDTO> manufacturers = manufacturerService.findByCountry("Ukraine");
        assertThat(manufacturers).hasSize(1);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Azovstal");
    }

    @Test
    public void findByCountryAndRegion() {
        List<ManufacturerDTO> manufacturers =
                manufacturerService.findByCountryAndRegion("Ukraine", "Donetsk region");
        assertThat(manufacturers).hasSize(1);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Azovstal");
    }

    @Test
    public void findByCity() {
        List<ManufacturerDTO> manufacturers =
                manufacturerService.findByCity("Mariupol");
        assertThat(manufacturers).hasSize(1);
        assertThat(manufacturers.get(0).getName()).isEqualTo("Azovstal");
    }

}
