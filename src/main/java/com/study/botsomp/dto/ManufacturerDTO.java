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
public class ManufacturerDTO implements Serializable {

    private static final long serialVersionUID = -5271127246570542525L;

    public interface New {
    }

    public interface Exist {
    }

    @NegativeOrZero(groups = {ContactDetailsDTO.New.class}, message = "Id field is positive")
    @Positive(groups = {ContactDetailsDTO.Exist.class}, message = "Id field is negative or zero")
    private long id;

    @NotNull(groups = {New.class, Exist.class}, message = "Name field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Name field is empty")
    @Size(groups = {New.class, Exist.class}, max = 100, message = "Name field max length (100) exceeded")
    private String name;

    @NotNull(groups = {New.class, Exist.class}, message = "Country field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Country field is empty")
    @Size(groups = {New.class, Exist.class}, max = 50, message = "Country field max length (50) exceeded")
    private String country;

    @NotNull(groups = {New.class, Exist.class}, message = "Region field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Region field is empty")
    @Size(groups = {New.class, Exist.class}, max = 100, message = "Region field max length (100) exceeded")
    private String region;

    @NotNull(groups = {New.class, Exist.class}, message = "City field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "City field is empty")
    @Size(groups = {New.class, Exist.class}, max = 50, message = "City field max length (50) exceeded")
    private String city;

    @NotNull(groups = {New.class, Exist.class}, message = "Address field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Address field is empty")
    @Size(groups = {New.class, Exist.class}, max = 255, message = "Address field max length (255) exceeded")
    private String address;

    private List<Long> manufacturedProducts;
    private Long contactDetails;

}
