package com.nemo.autumn.api.rest.errormapper;

import com.nemo.autumn.api.common.model.ErrorMessage;
import com.nemo.autumn.exception.AccessForbiddenException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
public class AccessForbiddenMapper
        implements ExceptionMapper<AccessForbiddenException> {

    @Override
    public Response toResponse(AccessForbiddenException exception) {
        return Response.
                               status(FORBIDDEN).
                               entity(new ErrorMessage(exception.getMessage())).
                               type(MediaType.APPLICATION_JSON).
                               build();
    }

}