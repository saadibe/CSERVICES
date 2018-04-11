package com.bnp.cservices.frontend.config;

import com.bnp.cservices.config.SpringConfig;
import java.net.URL;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The Spring configuration for the application.
 * <p>
 * By default, this configuration is provided through JNDI with the delivery manager.
 * </p>
 * <p>
 * When no JNDI provider is set-up, then the class uses the provided application.properties in resource-dev folder. To
 * make this folder available, you have to build your project with the dev maven profile.
 * </p>
 */
@Configuration
@PropertySource(
        encoding = "UTF-8",
        value = "classpath:application.properties"
)
public class PresApplicationConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresApplicationConfig.class.getCanonicalName());

    private static final String JAVA_COMP_ENV = "java:comp/env";

    /**
     * The Sesame domain for authentication property name.
     */
    private static final String SESAME_DOMAIN = "sesame.domain";

    /**
     * The sesame group for authentication property name.
     */
    private static final String SESAME_AUTHENTICATION_GROUP = "sesame.authentication.group";

    /**
     * The application version property name.
     */
    private static final String APPLICATION_VERSION = "application.version";

    /**
     * The url to sesame SOAP service property name.
     */
    private static final String URL_SESAME_SERVER = "url/SesameServer";

    /**
     * The url to application layer property name.
     */
    private static final String URL_SERVICE_URL = "url/ServiceUrl";

    /**
     * Load the configuration.
     *
     * @param environment the Spring environment.
     */
    public PresApplicationConfig(Environment environment) {
        super(environment);
    }

    /**
     * Search a variable in the JNDI dictionary. If no value is found, default it from the dev properties file. The
     * property file is used through the dev maven profile.
     *
     * @param name the name of the variable in the dictionary.
     * @return the found value.
     */
    private String getVariable(String name) {
        try {
            Context envEntryContext = (Context) new InitialContext().lookup(JAVA_COMP_ENV);

            URL jndiFoundUrl = (URL) envEntryContext.lookup(name);
            LOGGER.info("Binding JNDI service url gave {}", jndiFoundUrl);
            return jndiFoundUrl.toString();

        } catch (NamingException e) {
            LOGGER.info("Binding JNDI gave null result. Returning property {}", super.getProperty(name, String.class));
            LOGGER.trace("raised error", e);
            return super.getProperty(name, String.class);
        }
    }

    /**
     * The URL to application layer (3tiers architecture design). This value must be used when setting up the URL to the
     * business service through {@link org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean}.
     *
     * @return the URL to application layer.
     */
    @Bean
    public String servicesUrl() {
        LOGGER.info("Application layer URL loaded");
        return this.getVariable(URL_SERVICE_URL);
    }

    /**
     * The URL to SESAME service.
     *
     * @return the URL to SESAME service.
     */
    @Bean
    public String sesameUrl() {
        LOGGER.info("Sesame URL loaded");
        return this.getVariable(URL_SESAME_SERVER);
    }

    /**
     * The SESAME domain for roles and permissions. Those values are used by sesame for the authentication.
     *
     * @return the SESAME domain.
     */
    @Bean
    public String sesameDomain() {
        LOGGER.info("Sesame domain set to {}", super.getProperty(SESAME_DOMAIN, String.class));
        return super.getProperty(SESAME_DOMAIN, String.class);
    }

    /**
     * The authentication Group. Those values are used by sesame for the authentication.
     *
     * @return the authentication Group.
     */
    @Bean
    public String authenticationGroup() {
        LOGGER.info("Sesame Group set to {}", super.getProperty(SESAME_AUTHENTICATION_GROUP, String.class));
        return super.getProperty(SESAME_AUTHENTICATION_GROUP, String.class);
    }

    /**
     * POM project version.
     *
     * @return the application version.
     */
    @Bean
    public String applicationVersion() {
        LOGGER.info("POM application version found {}", super.getProperty(APPLICATION_VERSION, String.class));
        return super.getProperty(APPLICATION_VERSION, String.class);
    }

}
