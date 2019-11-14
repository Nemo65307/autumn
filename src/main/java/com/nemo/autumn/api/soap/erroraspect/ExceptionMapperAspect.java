package com.nemo.autumn.api.soap.erroraspect;

import com.nemo.autumn.exception.BusinessException;
import com.nemo.autumn.exception.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

@Aspect
@Component
public class ExceptionMapperAspect {

    private static final Logger logger = LoggerFactory.getLogger(
            ExceptionMapperAspect.class);

    @Around("execution(* com.nemo.autumn.api.soap.service.SoapUserServiceImpl.*(..))")
    public Object aroundProcessImageResource(ProceedingJoinPoint jp)
            throws Exception {
        validateParameters(jp.getArgs());
        try {
            return jp.proceed();
        } catch (Throwable e) {
            if (e instanceof BusinessException
                    || e instanceof ValidationException) {
                logger.warn("Ws request failed: {0}", e.getMessage());
                throw new SOAPFaultException(
                        createSOAPFault(e.getMessage(), true));
            } else {
                logger.error("Ws request failed: " + e.getMessage(), e);
                throw new SOAPFaultException(
                        createSOAPFault("Internal error", false));
            }
        }
    }

    private void validateParameters(Object[] parameters) throws SOAPException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] == null) {
                throw new SOAPFaultException(
                        createSOAPFault("Parameter [" + i + "] is invalid",
                                true));
            }
        }
    }

    private SOAPFault createSOAPFault(String message, boolean isClientError)
            throws SOAPException {
        String errorType = isClientError ? "Client" : "Server";
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        return soapFactory.createFault(message,
                new QName("http://schemas.xmlsoap.org/soap/envelope/",
                        errorType, ""));
    }

}