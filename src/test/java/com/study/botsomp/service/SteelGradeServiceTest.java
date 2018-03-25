package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.SteelGradeDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class SteelGradeServiceTest extends BaseDomainTest {

    @Autowired
    private SteelGradeService steelGradeService;

    @Test
    public void addOrUpdate() {
        steelGradeService.addOrUpdate(SteelGradeDTO
                .builder()
                .designation("St2sp")
                .gradeStandard("GOST380-2005")
                .build());

        assertThat(steelGradeService.findByDesignation("St2sp")).isNotNull();

        steelGradeService.addOrUpdate(steelGradeService
                .findByDesignation("St2sp")
                .toBuilder()
                .gradeStandard("GOST5058-65")
                .build());

        assertThat(steelGradeService.findByDesignation("St2sp").getGradeStandard()).isEqualTo("GOST5058-65");
    }

    @Test
    public void deleteById() {
        assertThat(steelGradeService.delete(steelGradeService.findByDesignation("St5sp").getId())).isTrue();
        assertThat(steelGradeService.findByDesignation("St5sp")).isNull();
    }

    @Test
    public void deleteByDesignation() {
        assertThat(steelGradeService.delete("St5sp")).isTrue();
        assertThat(steelGradeService.findByDesignation("St5sp")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(steelGradeService.getOne(steelGradeService.findByDesignation("St5sp").getId())).isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(steelGradeService.existsById(steelGradeService.findByDesignation("St5sp").getId())).isTrue();
    }

    @Test
    public void findAll() {
        assertThat(steelGradeService.findAll()).hasSize(4);
    }

    @Test
    public void findByDesignation() {
        assertThat(steelGradeService.findByDesignation("St5sp")).isNotNull();
    }
}
