package com.nemo.autumn.service.impl;

import com.nemo.autumn.exception.ConflictException;
import com.nemo.autumn.exception.UserAlreadyExistsException;
import com.nemo.autumn.exception.UserNotFoundException;
import com.nemo.autumn.repository.RoleRepository;
import com.nemo.autumn.repository.UserRepository;
import com.nemo.autumn.security.CurrentUser;
import com.nemo.autumn.domain.Role;
import com.nemo.autumn.domain.User;
import com.nemo.autumn.mvc.form.SignupForm;
import com.nemo.autumn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "This username is already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "This email is already registered");
        }
        user.setRole(getCorrespondingRole(user));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws UserNotFoundException {
        Optional<User> loadedUser = userRepository.findByLogin(user.getLogin());
        if (!loadedUser.isPresent()) {
            throw new UserNotFoundException("Couldn't find the user");
        }
        User finalUser = loadedUser.get();
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(finalUser.getPassword());
        }
        finalUser = new User(finalUser.getId(), finalUser.getLogin(),
                user.getPassword(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getBirthday(),
                getCorrespondingRole(user));
        userRepository.save(finalUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User retrieveUserByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User retrieveUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User retrieveUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = retrieveUserByLogin(username);
        if (user != null) {
            return new CurrentUser(user);
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }

    @Override
    @Transactional
    public void createUser(SignupForm form) throws UserAlreadyExistsException {
        Optional<User> user = userRepository.findByLogin(form.getLogin());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(
                    "This username is already taken");
        }
        user = userRepository.findByEmail(form.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(
                    "This email is already registered");
        }
        Role role = roleRepository.findByName("user").orElse(null);
        User savingUser = new User(null, form.getLogin(), form.getPassword(),
                form.getEmail(), form.getFirstName(), form.getLastName(),
                form.getBirthday(), role);
        userRepository.save(savingUser);
    }

    private Role getCorrespondingRole(User user) {
        Role role = user.getRole();
        if (role != null) {
            if (role.getId() != null) {
                role = roleRepository.findById(role.getId()).orElse(null);
            } else if (role.getName() != null) {
                role = roleRepository.findByName(role.getName()).orElse(null);
            }
        }
        //default role
        if (role == null || (role.getName() == null && role.getId() == null)) {
            role = roleRepository.findByName("user")
                                 .orElseThrow(ConflictException::new);
        }
        return role;
    }

}
