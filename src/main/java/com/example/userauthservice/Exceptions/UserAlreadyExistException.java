package com.example.userauthservice.Exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message){
        super(message);
    }

}
