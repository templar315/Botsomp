package com.study.botsomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SteelGradeDTO implements Serializable {

    private static final long serialVersionUID = 4477149048500304357L;

    public interface New {
    }

    public interface Exist {
    }

    @NegativeOrZero(groups = {ContactDetailsDTO.New.class}, message = "Id field is positive")
    @Positive(groups = {ContactDetailsDTO.Exist.class}, message = "Id field is negative or zero")
    private long id;

    @NotNull(groups = {New.class, Exist.class}, message = "Designation field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Designation field is empty")
    @Size(groups = {New.class, Exist.class}, max = 255, message = "Designation field max length (255) exceeded")
    private String designation;

    private List<Long> products;

    @Size(groups = {New.class, Exist.class}, max = 255, message = "Standard field max length (255) exceeded")
    private String gradeStandard;

}
