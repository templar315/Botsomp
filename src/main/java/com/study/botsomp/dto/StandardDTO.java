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
public class StandardDTO implements Serializable {

    private static final long serialVersionUID = -2158372517247381493L;

    private long id;
    private String designation;
    private List<Long> products;
    private List<Long> steelGrades;

}
