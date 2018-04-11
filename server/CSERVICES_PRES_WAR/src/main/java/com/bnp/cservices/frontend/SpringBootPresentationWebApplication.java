package com.bnp.cservices.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The starter.
 */
@SpringBootApplication
public class SpringBootPresentationWebApplication extends SpringBootServletInitializer {

    /**
     * Start the server.
     *
     * @param args the arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootPresentationWebApplication.class, args);
    }
}
