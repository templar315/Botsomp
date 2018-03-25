package com.study.botsomp.repository;

import com.study.botsomp.BaseDomainTest;
import com.study.botsomp.domain.Manufacturer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerRepositoryTest extends BaseDomainTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Test
    public void add() {
        manufacturerRepository.save(Manufacturer.builder()
                .name("Ilyich Plant")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Donetsk")
                .address("Str. Unclearly, 100")
                .build());

        assertThat(manufacturerRepository.findByName("Ilyich Plant")).isNotNull();
    }

    @Test
    public void update() {
        manufacturerRepository.save(manufacturerRepository.findByName("Azovstal")
                .toBuilder()
                .name("Azovstal-Metinvest")
                .build());

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
        assertThat(manufacturerRepository.findByName("Ilyich Plant")).isNotNull();
    }

    @Test
    public void findById() {
        assertThat(manufacturerRepository.findById
                (manufacturerRepository.findByName("Donetsk Metallurgical Plant").getId()))
                .isNotNull();
    }

    @Test
    public void existsById() {
        assertThat(manufacturerRepository.existsById
                (manufacturerRepository.findByName("Donetsk Metallurgical Plant").getId()))
                .isTrue();
    }

    @Test
    public void findAll() {
        assertThat(manufacturerRepository.findAll()).hasSize(2);
    }

    @Test
    public void findAllById() {
        List<Long> ids = new ArrayList<>();

        ids.add(manufacturerRepository.findByName("Donetsk Metallurgical Plant").getId());
        ids.add(manufacturerRepository.findByName("Azovstal").getId());

        assertThat(manufacturerRepository.findAllById(ids)).hasSize(2);
    }

    @Test
    public void count() {
        assertThat(manufacturerRepository.count()).isEqualTo(2);
    }

    @Test
    public void deleteById() {
        Long id = manufacturerRepository.findByName("Donetsk Metallurgical Plant").getId();
        clearManufacturer(id);
        manufacturerRepository.deleteById(id);
        assertThat(manufacturerRepository.findByName("Donetsk Metallurgical Plant")).isNull();
    }

    @Test
    public void delete() {
        Manufacturer manufacturer = manufacturerRepository.findByName("Donetsk Metallurgical Plant");
        clearManufacturer(manufacturer.getId());
        manufacturerRepository.delete(manufacturer);
        assertThat(manufacturerRepository.findByName("Donetsk Metallurgical Plant")).isNull();
    }

    @Test
    public void deleteFromList() {
        List<Manufacturer> manufacturers = new ArrayList<>();

        manufacturers.add(manufacturerRepository.findByName("Donetsk Metallurgical Plant"));
        manufacturers.add(manufacturerRepository.findByName("Azovstal"));

        clearManufacturer(manufacturers.get(0).getId());
        clearManufacturer(manufacturers.get(1).getId());

        manufacturerRepository.deleteAll(manufacturers);

        assertThat(manufacturerRepository.findByName("Donetsk Metallurgical Plant")).isNull();
        assertThat(manufacturerRepository.findByName("Azovstal")).isNull();
    }

    @Test
    public void deleteAll() {
        contactDetailsRepository.deleteAll();
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
        assertThat(manufacturerRepository.findByCountry("Ukraine")).hasSize(2);
        assertThat(manufacturerRepository.findByCountry("Ukraine")
                .get(0)
                .getName())
                .isEqualTo("Donetsk Metallurgical Plant");
        assertThat(manufacturerRepository.findByCountry("Ukraine")
                .get(1)
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void findByCountryAndRegion() {
        assertThat(manufacturerRepository.findByCountryAndRegion("Ukraine", "Donetsk region")).hasSize(2);
        assertThat(manufacturerRepository.findByCountryAndRegion("Ukraine", "Donetsk region")
                .get(0)
                .getName())
                .isEqualTo("Donetsk Metallurgical Plant");
        assertThat(manufacturerRepository.findByCountryAndRegion("Ukraine", "Donetsk region")
                .get(1)
                .getName())
                .isEqualTo("Azovstal");
    }

    @Test
    public void findByCity() {
        assertThat(manufacturerRepository.findByCity("Donetsk")).hasSize(1);
        assertThat(manufacturerRepository.findByCity("Donetsk")
                .get(0)
                .getName())
                .isEqualTo("Donetsk Metallurgical Plant");
    }

    private void clearManufacturer(Long id) {
        contactDetailsRepository.delete(contactDetailsRepository.findByManufacturerId(id));
    }

}
