package com.nemo.autumn.repository;

import com.nemo.autumn.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

}
