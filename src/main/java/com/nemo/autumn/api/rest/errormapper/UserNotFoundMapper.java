package com.nemo.autumn.api.rest.errormapper;

import com.nemo.autumn.api.common.model.ErrorMessage;
import com.nemo.autumn.exception.UserNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class UserNotFoundMapper
        implements ExceptionMapper<UserNotFoundException> {

    @Override
    public Response toResponse(UserNotFoundException exception) {
        return Response.
                               status(NOT_FOUND).
                               entity(new ErrorMessage(exception.getMessage())).
                               type(MediaType.APPLICATION_JSON).
                               build();
    }

}