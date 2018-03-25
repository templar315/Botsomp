package com.study.botsomp.repository;

import com.study.botsomp.domain.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Long> {

    Standard findByDesignation(String designation);

}
