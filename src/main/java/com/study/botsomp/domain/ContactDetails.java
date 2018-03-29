package com.study.botsomp.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact_details")
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = -7286371480438723163L;

    @Id
    @GeneratedValue
    @Column(name = "contact_details_id")
    private long id;

    @Column(name = "contact_details_first_name", nullable = false)
    private String firstName;

    @Column(name = "contact_details_last_name", nullable = false)
    private String lastName;

    @Column(name = "contact_details_position", nullable = false)
    private String position;

    @Column(name = "contact_details_phone", nullable = false)
    private long phone;

    @Column(name = "contact_details_email", nullable = false)
    private String email;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "contact_details_manufacturer", nullable = false, unique = true)
    private Manufacturer manufacturer;

}
