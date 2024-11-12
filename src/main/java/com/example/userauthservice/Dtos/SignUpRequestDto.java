package com.example.userauthservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;
}

