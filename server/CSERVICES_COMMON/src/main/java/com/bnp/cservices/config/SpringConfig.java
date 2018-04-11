package com.bnp.cservices.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * Global Spring configuration class for all Spring conf classes.
 * Allow them to easily access properties and beans.
 *
 * @author itametis (chloe.mahalin@itametis.com)
 */
public class SpringConfig implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfig.class.getCanonicalName());

    @Autowired
    private ApplicationContext context;

    @Autowired
    private final Environment env;


    /**
     * Make the environment available for every config class.
     *
     * @param environment the spring environment.
     */
    protected SpringConfig(Environment environment) {
        this.env = environment;
    }


    /**
     * Returns the shared Spring context (by all the configuration classes).
     *
     * @return The common Spring context.
     */
    protected ApplicationContext getContext() {
        return this.context;
    }


    /**
     * The Spring environment.
     *
     * @return the environment.
     */
    protected Environment getEnvironment() {
        return this.env;
    }


    /**
     * Return a property from a property file.
     *
     * @param key the key to find the property.
     * @return the value of the property.
     */
    protected String getProperty(String key) {
        String prop = this.env.getProperty(key);
        LOGGER.debug("Property loaded '{}' as String", key);
        return prop;
    }


    /**
     * Return a property from a property file as the given type.
     *
     * @param key  the key to find the property.
     * @param type the expected type of the property.
     * @param <T>  the type of the property.
     * @return the value of the property.
     */
    protected <T> T getProperty(String key, Class<T> type) {
        T prop = this.env.getProperty(key, type);
        LOGGER.debug("Property loaded '{}' as {}", key, type.getSimpleName());
        return prop;
    }


    /**
     * Add a new property from Spring context in the props property holder.
     *
     * @param key   the key to the property.
     * @param props the property holder getting properties from Spring context.
     */
    protected void putProperties(String key, Properties props) {
        LOGGER.debug("Property set '{}'", key);
        props.setProperty(key, this.env.getProperty(key));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

}
