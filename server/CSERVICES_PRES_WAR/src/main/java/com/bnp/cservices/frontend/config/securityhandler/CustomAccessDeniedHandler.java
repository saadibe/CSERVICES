package com.bnp.cservices.frontend.config.securityhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The access denied handler is called when authorization fails. This means the client is passing in a correct token but
 * the permissions associated with the role of this user does not allow the client the access.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        LOGGER.info("intercepted request : 403 error code.");
        httpServletResponse.getWriter().append("Access denied : " + e.getMessage());
        httpServletResponse.setStatus(403);
    }

}
