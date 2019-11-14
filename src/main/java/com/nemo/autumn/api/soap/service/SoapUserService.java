package com.nemo.autumn.api.soap.service;

import com.nemo.autumn.api.common.model.UserDto;
import com.nemo.autumn.api.common.model.ValidationResult;
import com.nemo.autumn.exception.UserAlreadyExistsException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://soap.nemo.com/UserService?wsdl")
public interface SoapUserService {

    @WebMethod
    List<UserDto> getAllUsers();

    @WebMethod
    @WebResult(name = "user")
    UserDto getUser(@WebParam(name = "id") String id);

    @WebMethod
    void deleteUser(@WebParam(name = "id") String id);

    @WebMethod
    ValidationResult createUser(@WebParam(name = "user") UserDto user)
            throws UserAlreadyExistsException;

    @WebMethod
    ValidationResult updateUser(@WebParam(name = "user") UserDto user,
            @WebParam(name = "id") String id);

}