package com.study.botsomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetailsDTO implements Serializable {

    private static final long serialVersionUID = -3554721242569543640L;

    private long id;
    private String firstName;
    private String lastName;
    private String position;
    private long phone;
    private String email;
    private long manufacturer;

}
