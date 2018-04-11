package com.bnp.cservices.frontend.config;

import com.bnp.cservices.config.SpringConfig;
import com.bnp.cservices.frontend.config.securityhandler.CsrfHeaderFilter;
import com.bpc.dreamteam.security.AuthenticationType;
import com.bpc.dreamteam.security.SesameTokenValidationFilter;
import com.bpc.dreamteam.security.config.CustomAuthenticationEntryPoint;
import com.bpc.dreamteam.security.route.SecurityRouteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Configure the security for the application.
 */
@Configuration
@ComponentScan({"com.bpc.dreamteam.security"})
public class ApplicationSecurityConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSecurityConfig.class.getCanonicalName());

    /**
     * Load the configuration.
     *
     * @param environment the Spring environment.
     */
    public ApplicationSecurityConfig(Environment environment) {
        super(environment);
    }

    /**
     * Here you can define your own authentication handler. Remove the dependency in your pom to sesame authentication.
     * The authentication is mocked with dev profile in pom.
     *
     * @return the custom authentication handler you would define.
     */
    //@Bean
    //public AuthenticationProvider authenticationProvider() {
    //    return null;
    //}
    /**
     * Disables the security. The Security Route handler is not used if the security is disabled. Make the security
     * component use your securityRouteHandler implementation.
     *
     * @return the indicator of security.
     */
    //@Bean
    //@Primary
    //public Boolean isSecurityDisabled() {
    //    return false;
    //}
    /**
     * Here you can configurate your security, only if you set the isSecurityDisabled to false.
     *
     * @return the configuration of the security.
     */
    @Bean
    public SecurityRouteHandler securityRouteHandler() {
        final SesameTokenValidationFilter sesameTokenValidationFilter = this.getContext().getBean("sesameTokenValidationFilter", SesameTokenValidationFilter.class);
        final CustomAuthenticationEntryPoint customAuthenticationEntryPoint = this.getContext().getBean("customAuthenticationEntryPoint", CustomAuthenticationEntryPoint.class);
        final LogoutSuccessHandler logoutSuccessHandler = this.getContext().getBean("logoutSuccessHandler", LogoutSuccessHandler.class);

        return new SecurityRouteHandler() {
            @Override
            public void configureRoutes(HttpSecurity http) throws Exception {
                LOGGER.info("Loading custom security configuration.");
                http.httpBasic().and()
                        .authorizeRequests().antMatchers("/index.html", "/", "/logout.html").permitAll().and()
                        // configure the csrfTokenRepository
                        .csrf().csrfTokenRepository(csrfTokenRepository()).and()
                        .authorizeRequests()
                        .antMatchers("/user").permitAll()
                        .antMatchers("/contact/**").access("hasRole('ADMIN') and hasRole('DBA')")
                        .anyRequest().authenticated();

                //Override the default login pop-up with a 401 response.
                http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
                // Add the csrf filter : to communicate safely with angular
                http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
                //If sesame authentication provider is provided.
                //this filter validate the token regularly.
                http.addFilterAfter(sesameTokenValidationFilter, CsrfHeaderFilter.class);
                //Manages the event on successful logout
                http.logout().logoutSuccessHandler(logoutSuccessHandler);
            }

            @Override
            public void configureWebSecurity(WebSecurity web) throws Exception {
                web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
                // Spring Security should completely ignore URLs starting with
                web.ignoring().antMatchers("/assets/**").and();
                web.ignoring().antMatchers("/app/**").and();
                // Spring Security should completely ignore URLs for the js / images /
                // font
                web.ignoring().antMatchers("/*.ico").and();
                web.ignoring().antMatchers("/*.eot").and();
                web.ignoring().antMatchers("/*.svg").and();
                web.ignoring().antMatchers("/*.ttf").and();
                web.ignoring().antMatchers("/*.woff").and();
                web.ignoring().antMatchers("/*.woff2").and();
                web.ignoring().antMatchers("/*.js").and();
                web.ignoring().antMatchers("/*.css").and();
                web.ignoring().antMatchers("/*.map").and();
                web.ignoring().antMatchers("/*.png").and();
                web.ignoring().antMatchers("/*.jpg").and();
            }
        };
    }

    /**
     * Csrf Token Repository that contains the header named "X-XSRF-TOKEN".
     *
     * @return CsrfTokenRepository
     */
    private static CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    /**
     * Mock the security user to test permissions.
     *
     * @return the mock of the user.
     */
    //@Primary
    //@Bean
    //public SecurityUser mockedSecurityUser() {
    //    SecurityUser user = new SecurityUser("aaabbb", "token");
    //
    //    user.setLastAuthenticationDate(Calendar.getInstance().getTimeInMillis());
    //    user.setCivility("M");
    //    user.setAccountType("");
    //    user.setEmail("John.doe@bnpparibas.com");
    //    user.setFirstName("John");
    //    user.setLastName("Doe");
    //    user.setPhone("00 00 00 00 00");
    //    user.setUid("a00000");
    //    user.setRoles(new ArrayList<>());
    //    user.setPermissions(new ArrayList<>());
    //
    //    return user;
    //}
    /**
     * The message to display when an exception occurs in authentication process.
     *
     * @return the message to display.
     */
    @Bean
    public String contactMessage() {
        return "Please contact your CPI";
    }

    /**
     * You can define an authentication type. SOAP is the only one available right now.
     *
     * @return the authentication type.
     */
    @Bean
    public AuthenticationType authenticationType() {
        return AuthenticationType.SOAP;
    }

}
