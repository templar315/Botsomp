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
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -3778278201185552644L;

    public interface New {
    }

    public interface Exist {
    }

    @NegativeOrZero(groups = {New.class}, message = "Id field is positive")
    @Positive(groups = {Exist.class}, message = "Id field is negative or zero")
    private long id;

    @NotNull(groups = {New.class, Exist.class}, message = "Name field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Name field is empty")
    @Size(groups = {New.class, Exist.class}, max = 255, message = "Name field max length (255) exceeded")
    private String name;

    @NotNull(groups = {New.class, Exist.class}, message = "Type field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Type field is empty")
    @Size(groups = {New.class, Exist.class}, max = 50, message = "Type field max length (50) exceeded")
    private String type;

    private List<Long> manufacturers;

    @NotNull(groups = {New.class, Exist.class}, message = "Steel Grade field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Steel Grade field is empty")
    @Size(groups = {New.class, Exist.class}, max = 255, message = "Steel Grade field max length (255) exceeded")
    private String steelGrade;

    @Size(groups = {New.class, Exist.class}, max = 255, message = "Standard field max length (255) exceeded")
    private String standard;

}
