package com.emirhanuzen.dto.driver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverUpdateRequest {

    @NotBlank(message = "Ad soyad boş olamaz")
    private String fullName;

    @NotBlank(message = "Ehliyet numarası boş olamaz")
    private String licenseNumber;

    @NotBlank(message = "Ehliyet sınıfı boş olamaz")
    private String licenseClass;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Geçerli bir telefon numarası giriniz")
    private String phoneNumber;
}