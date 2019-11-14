package com.nemo.autumn.api.rest.errormapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.nemo.autumn.api.common.model.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class JsonMappingExceptionMapper
        implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        return Response
                .status(BAD_REQUEST)
                .entity(new ErrorMessage("Couldn't process the json"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
