package com.example.ecom.dto;


import lombok.Data;

@Data
public class CategoryDto {


    public Long id;
    public String name;
    public String description;

    public CategoryDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryDto() {
    }
}
