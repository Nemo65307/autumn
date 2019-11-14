package com.nemo.autumn.api.rest.resource;

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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Component
@Path("/user")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/all")
    public List<UserDto> getAllUsers() {
        return DtoEntityAdapter.convert(userService.findAllUsers());
    }

    @GET
    @Path("/{id}")
    public UserDto getUser(@PathParam("id") String id) {
        User user = userService.retrieveUserById(id);
        if (user == null)
            throw new UserNotFoundException("User not found by ID");
        return DtoEntityAdapter.convert(user);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User user = userService.retrieveUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found by ID");
        }
        userService.deleteUser(user.getId());
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @POST
    public Response createUser(UserDto user, @Context UriInfo uriInfo)
            throws UserAlreadyExistsException {
        Set<ConstraintViolation<UserDto>> violations = validate(user);
        if (!violations.isEmpty()) {
            ValidationResult validationResult = ConstraintValidationConverter.convert(
                    violations);
            return Response.status(BAD_REQUEST)
                           .entity(validationResult)
                           .build();
        }
        userService.createUser(DtoEntityAdapter.convert(user));
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(UserDto user, @PathParam("id") String id) {
        Set<ConstraintViolation<UserDto>> violations = validate(user);
        if (!violations.isEmpty()) {
            if (!((violations.size() == 1) && user.getPassword().isEmpty())) {
                ValidationResult validationResult = ConstraintValidationConverter
                        .convert(violations);
                return Response.status(BAD_REQUEST)
                               .entity(validationResult)
                               .build();
            }
        }
        if (!id.equals(String.valueOf(user.getId()))) {
            throw new ConflictException("Ids don't match!");
        }
        if (userService.retrieveUserById(String.valueOf(user.getId()))
                == null) {
            throw new UserNotFoundException("User not found by ID");
        }
        userService.updateUser(DtoEntityAdapter.convert(user));
        return Response.status(Response.Status.ACCEPTED.getStatusCode())
                       .build();
    }

    private Set<ConstraintViolation<UserDto>> validate(UserDto user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(user, Default.class);
    }

}