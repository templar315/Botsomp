package com.study.botsomp.repository;

import com.study.botsomp.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Manufacturer findByName(String name);
    List<Manufacturer> findByCountry(String country);
    List<Manufacturer> findByCountryAndRegion(String country, String region);
    List<Manufacturer> findByCity(String city);

}
