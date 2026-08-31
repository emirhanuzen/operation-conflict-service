package com.emirhanuzen.dto.task;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    @NotBlank(message = "Görev başlığı boş olamaz")
    private String title;

    private String description;
}