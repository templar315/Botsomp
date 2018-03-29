package com.study.botsomp.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "manufacturer")
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 5619897085759659636L;

    @Id
    @GeneratedValue
    @Column(name = "manufacturer_id")
    private long id;

    @Column(name = "manufacturer_name", nullable = false, unique = true)
    private String name;

    @Column(name = "manufacturer_country", nullable = false)
    private String country;

    @Column(name = "manufacturer_region", nullable = false)
    private String region;

    @Column(name = "manufacturer_city", nullable = false)
    private String city;

    @Column(name = "manufacturer_address", nullable = false)
    private String address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "manufacturer_product",
            joinColumns = @JoinColumn(name = "manufacturer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> manufacturedProducts;

    @OneToOne(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactDetails contactDetails;

}
