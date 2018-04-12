package com.bnp.apiproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author a03150
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * 
     * @return Docket
     */
    @Bean
    public Docket productApi() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.bnp.apiproxy"))
                .paths(regex("/apiproxy.*")).build().apiInfo(metaInfo());
    }

    /**
     * 
     * @return ApiInfo
     */
    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo("API PROXY BNP PARIBAS",
                "API PROXY BNP PARIBAS ARCHETYPE", "1.0", "Services",
                new Contact("saadi bechir", "", "saadi@gmail.com"), "", "");
        return apiInfo;

    }
}
