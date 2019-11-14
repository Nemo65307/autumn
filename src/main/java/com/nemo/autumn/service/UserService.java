package com.nemo.autumn.service;

import com.nemo.autumn.exception.UserAlreadyExistsException;
import com.nemo.autumn.exception.UserNotFoundException;
import com.nemo.autumn.domain.User;
import com.nemo.autumn.mvc.form.SignupForm;

import java.util.List;

public interface UserService {

    void createUser(User user) throws UserAlreadyExistsException;

    void deleteUser(User user);

    void deleteUser(String id);

    void updateUser(User user) throws UserNotFoundException;

    User retrieveUserByLogin(String login);

    User retrieveUserByEmail(String email);

    User retrieveUserById(String id);

    List<User> findAllUsers();

    void createUser(SignupForm form) throws UserAlreadyExistsException;

}