package com.study.botsomp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 9150609166649325227L;

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_type", nullable = false)
    private String type;

    @ManyToMany(mappedBy = "manufacturedProducts", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Manufacturer> manufacturers;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_steel_grade", nullable = false)
    private SteelGrade steelGrade;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_standard")
    private Standard productStandard;

}
