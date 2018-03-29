package com.study.botsomp;

import com.study.botsomp.domain.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class BaseDomainTest {

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {

        Standard standard = Standard.builder()
                .designation("GOST380-2005")
                .build();

        SteelGrade steelGrade = SteelGrade.builder()
                .designation("St3sp")
                .gradeStandard(standard)
                .build();

        Product product = Product.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade(steelGrade)
                .productStandard(standard)
                .build();

        Manufacturer manufacturer = Manufacturer.builder()
                .name("Azovstal")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Mariupol")
                .address("Str. Unclearly, 100")
                .manufacturedProducts(new ArrayList<>(Arrays.asList(product)))
                .build();

        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("Vladimir")
                .lastName("Drobyshev")
                .position("Director of Commercial Affairs")
                .phone(380998541212L)
                .email("azovstal@com.ua")
                .manufacturer(manufacturer)
                .build();


        manufacturer.setContactDetails(contactDetails);
        product.setManufacturers(new ArrayList<>(Arrays.asList(manufacturer)));
        steelGrade.setProducts(new ArrayList<>(Arrays.asList(product)));
        standard.setSteelGrades(new ArrayList<>(Arrays.asList(steelGrade)));
        standard.setProducts(new ArrayList<>(Arrays.asList(product)));

        entityManager.persist(standard);

    }
}
