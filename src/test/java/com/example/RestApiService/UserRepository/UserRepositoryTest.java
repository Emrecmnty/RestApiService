package com.example.RestApiService.UserRepository;

import com.example.RestApiService.model.User;
import com.example.RestApiService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findAllByUserName(){

        List<User> users = userRepository.findAllByUserName("JohnDoe");
        System.out.println(users.stream().findAny());
        assertThat(users.size()).isEqualTo(1);
    }
    @Test
    void findByUserName(){
        Optional<User> responseEntity = userRepository.findByUserName("JohnDoe");
        assertThat(responseEntity.isPresent()).isTrue();
        assertThat(responseEntity.get().getUserName()).isEqualTo("JohnDoe");

    }

}
