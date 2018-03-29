package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import com.study.botsomp.domain.Standard;
import com.study.botsomp.domain.SteelGrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
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
        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St3sp");
        steelGrade.setDesignation("St5");
        steelGradeRepository.saveAndFlush(steelGrade);

        assertThat(steelGradeRepository.findByDesignation("St5")).isNotNull();
        assertThat(steelGradeRepository.findAll()).hasSize(1);
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
                .findById(steelGradeRepository.findByDesignation("St3sp").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(steelGradeRepository
                .existsById(steelGradeRepository.findByDesignation("St3sp").getId()))
                .isTrue();
    }

    @Test
    public void findAllByIds() {
        List<Long> ids = new ArrayList<>(Arrays.asList(steelGradeRepository.findByDesignation("St3sp").getId()));
        assertThat(steelGradeRepository.findAllById(ids)).hasSize(1);
    }

    @Test
    public void count() {
        assertThat(steelGradeRepository.count()).isEqualTo(1);
    }

    private void deleteUp() {
        standardRepository.save(Standard.builder()
                .designation("GOST540-2012")
                .build());

        steelGradeRepository.save(SteelGrade.builder()
                .designation("St6sp")
                .gradeStandard(standardRepository.findByDesignation("GOST540-2012"))
                .build());

        productRepository.save(Product.builder()
                .name("Steel hot-rolled channel #10")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St6sp"))
                .productStandard(standardRepository.findByDesignation("GOST540-2012"))
                .manufacturers(new ArrayList<>())
                .build());

        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St6sp");
        steelGrade.setProducts(new ArrayList<>(Arrays.asList(productRepository
                .findByName("Steel hot-rolled channel #10")
                .get(0))));
        steelGradeRepository.saveAndFlush(steelGrade);

        manufacturerRepository.save(Manufacturer.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .manufacturedProducts(new ArrayList<>(Arrays.asList(productRepository
                        .findByName("Steel hot-rolled channel #10")
                        .get(0))))
                .build());

        Product product = productRepository.findByName("Steel hot-rolled channel #10").get(0);
        product.setManufacturers(new ArrayList<>(Arrays.asList(manufacturerRepository.findByName("Ilyich Plant"))));
        productRepository.saveAndFlush(product);

    }

    @Test
    public void deleteById() {
        deleteUp();
        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St6sp");
        clearSteelGrade(steelGrade);
        steelGradeRepository.deleteById(steelGrade.getId());

        assertThat(steelGradeRepository.findByDesignation("St6sp")).isNull();
    }

    @Test
    public void delete() {
        deleteUp();
        SteelGrade steelGrade = steelGradeRepository.findByDesignation("St6sp");
        clearSteelGrade(steelGrade);
        steelGradeRepository.delete(steelGrade);

        assertThat(steelGradeRepository.findByDesignation("St6sp")).isNull();
    }

    @Test
    public void deleteFromList() {
        deleteUp();
        List<SteelGrade> steelGrades = new ArrayList<>(Arrays.asList(steelGradeRepository
                .findByDesignation("St6sp")));
        clearSteelGrade(steelGrades.get(0));
        steelGradeRepository.deleteAll(steelGrades);

        assertThat(steelGradeRepository.findByDesignation("St6sp")).isNull();
    }

    @Test
    public void getOne() {
        assertThat(steelGradeRepository
                .getOne(steelGradeRepository.findByDesignation("St3sp").getId())).isNotNull();
    }

    @Test
    public void findByDesignation() {
        assertThat(steelGradeRepository.findByDesignation("St3sp")).isNotNull();
    }

    private void clearSteelGrade(SteelGrade steelGrade) {
        for(Product product : steelGrade.getProducts()) {
            for(Manufacturer manufacturer : product.getManufacturers()) {
                manufacturer.getManufacturedProducts().remove(product);
                manufacturerRepository.saveAndFlush(manufacturer);
            }
            productRepository.delete(product);
        }
    }
}
