package com.study.botsomp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "steel_grade")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SteelGrade implements Serializable {

    private static final long serialVersionUID = 2602749877361371758L;

    @Id
    @GeneratedValue
    @Column(name = "steel_grade_id")
    private long id;

    @Column(name = "steel_grade_designation", nullable = false, unique = true)
    private String designation;

    @OneToMany(mappedBy = "steelGrade", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "steel_grade_standard")
    private Standard gradeStandard;

}
