package com.study.botsomp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "standard")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Standard implements Serializable {

    private static final long serialVersionUID = -6153922811400685048L;

    @Id
    @GeneratedValue
    @Column(name = "standard_id")
    private long id;

    @Column(name = "standard_designation", nullable = false, unique = true)
    private String designation;

    @OneToMany(mappedBy = "productStandard", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products;

    @OneToMany(mappedBy = "gradeStandard", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SteelGrade> steelGrades;

}
