package com.example.demo.mapper;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;

public class UserMapper {

    public static UserResponseDTO mapToDto(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(), 
                user.getAddress()      
        );
    }
}
