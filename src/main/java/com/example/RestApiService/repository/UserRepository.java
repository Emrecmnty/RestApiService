package com.example.RestApiService.repository;

import com.example.RestApiService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    List<User> findAllByUserName(String name);
    Optional<User> findByUserName(String name);
}
