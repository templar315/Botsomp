package com.study.botsomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetailsDTO implements Serializable {

    private static final long serialVersionUID = -3554721242569543640L;

    public interface New {
    }

    public interface Exist {
    }

    @NegativeOrZero(groups = {New.class}, message = "Id field is positive")
    @Positive(groups = {Exist.class}, message = "Id field is negative or zero")
    private long id;

    @NotNull(groups = {New.class, Exist.class}, message = "First name field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "First name field is empty")
    @Size(groups = {New.class, Exist.class}, max = 50, message = "First name field max length (50) exceeded")
    private String firstName;

    @NotNull(groups = {New.class, Exist.class}, message = "Last name field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Last name field is empty")
    @Size(groups = {New.class, Exist.class}, max = 50, message = "Last name field max length (50) exceeded")
    private String lastName;

    @NotNull(groups = {New.class, Exist.class}, message = "Position field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Position field is empty")
    @Size(groups = {New.class, Exist.class}, max = 255, message = "Position field max length (255) exceeded")
    private String position;

    @Positive(groups = {Exist.class}, message = "Phone field must be positive")
    private long phone;

    @NotNull(groups = {New.class, Exist.class}, message = "Email field is null")
    @NotEmpty(groups = {New.class, Exist.class}, message = "Email field is empty")
    @Size(groups = {New.class, Exist.class}, max = 62, message = "Email field max length (62) exceeded")
    @Email(groups = {New.class, Exist.class}, message = "Email field do not contain email",
            regexp = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")
    private String email;

    @Positive(groups = {Exist.class}, message = "Manufacturer id field must be positive")
    private long manufacturer;

}
