package com.example.RestApiService.service;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(final String username) {
        if (username == null) {
            return userRepository.findAll();
        } else {
            return userRepository.findAllByUserName(username);
        }
    }
    public User createUser(final User user) {
        final Optional<User> userOptional = userRepository.findByUserName(user.getUserName());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsExeption("User already exists with name: " + user.getUserName());
        }
        return userRepository.save(user);
    }
    public ResponseEntity deleteUser(final Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public User updateUser(final Integer id,final User user) {
      final User oldUser = getUserById(id);
        oldUser.setUserName(user.getUserName());
        return userRepository.save(oldUser);
    }
    public User getUserById(final Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("User not found with id: " + id));
    }

}
