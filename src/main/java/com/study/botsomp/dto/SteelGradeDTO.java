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

public class SteelGradeDTO implements Serializable {

    private static final long serialVersionUID = 4477149048500304357L;

    private long id;
    private String designation;
    private List<Long> products;
    private String gradeStandard;

}
