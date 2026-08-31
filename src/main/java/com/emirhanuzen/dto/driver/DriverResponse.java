package com.emirhanuzen.dto.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {

    private Long id;
    private String fullName;
    private String licenseNumber;
    private String licenseClass;
    private String phoneNumber;
}