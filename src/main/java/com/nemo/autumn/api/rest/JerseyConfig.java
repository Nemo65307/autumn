package com.nemo.autumn.api.rest;

import com.nemo.autumn.api.rest.errormapper.AccessForbiddenMapper;
import com.nemo.autumn.api.rest.errormapper.ConflictExceptionMapper;
import com.nemo.autumn.api.rest.errormapper.DefaultExceptionMapper;
import com.nemo.autumn.api.rest.errormapper.JaxRsExceptionMapper;
import com.nemo.autumn.api.rest.errormapper.JsonMappingExceptionMapper;
import com.nemo.autumn.api.rest.errormapper.UserAlreadyExistsMapper;
import com.nemo.autumn.api.rest.errormapper.UserNotFoundMapper;
import com.nemo.autumn.api.rest.resource.RoleResource;
import com.nemo.autumn.api.rest.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserResource.class);
        register(RoleResource.class);
        
        register(AccessForbiddenMapper.class);
        register(ConflictExceptionMapper.class);
        register(DefaultExceptionMapper.class);
        register(JaxRsExceptionMapper.class);
        register(JsonMappingExceptionMapper.class);
        register(UserAlreadyExistsMapper.class);
        register(UserNotFoundMapper.class);
    }

}