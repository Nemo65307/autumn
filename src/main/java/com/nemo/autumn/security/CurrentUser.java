package com.nemo.autumn.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CurrentUser extends User {

    private final String id;
    private final String fullName;
    private final String role;

    public CurrentUser(com.nemo.autumn.domain.User user) {
        super(user.getLogin(), user.getPassword(), true, true, true, true,
                Collections.singleton(new SimpleGrantedAuthority(
                        SecurityUtil.getCurrentUserRole(user))));
        id = user.getId();
        fullName = user.getFirstName() + " " + user.getLastName();
        role = SecurityUtil.getCurrentUserRole(user);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("CurrentProfile [id=%s, username=%s]", id,
                getUsername());
    }

}