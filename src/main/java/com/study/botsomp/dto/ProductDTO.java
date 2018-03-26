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
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -3778278201185552644L;

    private long id;
    private String name;
    private String type;
    private List<Long> manufacturers;
    private String steelGrade;
    private String standard;

}
