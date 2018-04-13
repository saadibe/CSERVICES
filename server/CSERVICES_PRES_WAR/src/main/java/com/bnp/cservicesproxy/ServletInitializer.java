package com.bnp.cservicesproxy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author a03150
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {
/**
 * 
 * @param application
 * @return
 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiProxyApplication.class);
    }

}
