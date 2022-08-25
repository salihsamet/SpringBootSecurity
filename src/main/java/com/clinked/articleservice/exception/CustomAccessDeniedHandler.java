package com.clinked.articleservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOG = LogManager.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String errorMessage = null;
        if (auth != null) {
            errorMessage = "User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI();
            LOG.warn(errorMessage);
        }


    }
}