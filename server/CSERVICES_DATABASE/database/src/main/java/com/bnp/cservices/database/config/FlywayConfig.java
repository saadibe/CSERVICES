package com.bnp.cservices.database.config;

import com.bnp.cservices.config.SpringConfig;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * The configuration for the flyway module. Flyway updates the database when a new sql file in found in the migration
 * resource folder.
 */
@Configuration
public class FlywayConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayConfig.class.getCanonicalName());


    /**
     * Initiate the configuration for the flyway module.
     *
     * @param environment the Spring environment.
     */
    public FlywayConfig(Environment environment) {
        super(environment);
    }


    /**
     * FLYWAY - Database loader. Create the Engine capable to load flyway migration scripts in the database.
     *
     * @return the Flyway engine.
     */
    @Primary
    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();

        flyway.setDataSource(this.getContext().getBean("dataSource", DataSource.class));
        flyway.setSchemas(super.getContext().getBean("schemaName", String.class));
        flyway.setSqlMigrationPrefix(super.getProperty(("flyway.sql.script.prefix")));

        LOGGER.info("Flyway loaded");
        return flyway;
    }


    /**
     * The database initializer.
     *
     * @return the database initializer.
     */
    @Bean
    public DatabaseInitializer databaseInitializer() {
        LOGGER.info("DataBaseInitializer loaded");
        return new DatabaseInitializer(this.flyway());
    }
}
