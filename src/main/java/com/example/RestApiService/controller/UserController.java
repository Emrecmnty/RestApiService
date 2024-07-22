package com.example.RestApiService.controller;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.service.UserService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String username) {
        return new ResponseEntity<>(userService.getAllUsers(username), OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(getİtById(id),OK);
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(OK);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(OK);
    }
    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundExeption userNotFoundExeption) {
        return new ResponseEntity<>(userNotFoundExeption.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsExeption.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsExeption userAlreadyExistsExeption) {
        return new ResponseEntity<>(userAlreadyExistsExeption.getMessage(), CONFLICT);
    }
   private User getİtById(Integer id) {
        return userService.getUserById(id);
   }


}

