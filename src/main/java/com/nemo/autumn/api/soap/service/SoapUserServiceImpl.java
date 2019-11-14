package com.nemo.autumn.api.soap.service;

import com.nemo.autumn.api.common.ConstraintValidationConverter;
import com.nemo.autumn.api.common.DtoEntityAdapter;
import com.nemo.autumn.api.common.model.UserDto;
import com.nemo.autumn.api.common.model.ValidationResult;
import com.nemo.autumn.domain.User;
import com.nemo.autumn.exception.ConflictException;
import com.nemo.autumn.exception.UserAlreadyExistsException;
import com.nemo.autumn.exception.UserNotFoundException;
import com.nemo.autumn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

@Component
@WebService(serviceName = "UserService",
        targetNamespace = "http://soap.nemo.com/UserService?wsdl",
        endpointInterface = "com.nemo.autumn.api.soap.service.SoapUserService")
public class SoapUserServiceImpl implements SoapUserService {

    @Autowired
    private UserService userService;

    @Override
    public List<UserDto> getAllUsers() {
        return DtoEntityAdapter.convert(userService.findAllUsers());
    }

    @Override
    public UserDto getUser(String id) {
        return DtoEntityAdapter.convert(userService.retrieveUserById(id));
    }

    @Override
    public void deleteUser(String id) {
        User user = userService.retrieveUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        userService.deleteUser(user.getLogin());
    }

    @Override
    public ValidationResult createUser(UserDto user) throws UserAlreadyExistsException {
        Set<ConstraintViolation<UserDto>> violations = validate(user);
        if (!violations.isEmpty()) {
            return ConstraintValidationConverter.convert(violations);
        }
        userService.createUser(DtoEntityAdapter.convert(user));
        return null;
    }

    @Override
    public ValidationResult updateUser(UserDto user, String id) {
        if (!id.equals(String.valueOf(user.getId()))) {
            throw new ConflictException("Ids don't match!");
        }
        if (userService.retrieveUserById(id) == null) {
            throw new UserNotFoundException("User not found");
        }
        Set<ConstraintViolation<UserDto>> violations = validate(user);
        if (!violations.isEmpty()) {
            return ConstraintValidationConverter.convert(violations);
        }
        userService.updateUser(DtoEntityAdapter.convert(user));
        return null;
    }

    private Set<ConstraintViolation<UserDto>> validate(UserDto user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(user, Default.class);
    }

}
