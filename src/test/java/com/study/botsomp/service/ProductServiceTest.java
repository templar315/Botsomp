package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.dto.ProductDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class ProductServiceTest extends BaseDomainTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    private void addUp() {
        ProductDTO product = ProductDTO.builder()
                .name("Steel hot-rolled channel #10")
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
                        .findByName("Steel hot-rolled channel #10")
                        .get(0)
                        .getId())))
                .build();

        manufacturerService.add(manufacturer);
    }

    @Test
    public void add() {
        ProductDTO product = ProductDTO.builder()
                .name("Steel hot-rolled channel #8")
                .type("Channel bars")
                .steelGrade("St3sp")
                .standard("GOST380-2005")
                .build();

        productService.add(product);

        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #8", "St3sp"))
                .isNotNull();
    }

    @Test
    public void update() {
        addUp();
        ProductDTO productDTO = productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #10", "St3sp");
        productDTO.setType("New channel bar");
        productService.update(productDTO);

        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #10", "St3sp")
                .getType())
                .isEqualTo("New channel bar");
    }

    @Test
    public void delete() {
        addUp();
        productService.delete(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #10", "St3sp")
                .getId());

        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #10", "St3sp"))
                .isNull();
    }

    @Test
    public void getOne() {
        assertThat(productService.getOne(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "St3sp")
                .getId()))
                .isNotNull();
    }

    @Test
    public void findAll() {
        assertThat(productService.findAll()).hasSize(1);
    }

    @Test
    public void findByName() {
        assertThat(productService.findByName("Steel hot-rolled channel #5")).hasSize(1);
    }

    @Test
    public void findByType() {
        assertThat(productService.findByType("Channel bars")).hasSize(1);
    }

    @Test
    public void findByNameAndSteelGradeDesignation() {
        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "St3sp"))
                .isNotNull();
    }

    @Test
    public void findByTypeAndSteelGradeDesignation() {
        assertThat(productService
                .findByTypeAndSteelGradeDesignation("Channel bars", "St3sp"))
                .hasSize(1);
    }

    @Test
    public void findBySteelGradeAndProductStandard() {
        assertThat(productService
                .findBySteelGradeAndProductStandard("St3sp", "GOST380-2005"))
                .hasSize(1);
    }

}
