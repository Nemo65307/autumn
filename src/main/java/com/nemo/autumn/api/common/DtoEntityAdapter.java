package com.nemo.autumn.api.common;

import com.nemo.autumn.api.common.model.UserDto;
import com.nemo.autumn.domain.User;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public final class DtoEntityAdapter {

    public static UserDto convert(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getEmail(), null,
                user.getFirstName(), user.getLastName(), user.getBirthday(),
                user.getRole());
    }

    public static List<UserDto> convert(List<User> userList) {
        return userList.stream()
                .map(DtoEntityAdapter::convert)
                .collect(toCollection(LinkedList::new));
    }

    public static User convert(UserDto userDto) {
        return new User(null, userDto.getLogin(), userDto.getPassword(),
                userDto.getEmail(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getBirthday(),
                userDto.getRole());
    }

}
