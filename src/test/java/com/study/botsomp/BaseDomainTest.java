package com.study.botsomp;

import com.study.botsomp.domain.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class BaseDomainTest {

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {

        Standard standard1 = Standard.builder()
                .designation("GOST8240-89")
                .build();
        entityManager.persist(standard1);

        Standard standard2 = Standard.builder()
                .designation("GOST8509-93")
                .build();
        entityManager.persist(standard2);

        Standard standard3 = Standard.builder()
                .designation("GOST380-2005")
                .build();
        entityManager.persist(standard3);

        Standard standard4 = Standard.builder()
                .designation("GOST5058-65")
                .build();
        entityManager.persist(standard4);

        SteelGrade steelGrade1 = SteelGrade.builder()
                .designation("St5sp")
                .gradeStandard(standard3)
                .build();
        entityManager.persist(steelGrade1);

        SteelGrade steelGrade2 = SteelGrade.builder()
                .designation("St3sp")
                .gradeStandard(standard3)
                .build();
        entityManager.persist(steelGrade2);

        SteelGrade steelGrade3 = SteelGrade.builder()
                .designation("14G")
                .gradeStandard(standard4)
                .build();
        entityManager.persist(steelGrade3);

        SteelGrade steelGrade4 = SteelGrade.builder()
                .designation("35GS")
                .gradeStandard(standard4)
                .build();
        entityManager.persist(steelGrade4);

        Product product1 = Product.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade(steelGrade1)
                .productStandard(standard1)
                .build();
        entityManager.persist(product1);

        Product product2 = Product.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade(steelGrade2)
                .productStandard(standard1)
                .build();
        entityManager.persist(product1);

        Product product3 = Product.builder()
                .name("Corner steel hot-rolled equipotential #3")
                .type("Corners")
                .steelGrade(steelGrade3)
                .productStandard(standard2)
                .build();
        entityManager.persist(product2);

        Product product4 = Product.builder()
                .name("Corner steel hot-rolled equipotential #3")
                .type("Corners")
                .steelGrade(steelGrade4)
                .productStandard(standard2)
                .build();
        entityManager.persist(product4);

        List<Product> productsForSteelGrade1 = new ArrayList<>();
        List<Product> productsForSteelGrade2 = new ArrayList<>();
        List<Product> productsForSteelGrade3 = new ArrayList<>();
        List<Product> productsForSteelGrade4 = new ArrayList<>();

        productsForSteelGrade1.add(product1);
        productsForSteelGrade2.add(product2);
        productsForSteelGrade3.add(product3);
        productsForSteelGrade4.add(product4);

        steelGrade1.toBuilder().products(productsForSteelGrade1).build();
        steelGrade2.toBuilder().products(productsForSteelGrade2).build();
        steelGrade3.toBuilder().products(productsForSteelGrade3).build();
        steelGrade4.toBuilder().products(productsForSteelGrade4).build();


        List<Product> productsForStandard1 = new ArrayList<>();
        List<Product> productsForStandard2 = new ArrayList<>();

        productsForStandard1.add(product1);
        productsForStandard1.add(product2);

        productsForStandard2.add(product3);
        productsForStandard2.add(product4);

        List<SteelGrade> steelGradesForStandard3 = new ArrayList<>();
        List<SteelGrade> steelGradesForStandard4 = new ArrayList<>();

        steelGradesForStandard3.add(steelGrade1);
        steelGradesForStandard3.add(steelGrade2);

        steelGradesForStandard4.add(steelGrade3);
        steelGradesForStandard4.add(steelGrade4);

        standard1.toBuilder().products(productsForStandard1).build();
        standard2.toBuilder().products(productsForStandard2).build();

        standard3.toBuilder().steelGrades(steelGradesForStandard3);
        standard4.toBuilder().steelGrades(steelGradesForStandard4);

        List<Product> productList1 = new ArrayList<>();
        List<Product> productList2 = new ArrayList<>();
        productList1.add(product1);
        productList1.add(product3);
        productList2.add(product2);
        productList2.add(product4);

        Manufacturer manufacturer1 = Manufacturer.builder()
                .name("Donetsk Metallurgical Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Artema, 26")
                .manufacturedProducts(productList1)
                .build();
        entityManager.persist(manufacturer1);

        ContactDetails contactDetails1 = ContactDetails.builder()
                .firstName("Alexander")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(380998426350L)
                .email("dmz@com.ua")
                .manufacturer(manufacturer1)
                .build();

        entityManager.persist(contactDetails1);

        Manufacturer manufacturer2 = Manufacturer.builder()
                .name("Azovstal")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Mariupol")
                .address("Str. Unclearly, 100")
                .manufacturedProducts(productList2)
                .build();
        entityManager.persist(manufacturer2);

        ContactDetails contactDetails2 = ContactDetails.builder()
                .firstName("Vladimir")
                .lastName("Drobyshev")
                .position("Director of Commercial Affairs")
                .phone(380998541212L)
                .email("azovstal@com.ua")
                .manufacturer(manufacturer2)
                .build();

        entityManager.persist(contactDetails2);

    }
}
