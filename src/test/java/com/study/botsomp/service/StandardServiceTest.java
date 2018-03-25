package com.study.botsomp.service;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.dto.StandardDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan
public class StandardServiceTest extends BaseDomainTest {

    @Autowired
    private StandardService standardService;

    @Test
    public void addOrUpdate() {
        standardService.addOrUpdate(StandardDTO.builder()
                .designation("GOST8240-95")
                .build());

        assertThat(standardService.findByDesignation("GOST8240-95")).isNotNull();

        standardService.addOrUpdate(standardService
                .findByDesignation("GOST8240-95")
                .toBuilder()
                .designation("GOST8240-2005")
                .build());

        assertThat(standardService.findByDesignation("GOST8240-2005")).isNotNull();
        assertThat(standardService.findAll()).hasSize(5);
    }

    @Test
    public void deleteById() {
        assertThat(standardService.delete(standardService.findByDesignation("GOST8509-93").getId())).isTrue();
        assertThat(standardService.findByDesignation("GOST8509-93")).isNull();
    }

    @Test
    public void deleteByDesignation() {
        assertThat(standardService.delete("GOST8509-93")).isTrue();
        assertThat(standardService.findByDesignation("GOST8509-93")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(standardService.getOne(standardService.findByDesignation("GOST8509-93").getId())).isNotNull();
    }

    @Test
    public void findAll() {
        assertThat(standardService.findAll())
                .isNotEmpty()
                .hasSize(4);
    }

    @Test
    public void findByDesignation() {
        assertThat(standardService.findByDesignation("GOST8509-93")).isNotNull();
        assertThat(standardService.findByDesignation("GOST8509-93").getDesignation()).isEqualTo("GOST8509-93");
    }
}
