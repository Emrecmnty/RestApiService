package com.example.RestApiService.controller;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false)final String username) {
        return new ResponseEntity<>(userService.getAllUsers(username), OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable final Integer id) {
        return new ResponseEntity<>(getİtById(id),OK);
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final User user) {
        return new ResponseEntity<User>(userService.createUser(user), OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable final Integer id, @RequestBody final User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(user,OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable final Integer id) {
        User  deleteUser =  userService.getUserById(id);
        userService.deleteUser(id);
       return new ResponseEntity<>(deleteUser,OK);
    }
    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<String> handleUserNotFoundException(final UserNotFoundExeption userNotFoundExeption) {
        return new ResponseEntity<>(userNotFoundExeption.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsExeption.class)
    public ResponseEntity<String> handleUserAlreadyExists(final UserAlreadyExistsExeption userAlreadyExistsExeption) {
        return new ResponseEntity<>(userAlreadyExistsExeption.getMessage(), CONFLICT);
    }
   private User getİtById(final Integer id) {
        return userService.getUserById(id);
   }


}

