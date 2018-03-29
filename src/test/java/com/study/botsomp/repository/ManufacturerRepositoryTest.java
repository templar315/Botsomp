package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.ContactDetails;
import com.study.botsomp.domain.Manufacturer;
import com.study.botsomp.domain.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerRepositoryTest extends BaseDomainTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private SteelGradeRepository steelGradeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void add() {
        Product product = Product.builder()
                .name("Steel hot-rolled channel #6")
                .type("Channel bars")
                .steelGrade(steelGradeRepository.findByDesignation("St3sp"))
                .build();
        productRepository.saveAndFlush(product);

        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("Valentine")
                .lastName("Menshikov")
                .position("Director of Commercial Affairs")
                .phone(+380952035855L)
                .email("dmz@com.ua")
                .build();

        Manufacturer manufacturer = Manufacturer.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .contactDetails(contactDetails)
                .manufacturedProducts(new ArrayList<>(Arrays.asList(productRepository.findByNameAndSteelGradeDesignation(
                                        "Steel hot-rolled channel #6",
                                        "St3sp"))))
                .build();

        contactDetails.setManufacturer(manufacturer);
        manufacturerRepository.saveAndFlush(manufacturer);

        assertThat(manufacturerRepository.findByName("Ilyich Plant")).isNotNull();
    }

    @Test
    public void update() {
        Manufacturer manufacturer = manufacturerRepository.findByName("Azovstal");
        manufacturer.setName("Azovstal-Metinvest");
        manufacturerRepository.save(manufacturer);

        assertThat(manufacturerRepository.findByName("Azovstal-Metinvest")).isNotNull();
    }

    @Test
    public void addAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();

        Manufacturer man1 = Manufacturer.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .build();
        manufacturers.add(man1);
        Manufacturer man2 = Manufacturer.builder()
                .name("Zaporizhia Ferroalloys")
                .country("Ukraine")
                .region("Zaporozhye region")
                .city("Zaporozhye")
                .address("Str. Unclearly, 100")
                .build();
        manufacturers.add(man2);

        manufacturerRepository.saveAll(manufacturers);

        assertThat(manufacturerRepository.findByName("Ilyich Plant")).isNotNull();
        assertThat(manufacturerRepository.findByName("Zaporizhia Ferroalloys")).isNotNull();
    }

    @Test
    public void findById() {
        assertThat(manufacturerRepository.findById
                (manufacturerRepository.findByName("Azovstal").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(manufacturerRepository.existsById
                (manufacturerRepository.findByName("Azovstal").getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        assertThat(manufacturerRepository.findAll()).hasSize(1);
    }

    @Test
    public void findAllById() {
        List<Long> ids = new ArrayList<>(Arrays.asList(manufacturerRepository.findByName("Azovstal").getId()));
        assertThat(manufacturerRepository.findAllById(ids)).hasSize(1);
    }

    @Test
    public void count() {
        assertThat(manufacturerRepository.count()).isEqualTo(1);
    }

    @Test
    public void deleteById() {
        Long id = manufacturerRepository.findByName("Azovstal").getId();
        clearManufacturer(id);
        manufacturerRepository.deleteById(id);

        assertThat(manufacturerRepository.findByName("Azovstal")).isNull();
    }

    @Test
    public void delete() {
        Manufacturer manufacturer = manufacturerRepository.findByName("Azovstal");
        clearManufacturer(manufacturer.getId());
        manufacturerRepository.delete(manufacturer);

        assertThat(manufacturerRepository.findByName("Azovstal")).isNull();
    }

    @Test
    public void deleteFromList() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturerRepository.findByName("Azovstal"));
        clearManufacturer(manufacturers.get(0).getId());
        manufacturerRepository.deleteAll(manufacturers);

        assertThat(manufacturerRepository.findByName("Azovstal")).isNull();
    }

    @Test
    public void deleteAll() {
        for(Manufacturer manufacturer : manufacturerRepository.findAll()) {
            for(Product product : manufacturer.getManufacturedProducts()) {
                product.getManufacturers().remove(manufacturer);
                productRepository.saveAndFlush(product);
            }
        }
        manufacturerRepository.deleteAll();

        assertThat(manufacturerRepository.findAll()).isEmpty();
    }

    @Test
    public void getOne() {
        assertThat(manufacturerRepository.getOne
                (manufacturerRepository.findByName("Azovstal").getId())).isNotNull();
    }

    @Test
    public void findByName() {
        assertThat(manufacturerRepository.findByName("Azovstal")).isNotNull();
        assertThat(manufacturerRepository.findByName("Azovstal").getCountry()).isEqualTo("Ukraine");
    }

    @Test
    public void findByCountry() {
        assertThat(manufacturerRepository.findByCountry("Ukraine")).hasSize(1);
        assertThat(manufacturerRepository.findByCountry("Ukraine")
                .get(0)
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void findByCountryAndRegion() {
        assertThat(manufacturerRepository.findByCountryAndRegion("Ukraine", "Donetsk region")).hasSize(1);
        assertThat(manufacturerRepository.findByCountryAndRegion("Ukraine", "Donetsk region")
                .get(0)
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void findByCity() {
        assertThat(manufacturerRepository.findByCity("Mariupol")).hasSize(1);
        assertThat(manufacturerRepository.findByCity("Mariupol")
                .get(0)
                .getName())
                .isEqualTo("Azovstal");
    }

    private void clearManufacturer(Long id) {
        Manufacturer manufacturer = manufacturerRepository.getOne(id);
        if(manufacturer != null) {
            for(Product product : manufacturer.getManufacturedProducts()) {
                product.getManufacturers().remove(manufacturer);
                productRepository.saveAndFlush(product);
            }
        }
    }

}
