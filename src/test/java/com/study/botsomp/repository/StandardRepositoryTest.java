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
        standardRepository.save(Standard.builder()
                .designation("GOST7798-70")
                .build());

        assertThat(standardRepository.findByDesignation("GOST7798-70")).isNotNull();
    }

    @Test
    public void update() {
        standardRepository.save(standardRepository.findByDesignation("GOST8240-89")
                .toBuilder()
                .designation("GOST8240-2018")
                .build());

        assertThat(standardRepository.findByDesignation("GOST8240-2018")).isNotNull();
        assertThat(standardRepository.findAll()).hasSize(4);
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
                .findById(standardRepository.findByDesignation("GOST8240-89").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(standardRepository
                .existsById(standardRepository.findByDesignation("GOST8240-89").getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        assertThat(standardRepository.findAll()).hasSize(4);
    }

    @Test
    public void findAllById() {
        List<Long> ids = new ArrayList<>();

        ids.add(standardRepository.findByDesignation("GOST8240-89").getId());
        ids.add(standardRepository.findByDesignation("GOST8509-93").getId());

        assertThat(standardRepository.findAllById(ids)).hasSize(2);
    }

    @Test
    public void count() {
        assertThat(standardRepository.count()).isEqualTo(4);
    }

    @Test
    public void deleteById() {
        Standard standard = standardRepository.findByDesignation("GOST8240-89");

        clearStandard(standard);

        standardRepository.deleteById(standard.getId());

        assertThat(standardRepository.findByDesignation("GOST8240-89")).isNull();
    }

    @Test
    public void delete() {
        Standard standard = standardRepository.findByDesignation("GOST8240-89");

        clearStandard(standard);

        standardRepository.delete(standard);

        assertThat(standardRepository.findByDesignation("GOST8240-89")).isNull();
    }

    @Test
    public void deleteFromList() {
        List<Standard> standards = new ArrayList<>();

        standards.add(standardRepository.findByDesignation("GOST8240-89"));
        standards.add(standardRepository.findByDesignation("GOST8509-93"));

        clearStandard(standards.get(0));
        clearStandard(standards.get(1));

        standardRepository.deleteAll(standards);

        assertThat(standardRepository.findByDesignation("GOST8240-89")).isNull();
        assertThat(standardRepository.findByDesignation("GOST8509-93")).isNull();
    }

    @Test
    public void deleteAll() {
        for(Product product : productRepository.findAll()) {
            productRepository.save(product.toBuilder().productStandard(null).build());
        }
        for(SteelGrade steelGrade : steelGradeRepository.findAll()) {
            steelGradeRepository.save(steelGrade.toBuilder().gradeStandard(null).build());
        }

        standardRepository.deleteAll();

        assertThat(standardRepository.findAll()).isEmpty();
    }

    @Test
    public void getOne() {
        assertThat(standardRepository.getOne(standardRepository.findByDesignation("GOST8240-89").getId())
                .getDesignation()).isEqualTo("GOST8240-89");
    }

    @Test
    public void findByDesignation() {
        assertThat(standardRepository.findByDesignation("GOST8240-89")).isNotNull();
    }

    private void clearStandard(Standard standard) {
        for(Product product : productRepository.findAll()) {
            if(product.getProductStandard() == standard) {
                productRepository.save(product.toBuilder().productStandard(null).build());
            }
        }
        for(SteelGrade steelGrade : steelGradeRepository.findAll()) {
            if(steelGrade.getGradeStandard() == standard) {
                steelGradeRepository.save(steelGrade.toBuilder().gradeStandard(null).build());
            }
        }
    }




}
