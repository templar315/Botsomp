package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        productRepository.save(Product.builder()
                .name("Channel #8")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St5sp"))
                .productStandard(standardRepository.findByDesignation("GOST8240-89"))
                .build());
        assertThat(productRepository.findByName("Channel #8")).isNotEmpty();
    }

    @Test
    public void update() {
        productRepository.save(productRepository.findByName("Steel hot-rolled channel #5").get(0)
                .toBuilder()
                .name("Channel steel hot-rolled #6")
                .build());

        assertThat(productRepository.findByName("Channel steel hot-rolled #6")).hasSize(1);
        assertThat(productRepository.findAll()).hasSize(4);
    }

    @Test
    public void addAll() {
        List<Product> products = new ArrayList<>();

        products.add(Product.builder()
                .name("Steel hot-rolled channel #10")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St5sp"))
                .productStandard(standardRepository.findByDesignation("GOST8240-89"))
                .build());
        products.add(Product.builder()
                .name("Steel hot-rolled channel #12")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St5sp"))
                .productStandard(standardRepository.findByDesignation("GOST8240-89"))
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
        assertThat(productRepository.findAll()).hasSize(4);
    }

    @Test
    public void findAllById() {
        assertThat(productRepository.findAllById(productRepository.findByType("Channel bars")
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList()))).hasSize(2);
    }

    @Test
    public void count() {
        assertThat(productRepository.count()).isEqualTo(4);
    }

    @Test
    public void deleteById() {
        Product product = productRepository.findByName("Steel hot-rolled channel #5").get(0);

        clearProduct(product);

        productRepository.deleteById(product.getId());

        assertThat(productRepository.existsById(product.getId())).isFalse();
    }

    @Test
    public void delete() {
        Product product = productRepository.findByName("Steel hot-rolled channel #5").get(0);

        clearProduct(product);

        productRepository.delete(product);

        assertThat(productRepository.existsById(product.getId())).isFalse();
    }

    @Test
    public void deleteByList() {
        List<Product> products = productRepository.findByType("Channel bars");

        for(Product product : products) clearProduct(product);

        productRepository.deleteAll();

        assertThat(productRepository.findByType("Channel bars")).isEmpty();
    }

    @Test
    public void deleteAll() {
        for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
            manufacturer.getManufacturedProducts().clear();
        }

        productRepository.deleteAll();

        assertThat(productRepository.findAll()).isEmpty();
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
        assertThat(productRepository.findByName("Steel hot-rolled channel #5")).hasSize(2);
    }

    @Test
    public void findByType() {
        assertThat(productRepository.findByType("Channel bars")).hasSize(2);
    }

    @Test
    public void findByNameAndSteelGradeDesignation() {
        assertThat(productRepository.findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5",
                "St5sp")).isNotNull();
    }

    @Test
    public void findByTypeAndSteelGradeDesignation() {
        assertThat(productRepository.findByTypeAndSteelGradeDesignation("Channel bars",
                "St5sp")).isNotEmpty().hasSize(1);
    }

    @Test
    public void findBySteelGradeDesignationAndProductStandardDesignation() {
        assertThat(productRepository
                .findBySteelGradeDesignationAndProductStandardDesignation("St5sp",
                        "GOST8240-89")).isNotEmpty().hasSize(1);
    }

    private void clearProduct(Product product) {
        for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
            manufacturer.getManufacturedProducts().remove(product);
        }
    }
/*
    @Test
    public void startTest() {
        for(ContactDetails contactDetails : contactDetailsRepository.findAll()) {
            System.out.println("CD");
            System.out.println("CD - Manufacturer : " + contactDetails.getManufacturer());
        }
        for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
            System.out.println("M");
            for(Product product : manufacturer.getManufacturedProducts()) {
                System.out.println("M - Product : " + product);
            }
            System.out.println("M - CD : " + manufacturer.getContactDetails());
        }
        for(Product product : productRepository.findAll()) {
            System.out.println("P");
            if(product.getManufacturers() != null) {
                for (Manufacturer manufacturer : product.getManufacturers()) {
                    System.out.println("P - Manufacturer : " + manufacturer);
                }
            }
            System.out.println("P - SteelGrade : " + product.getSteelGrade());
            System.out.println("P - Standard : " + product.getProductStandard());
        }
        for(Standard standard : standardRepository.findAll()) {
            System.out.println("ST");
            for(Product product : standard.getProducts()) {
                System.out.println("ST - Product : " + product);
            }
            for(SteelGrade steelGrade : standard.getSteelGrades()) {
                System.out.println("ST - SteelGrade : " + steelGrade);
            }
        }
        for(SteelGrade steelGrade : steelGradeRepository.findAll()) {
            System.out.println("SG");
            for(Product product : steelGrade.getProducts()) {
                System.out.println("SG - Product : " + product);
            }
            System.out.println("SG - Standard : " + steelGrade.getGradeStandard());
        }
    }*/

}
