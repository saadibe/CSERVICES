package com.bnp.cservices.database.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class in charge of the database initialization.
 */
public class DatabaseInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class.getCanonicalName());

    private final Flyway flyway;


    /**
     * Instantiate the database initializer engine.
     * Uses Flyway to set up the database.
     *
     * @param flyway the flyway engine.
     */
    public DatabaseInitializer(Flyway flyway) {
        this.flyway = flyway;
    }


    /**
     * Create the database schema if it not exists then executes the The SQL scripts from the last one executed.
     */
    public void initialize() {
        this.initializeSchema();
        this.initializeTables();
    }


    /**
     * Creates the Database schema if required.
     */
    private void initializeSchema() {
        try {
            if (this.flyway.isBaselineOnMigrate()) {
                LOGGER.info("Initialization of the DB Schema");
                this.flyway.baseline();
                LOGGER.info("Initialization of the DB Schema succeeded");
            }
        }
        catch (FlywayException ex) {
            LOGGER.error("Error while initializing the database schema", ex);
        }
    }


    /**
     * Executes SQL scripts one by one (starts from the last one executed successfully).
     */
    private void initializeTables() {
        try {
            LOGGER.info("Database table update");
            this.flyway.migrate();
            LOGGER.info("Database tables successfully updated");
        }
        catch (FlywayException ex) {
            LOGGER.error("Error while updating the database", ex);
        }
    }
}
