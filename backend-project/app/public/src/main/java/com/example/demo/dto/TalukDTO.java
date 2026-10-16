package com.example.demo.dto;

public class TalukDTO {
    private Long id;
    private String name;

    public TalukDTO(Long id, String name) {
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

