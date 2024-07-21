package com.example.RestApiService.Exeption;

public class UserAlreadyExistsExeption extends RuntimeException {
    public UserAlreadyExistsExeption(String message) {super(message);}
}
