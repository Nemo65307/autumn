package com.nemo.autumn.api.common;

import com.nemo.autumn.api.common.model.UserDto;
import com.nemo.autumn.domain.User;

import java.util.LinkedList;
import java.util.List;

public final class DtoEntityAdapter {

    public static UserDto convert(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getEmail(), null,
                user.getFirstName(), user.getLastName(), user.getBirthday(),
                user.getRole());
    }

    public static List<UserDto> convert(List<User> userList) {
        List<UserDto> jsonList = new LinkedList<>();
        for (User user : userList) {
            jsonList.add(convert(user));
        }
        return jsonList;
    }

    public static User convert(UserDto userDto) {
        return new User(null, userDto.getLogin(), userDto.getPassword(),
                userDto.getEmail(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getBirthday(),
                userDto.getRole());
    }

}