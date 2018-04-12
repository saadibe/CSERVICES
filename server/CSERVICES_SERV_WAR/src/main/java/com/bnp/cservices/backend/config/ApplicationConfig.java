package com.bnp.cservices.backend.config;

import com.bnp.cservices.config.SpringConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * The Spring configuration for the application layer.
 */
@Configuration
@PropertySource(encoding = "UTF-8", value = "classpath:application.properties")
public class ApplicationConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ApplicationConfig.class.getCanonicalName());

    private static final String JAVA_COMP_ENV = "java:comp/env";

    private static final String DATASOURCE_JNDI_NAME = "datasource.jndi.name";

    private static final String APPLICATION_VERSION = "application.version";

    /**
     * Load the configuration.
     *
     * @param environment the Spring environment.
     */
    public ApplicationConfig(Environment environment) {
        super(environment);
    }

    /**
     * POM project version.
     *
     * @return the application version.
     */
    @Bean
    public String applicationVersion() {
        LOGGER.info("Application version found {}",
                super.getProperty(APPLICATION_VERSION, String.class));
        return super.getProperty(APPLICATION_VERSION, String.class);
    }

    /**
     * Search a variable in the JNDI dictionary. If no value is found, default it from the
     * properties file.
     *
     * @param name the name of the variable in the dictionnary.
     * @return the found value.
     */
    private String getStringVariable(String name) {
        try {
            Context envEntryContext = (Context) new InitialContext()
                    .lookup(JAVA_COMP_ENV);

            String jndiFoundVariable = (String) envEntryContext.lookup(name);
            LOGGER.info("Binding JNDI {} gave {}", name, jndiFoundVariable);
            return jndiFoundVariable;
        }
        catch (NamingException e) {
            LOGGER.info(
                    "Binding JNDI {} gave null (Naming exception). Returning property {}",
                    name, super.getProperty(name, String.class));
            LOGGER.trace("raised error", e);
            return super.getProperty(name, String.class);
        }
    }

    /**
     * Database schema name.
     *
     * @return the database schema name.
     */
    @Bean
    public String schemaName() {
        LOGGER.info("Database schema Name loaded");
        String jndiFoundPath = this.getStringVariable("database.schema");
        LOGGER.info("Binding JNDI Database schema Name gave {}", jndiFoundPath);

        if (jndiFoundPath == null || "".equals(jndiFoundPath)) {
            LOGGER.info(
                    "Binding JND Database schema Name gave null. Returning property {}",
                    super.getProperty("database.schema", String.class));
            jndiFoundPath = super.getProperty("database.schema", String.class);
        }
        return jndiFoundPath;
    }

    /**
     * Database datasource property name.
     *
     * @return the datasource JNDI property name.
     */
    @Bean
    public String datasourceJndiName() {
        LOGGER.info("database JNDI name set to {}",
                super.getProperty(DATASOURCE_JNDI_NAME, String.class));
        return super.getProperty(DATASOURCE_JNDI_NAME, String.class);
    }

}
