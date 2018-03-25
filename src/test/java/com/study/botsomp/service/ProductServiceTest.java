package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ProductDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class ProductServiceTest extends BaseDomainTest {

    @Autowired
    private ProductService productService;

    @Test
    public void addOrUpdate() {
        productService.addOrUpdate(ProductDTO.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade("14G")
                .standard("GOST8240-89")
                .build());

        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "14G"))
                .isNotNull();

        productService.addOrUpdate(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "14G")
                .toBuilder()
                .standard(null)
                .build());

        assertThat(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "14G")
                .getStandard())
                .isNull();
    }

    @Test
    public void delete() {
        assertThat(productService.delete(
                productService.findByNameAndSteelGradeDesignation("Corner steel hot-rolled equipotential #3",
                        "35GS").getId())).isTrue();

        assertThat(productService.findByNameAndSteelGradeDesignation("Corner steel hot-rolled equipotential #3",
                "35GS")).isNull();
    }

    @Test
    public void getOne() {
        ProductDTO productDTO =
                productService.findByNameAndSteelGradeDesignation("Corner steel hot-rolled equipotential #3",
                "35GS");
        assertThat(productService.getOne(productDTO.getId())).isEqualTo(productDTO);
        assertThat(productService.getOne(productDTO.getId())).isNotNull();
    }

    @Test
    public void existsById() {
        ProductDTO productDTO =
                productService.findByNameAndSteelGradeDesignation("Corner steel hot-rolled equipotential #3",
                        "35GS");
        assertThat(productService.existsById(productDTO.getId())).isTrue();
    }

    @Test
    public void findAll() {
        assertThat(productService.findAll()).hasSize(4);
    }

    @Test
    public void findByName() {
        assertThat(productService.findByName("Steel hot-rolled channel #5"))
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void findByType() {
        assertThat(productService.findByType("Channel bars"))
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void findByNameAndSteelGradeDesignation() {
        assertThat(productService.findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5",
                "St5sp")).isNotNull();
    }

    @Test
    public void findByTypeAndSteelGradeDesignation() {
        assertThat(productService.findByTypeAndSteelGradeDesignation("Corners", "14G"))
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void findBySteelGradeAndProductStandard() {
        assertThat(productService.findBySteelGradeAndProductStandard("14G", "GOST8509-93"))
                .isNotEmpty()
                .hasSize(1);
    }

}
