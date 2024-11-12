package com.example.userauthservice.Controllers;

import com.example.userauthservice.Dtos.*;
import com.example.userauthservice.Exceptions.UserAlreadyExistException;
import com.example.userauthservice.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public  AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signUpRequestDto)  {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        try{
            if(authService.signUp(signUpRequestDto.getEmail(),signUpRequestDto.getPassword())) {
                signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            }
            return new ResponseEntity<>(signUpResponseDto, HttpStatus.ACCEPTED);

        }catch (Exception e){
            signUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(signUpResponseDto,HttpStatus.CONFLICT);
        }

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            String token = authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);


    }

}
