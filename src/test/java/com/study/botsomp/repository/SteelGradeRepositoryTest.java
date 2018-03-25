package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.SteelGrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SteelGradeRepositoryTest extends BaseDomainTest {

    @Autowired
    private SteelGradeRepository steelGradeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void add() {
        steelGradeRepository.save(SteelGrade.builder()
                .designation("St2sp")
                .gradeStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());

        assertThat(steelGradeRepository.findByDesignation("St2sp")).isNotNull();
    }

    @Test
    public void update() {
        steelGradeRepository.save(steelGradeRepository.findByDesignation("St5sp")
                .toBuilder()
                .designation("St5")
                .build());

        assertThat(steelGradeRepository.findByDesignation("St5")).isNotNull();
        assertThat(steelGradeRepository.findAll()).hasSize(4);
    }

    @Test
    public void addAll() {
        List<SteelGrade> steelGrades = new ArrayList<>();

        steelGrades.add(SteelGrade.builder()
                .designation("St3kp")
                .gradeStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());
        steelGrades.add(SteelGrade.builder()
                .designation("St4kp")
                .gradeStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());

        steelGradeRepository.saveAll(steelGrades);

        assertThat(steelGradeRepository.findByDesignation("St3kp")).isNotNull();
        assertThat(steelGradeRepository.findByDesignation("St4kp")).isNotNull();
    }

    @Test
    public void findById() {
        assertThat(steelGradeRepository
                .findById(steelGradeRepository.findByDesignation("St5sp").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(steelGradeRepository
                .existsById(steelGradeRepository.findByDesignation("St5sp").getId()))
                .isTrue();
    }

    @Test
    public void findAllByIds() {
        List<Long> ids = new ArrayList<>();

        ids.add(steelGradeRepository.findByDesignation("St5sp").getId());
        ids.add(steelGradeRepository.findByDesignation("St3sp").getId());

        assertThat(steelGradeRepository.findAllById(ids)).hasSize(2);
    }

    @Test
    public void count() {
        assertThat(steelGradeRepository.count()).isEqualTo(4);
    }

    @Test
    public void deleteById() {
        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St5sp");

        clearSteelGrade(steelGrade);

        steelGradeRepository.deleteById(steelGrade.getId());

        assertThat(steelGradeRepository.findByDesignation("St5sp")).isNull();
    }

    @Test
    public void delete() {
        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St5sp");

        clearSteelGrade(steelGrade);

        steelGradeRepository.delete(steelGrade);

        assertThat(steelGradeRepository.findByDesignation("St5sp")).isNull();
    }

    @Test
    public void deleteFromList() {
        List<SteelGrade> steelGrades = new ArrayList<>();

        steelGrades.add(steelGradeRepository.findByDesignation("St5sp"));
        steelGrades.add(steelGradeRepository.findByDesignation("St3sp"));

        clearSteelGrade(steelGrades.get(0));
        clearSteelGrade(steelGrades.get(1));

        steelGradeRepository.deleteAll(steelGrades);

        assertThat(steelGradeRepository.findByDesignation("St5sp")).isNull();
        assertThat(steelGradeRepository.findByDesignation("St3sp")).isNull();
    }

    @Test
    public void deleteAll() {
        for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
            manufacturer.getManufacturedProducts().clear();
        }
        for(Product product : productRepository.findAll()) {
            productRepository.delete(product);
        }

        steelGradeRepository.deleteAll();

        assertThat(steelGradeRepository.findAll()).isEmpty();
    }

    @Test
    public void getOne() {
        assertThat(steelGradeRepository
                .getOne(steelGradeRepository.findByDesignation("St5sp").getId())).isNotNull();
    }

    @Test
    public void findByDesignation() {
        assertThat(steelGradeRepository.findByDesignation("St5sp")).isNotNull();
    }

    private void clearSteelGrade(SteelGrade steelGrade) {
        for(Product product : productRepository.findAll()) {
            if(product.getSteelGrade() == steelGrade) {
                for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
                    if(manufacturer.getManufacturedProducts().contains(product)) {
                        manufacturer.getManufacturedProducts().remove(product);
                    }
                }
                productRepository.delete(product);
            }
        }
    }
}
