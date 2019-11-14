package com.nemo.autumn.api.rest.errormapper;

import com.nemo.autumn.api.common.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JaxRsExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    private static final Logger logger = LoggerFactory.getLogger(
            JaxRsExceptionMapper.class);

    @Override
    public Response toResponse(WebApplicationException exception) {
        logError(exception);
        return Response
                .status(exception.getResponse().getStatusInfo())
                .entity(new ErrorMessage(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void logError(WebApplicationException exception) {
        if (exception.getResponse().getStatus() >= 500) {
            logger.error(exception.getMessage(), exception);
        } else {
            logger.error(exception.getMessage());
        }
    }

}
