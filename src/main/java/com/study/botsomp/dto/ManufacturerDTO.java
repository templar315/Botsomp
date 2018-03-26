package com.study.botsomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO implements Serializable {

    private static final long serialVersionUID = -5271127246570542525L;

    private long id;
    private String name;
    private String country;
    private String region;
    private String city;
    private String address;
    private List<Long> manufacturedProducts;
    private Long contactDetails;

}
