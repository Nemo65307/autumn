package com.nemo.autumn.api.soap;

import com.nemo.autumn.api.soap.service.SoapUserServiceImpl;
import com.nemo.autumn.api.soap.service.SoapUserService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public SoapUserService soapUserService() {
        return new SoapUserServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(),
                soapUserService());
        endpoint.publish("/user");
        return endpoint;
    }

}
