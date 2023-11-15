package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonRequestDto {
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String address;
    private String idDniType;
    private Long dniNumber;
    private String password;
    private String birthDate;
}
