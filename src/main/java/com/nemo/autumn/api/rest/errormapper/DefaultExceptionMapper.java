package com.nemo.autumn.api.rest.errormapper;

import com.nemo.autumn.api.common.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(
            DefaultExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        logger.error(exception.getMessage(), exception);
        return Response.
                               status(INTERNAL_SERVER_ERROR)
                       .
                               entity(new ErrorMessage("Internal server error"))
                       .
                               type(MediaType.APPLICATION_JSON)
                       .
                               build();
    }

}