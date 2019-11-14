package com.nemo.autumn.api.rest.errormapper;

import com.nemo.autumn.api.common.model.ErrorMessage;
import com.nemo.autumn.exception.ConflictException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper
        implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException exception) {
        return Response.
                               status(Response.Status.CONFLICT).
                               entity(new ErrorMessage(exception.getMessage())).
                               type(MediaType.APPLICATION_JSON).
                               build();
    }

}
