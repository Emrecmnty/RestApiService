package com.example.RestApiService.controller;

import com.example.RestApiService.Exeption.UserAlreadyExistsExeption;
import com.example.RestApiService.Exeption.UserNotFoundExeption;
import com.example.RestApiService.model.User;
import com.example.RestApiService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUser() throws Exception {

        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setUserName("JohnDoe");
        user.setPassword("password123");
        user.setEmail("johndoe@example.comm");

        when(userService.getUserById(userId)).thenReturn(user);
        ResponseEntity responseEntity = userController.getUser(userId);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testCreateUser() throws Exception {
        int userId = 1;
        User user = new User();
        user.setId(1);
        user.setUserName("JohnDoe");
        user.setPassword("password123");
        user.setEmail("johndoe@example.comm");

            when(userService.createUser(user)).thenReturn(user);

            ResponseEntity responseEntity = userController.createUser(user);

            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).isEqualTo(user);


    }

    @Test
    public void testUpdateUser() throws Exception {
        int userId = 1;
        User user = new User(1,"uJohnDoe","upassword123","ujohndoe@example.comm");

        when(userService.updateUser(userId,user)).thenReturn(user);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)));

                result.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(userId))
                        .andExpect(jsonPath("$.userName").value("uJohnDoe"))
                        .andExpect(jsonPath("$.email").value("ujohndoe@example.comm"))
                        .andExpect(jsonPath("$.password").value("upassword123"));







    }

    @Test
    public void testDeleteUser() throws Exception {

        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setUserName("JohnDoe");
        user.setPassword("password123");
        user.setEmail("johndoe@example.comm");

        when(userService.deleteUser(userId)).thenReturn(ResponseEntity.ok().build());

        final ResponseEntity<User> responseEntity = userController.deleteUser(userId);

        assertThat(responseEntity).isEqualTo(ResponseEntity.ok().build());

}
    @Test
    public void testGetUsers() throws Exception {

        User user1 = new User();
        user1.setId(1);
        user1.setUserName("JohnDoe");
        user1.setPassword("password123");
        user1.setEmail("johndoe@example.comm");

        User user2 = new User();
        user2.setId(2);
        user2.setUserName("2JohnDoe");
        user2.setPassword("2password123");
        user2.setEmail("2johndoe@example.comm");

        List<User> users = Arrays.asList(user1, user2);


        when(userService.getAllUsers(null)).thenReturn(users);

        ResponseEntity responseEntity = userController.getUsers(null);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(users);

    }
    @Test
    public void testGetUserById() throws Exception {

        int userId = 1;
        User user = new User();
        user.setId(1);
        user.setUserName("JohnDoe");
        user.setPassword("password123");
        user.setEmail("johndoe@example.comm");

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity responseEntity = userController.getUser(userId);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isInstanceOf(User.class);


    }
    @Test
    public void testHandleUserNotFoundException() throws Exception {
        int id = 7;
        when(userService.getUserById(id))
                .thenThrow(new UserNotFoundExeption("User not found with id: " + id));

        assertThatExceptionOfType(UserNotFoundExeption.class).isThrownBy(()
                -> userController.getUser(id));

    }

    @Test
    public void testHandleUserAlreadyExists() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("JohnDoe");
        user.setPassword("password123");
        user.setEmail("johndoe@example.comm");



        when(userService.createUser(user))
                .thenThrow(new UserAlreadyExistsExeption("User already exists with name: " + user.getUserName()));

        assertThatExceptionOfType(UserAlreadyExistsExeption.class).isThrownBy(()
                -> userController.createUser(user));



    }

}



