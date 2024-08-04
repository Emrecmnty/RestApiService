package com.example.RestApiService;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.repository.UserRepository;
import com.example.RestApiService.service.UserService;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes= RestApiServiceApplicationTests.class)
@AutoConfigureMockMvc
class RestApiServiceApplicationTests {


    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;



    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(6);
        user1.setUserName("testUser");
        user1.setPassword("password");
        user1.setEmail("test@example.com");
        User user2 = new User();
        user2.setId(7);
        user2.setUserName("testUser2");
        user2.setPassword("password2");
        user2.setEmail("test2@example.com");

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers(null);

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        verify(userRepository).findAll();
    }


    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertThat(createdUser).isEqualTo(user);
        verify(userRepository).save(user);
    }
    @Test
    public void testDeleteUser() {
        Integer userId = 1;
        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }
    @Test
    public void testUpdateUser() {
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUserName("existingUser");

        User updatedUser = new User();
        updatedUser.setUserName("updatedUser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userService.updateUser(userId, updatedUser);

        assertThat(existingUser.getUserName()).isEqualTo("updatedUser");
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }

    @Test
    public void testGetUserById() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setUserName("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(userId);
    }
    @Test
    public void testUpdateUser_UserNotFound() throws UserNotFoundExeption {
        Integer userId = 1;
        User updatedUser = new User();
        updatedUser.setUserName("updatedUser");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundExeption.class, () -> {
            userService.updateUser(userId, updatedUser);
        });
    }
    @Test
    public void testCreateUser_UserAlreadyExists() throws UserAlreadyExistsExeption {

        User user1 = new User();
        user1.setId(6);
        user1.setUserName("testUser");
        user1.setPassword("password");
        user1.setEmail("test@example.com");


        when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));

        assertThrows(UserAlreadyExistsExeption.class, () -> {
            userService.createUser(user1);
        });
    }
}






