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
public class StandardServiceTest extends BaseDomainTest {

    @Autowired
    private StandardService standardService;

    @Autowired
    private SteelGradeService steelGradeService;

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

        assertThat(standardService.findByDesignation("GOST540-2012")).isNotNull();
        assertThat(standardService.findAll()).hasSize(2);
    }

    @Test
    public void update() {
        addUp();
        StandardDTO standardDTO = standardService.findByDesignation("GOST540-2012");
        standardDTO.setDesignation("GOST540-2015");
        standardService.update(standardDTO);

        assertThat(standardService.findByDesignation("GOST540-2015")).isNotNull();
        assertThat(standardService.findAll()).hasSize(2);
    }

    @Test
    public void delete() {
        addUp();
        standardService.delete(standardService.findByDesignation("GOST540-2012").getId());

        assertThat(standardService.findByDesignation("GOST540-2012")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(standardService.getOne(standardService.findByDesignation("GOST380-2005").getId())).isNotNull();
    }

    @Test
    public void findAll() {
        assertThat(standardService.findAll())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void findByDesignation() {
        assertThat(standardService.findByDesignation("GOST380-2005")).isNotNull();
        assertThat(standardService.findByDesignation("GOST380-2005").getDesignation()).isEqualTo("GOST380-2005");
    }

}
