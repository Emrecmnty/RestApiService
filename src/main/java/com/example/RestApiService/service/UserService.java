package com.example.RestApiService.service;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(String username) {
        if (username == null) {
            return userRepository.findAll();
        } else {
            return userRepository.findAllByUserName(username);
        }
    }
    public User createUser(User user) {
        Optional<User> userOptional = userRepository.findByUserName(user.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsExeption("User already exists with name: " + user.getUsername());
        }
        return userRepository.save(user);
    }
    public void deleteUser(Integer id) {userRepository.deleteById(id);}

    public void updateUser(Integer id, User user) {
        User oldUser = getUserById(id);
        oldUser.setUsername(user.getUsername());
        userRepository.save(oldUser);
    }
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("User not found with id: " + id));
    }

}
