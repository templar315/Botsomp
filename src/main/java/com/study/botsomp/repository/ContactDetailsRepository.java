package com.study.botsomp.repository;

import com.study.botsomp.domain.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {

    ContactDetails findByManufacturerId(long id);
    ContactDetails findByManufacturerName(String name);

}
