package com.example.demo.dto;

public class DistrictDTO {
    private Long id;
    private String name;

    public DistrictDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

