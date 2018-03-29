package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.Standard;
import com.study.botsomp.domain.SteelGrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StandardRepositoryTest extends BaseDomainTest {

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private SteelGradeRepository steelGradeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void add() {
        standardRepository.saveAndFlush(Standard.builder()
                .designation("GOST7798-70")
                .build());

        assertThat(standardRepository.findByDesignation("GOST7798-70")).isNotNull();
    }

    @Test
    public void update() {
        Standard standard = standardRepository.findByDesignation("GOST380-2005");
        standard.setDesignation("GOST8240-2018");
        standardRepository.saveAndFlush(standard);

        assertThat(standardRepository.findByDesignation("GOST8240-2018")).isNotNull();
        assertThat(standardRepository.findAll()).hasSize(1);
    }

    @Test
    public void addAll() {
        List<Standard> standards = new ArrayList<>();

        standards.add(Standard.builder()
                .designation("GOST7798-70")
                .build());
        standards.add(Standard.builder()
                .designation("GOST7798-90")
                .build());

        standardRepository.saveAll(standards);

        assertThat(standardRepository.findByDesignation("GOST7798-70")).isNotNull();
        assertThat(standardRepository.findByDesignation("GOST7798-90")).isNotNull();
    }

    @Test
    public void findById() {
        assertThat(standardRepository
                .findById(standardRepository.findByDesignation("GOST380-2005").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(standardRepository
                .existsById(standardRepository.findByDesignation("GOST380-2005").getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        assertThat(standardRepository.findAll()).hasSize(1);
    }

    @Test
    public void findAllById() {
        List<Long> ids = new ArrayList<>();
        ids.add(standardRepository.findByDesignation("GOST380-2005").getId());

        assertThat(standardRepository.findAllById(ids)).hasSize(1);
    }

    @Test
    public void count() {
        assertThat(standardRepository.count()).isEqualTo(1);
    }

    @Test
    public void deleteById() {
        Standard standard = standardRepository.findByDesignation("GOST380-2005");
        clearStandard(standard);
        standardRepository.deleteById(standard.getId());

        assertThat(standardRepository.findByDesignation("GOST380-2005")).isNull();
    }

    @Test
    public void delete() {
        Standard standard = standardRepository.findByDesignation("GOST380-2005");
        clearStandard(standard);
        standardRepository.delete(standard);

        assertThat(standardRepository.findByDesignation("GOST380-2005")).isNull();
    }

    @Test
    public void deleteFromList() {
        List<Standard> standards = new ArrayList<>();
        standards.add(standardRepository.findByDesignation("GOST380-2005"));
        clearStandard(standards.get(0));
        standardRepository.deleteAll(standards);

        assertThat(standardRepository.findByDesignation("GOST380-2005")).isNull();
    }

    @Test
    public void deleteAll() {
        for(Product product : productRepository.findAll()) {
            product.setProductStandard(null);
            productRepository.save(product);
        }
        for(SteelGrade steelGrade : steelGradeRepository.findAll()) {
            steelGrade.setGradeStandard(null);
            steelGradeRepository.save(steelGrade);
        }

        standardRepository.deleteAll();

        assertThat(standardRepository.findAll()).isEmpty();
    }

    @Test
    public void getOne() {
        assertThat(standardRepository.getOne(standardRepository.findByDesignation("GOST380-2005").getId())
                .getDesignation()).isEqualTo("GOST380-2005");
    }

    @Test
    public void findByDesignation() {
        assertThat(standardRepository.findByDesignation("GOST380-2005")).isNotNull();
    }

    private void clearStandard(Standard standard) {
        for(Product product : standard.getProducts()) {
            if(product.getProductStandard() == standard) {
                product.setProductStandard(null);
                productRepository.save(product);
            }
        }
        for(SteelGrade steelGrade : standard.getSteelGrades()) {
            if(steelGrade.getGradeStandard() == standard) {
                steelGrade.setGradeStandard(null);
                steelGradeRepository.save(steelGrade);
            }
        }
    }




}
