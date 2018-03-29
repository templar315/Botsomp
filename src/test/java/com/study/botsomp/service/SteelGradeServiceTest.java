package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.dto.SteelGradeDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class SteelGradeServiceTest extends BaseDomainTest {

    @Autowired
    private SteelGradeService steelGradeService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private ProductService productService;

    private void addUp() {
        standardService.add(StandardDTO.builder()
                .designation("GOST540-2012")
                .build());

        steelGradeService.add(SteelGradeDTO.builder()
                .designation("St6sp")
                .gradeStandard("GOST540-2012")
                .build());

        productService.add(ProductDTO.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade("St6sp")
                .standard("GOST540-2012")
                .build());
    }

    @Test
    public void add() {
        addUp();

        assertThat(steelGradeService.findByDesignation("St6sp")).isNotNull();
        assertThat(steelGradeService.findAll()).hasSize(2);
    }

    @Test
    public void update() {
        addUp();
        SteelGradeDTO steelGradeDTO = steelGradeService.findByDesignation("St6sp");
        steelGradeDTO.setDesignation("St6");
        steelGradeService.update(steelGradeDTO);

        assertThat(steelGradeService.findByDesignation("St6")).isNotNull();
        assertThat(steelGradeService.findAll()).hasSize(2);
    }

    @Test
    public void delete() {
        addUp();
        steelGradeService.delete(steelGradeService.findByDesignation("St6sp").getId());

        assertThat(steelGradeService.findByDesignation("St6sp")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(steelGradeService.getOne(steelGradeService.findByDesignation("St3sp").getId())).isNotNull();
    }

    @Test
    public void findAll() {
        assertThat(steelGradeService.findAll()).hasSize(1);
    }

    @Test
    public void findByDesignation() {
        assertThat(steelGradeService.findByDesignation("St3sp")).isNotNull();
    }

}
