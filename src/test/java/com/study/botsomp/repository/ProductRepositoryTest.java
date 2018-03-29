package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends BaseDomainTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SteelGradeRepository steelGradeRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void add() {
        productRepository.saveAndFlush(Product.builder()
                .name("Channel #8")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St3sp"))
                .productStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());

        Product product = productRepository
                .findByNameAndSteelGradeDesignation("Channel #8", "St3sp");

        assertThat(product).isNotNull();
        assertThat(product.getProductStandard().getDesignation()).isEqualTo("GOST380-2005");
    }

    @Test
    public void update() {
        Product product = productRepository.findByName("Steel hot-rolled channel #5").get(0);
        product.setName("Channel steel hot-rolled #6");
        productRepository.saveAndFlush(product);

        assertThat(productRepository.findByName("Channel steel hot-rolled #6")).hasSize(1);
        assertThat(productRepository.findAll()).hasSize(1);
    }

    @Test
    public void addAll() {
        List<Product> products = new ArrayList<>();

        products.add(Product.builder()
                .name("Steel hot-rolled channel #10")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St3sp"))
                .productStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());
        products.add(Product.builder()
                .name("Steel hot-rolled channel #12")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St3sp"))
                .productStandard(standardRepository.findByDesignation("GOST380-2005"))
                .build());

        productRepository.saveAll(products);

        assertThat(productRepository.findByName("Steel hot-rolled channel #10")).isNotEmpty();
        assertThat(productRepository.findByName("Steel hot-rolled channel #12")).isNotEmpty();
    }

    @Test
    public void findById() {
        assertThat(productRepository.findById(
                productRepository.findByName("Steel hot-rolled channel #5")
                        .get(0)
                        .getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(productRepository.existsById(
                productRepository.findByName("Steel hot-rolled channel #5")
                        .get(0)
                        .getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        assertThat(productRepository.findAll()).hasSize(1);
    }

    @Test
    public void findAllById() {
        assertThat(productRepository.findAllById(productRepository.findByType("Channel bars")
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList()))).hasSize(1);
    }

    @Test
    public void count() {
        assertThat(productRepository.count()).isEqualTo(1);
    }

    private void deleteUp() {
        productRepository.saveAndFlush(Product.builder()
                .name("Steel hot-rolled channel #10")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St3sp"))
                .productStandard(standardRepository.findByDesignation("GOST380-2005"))
                .manufacturers(new ArrayList<>(Arrays.asList(manufacturerRepository.findByName("Azovstal"))))
                .build());
    }

    @Test
    public void deleteById() {
        deleteUp();
        Product product = productRepository.findByName("Steel hot-rolled channel #10").get(0);
        clearProduct(product);
        productRepository.deleteById(product.getId());

        assertThat(productRepository.existsById(product.getId())).isFalse();
    }

    @Test
    public void delete() {
        deleteUp();
        Product product = productRepository.findByName("Steel hot-rolled channel #10").get(0);
        clearProduct(product);
        productRepository.delete(product);

        assertThat(productRepository.existsById(product.getId())).isFalse();
    }

    @Test
    public void deleteByList() {
        deleteUp();
        System.out.println(manufacturerRepository.findByName("Azovstal").getManufacturedProducts());
        List<Product> products = productRepository.findByName("Steel hot-rolled channel #10");
        for(Product product : products) clearProduct(product);
        productRepository.deleteAll();

        assertThat(productRepository.findByName("Steel hot-rolled channel #10")).isEmpty();
    }

    @Test
    public void getOne() {
        assertThat(productRepository
                .getOne(productRepository.findByName("Steel hot-rolled channel #5")
                        .get(0)
                        .getId()))
                .isNotNull();
    }

    @Test
    public void findByName() {
        assertThat(productRepository.findByName("Steel hot-rolled channel #5")).hasSize(1);
    }

    @Test
    public void findByType() {
        assertThat(productRepository.findByType("Channel bars")).hasSize(1);
    }

    @Test
    public void findByNameAndSteelGradeDesignation() {
        assertThat(productRepository.findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5",
                "St3sp")).isNotNull();
    }

    @Test
    public void findByTypeAndSteelGradeDesignation() {
        assertThat(productRepository.findByTypeAndSteelGradeDesignation("Channel bars",
                "St3sp")).isNotEmpty().hasSize(1);
    }

    @Test
    public void findBySteelGradeDesignationAndProductStandardDesignation() {
        assertThat(productRepository
                .findBySteelGradeDesignationAndProductStandardDesignation("St3sp",
                        "GOST380-2005")).isNotEmpty().hasSize(1);
    }

    private void clearProduct(Product product) {
        for (Manufacturer manufacturer : product.getManufacturers()) {
            manufacturer.getManufacturedProducts().remove(product);
            manufacturerRepository.saveAndFlush(manufacturer);
        }
    }

}
