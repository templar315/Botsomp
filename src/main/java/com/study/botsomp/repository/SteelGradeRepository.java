package com.study.botsomp.repository;

import com.study.botsomp.domain.SteelGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SteelGradeRepository extends JpaRepository<SteelGrade, Long> {

    SteelGrade findByDesignation(String name);

}
