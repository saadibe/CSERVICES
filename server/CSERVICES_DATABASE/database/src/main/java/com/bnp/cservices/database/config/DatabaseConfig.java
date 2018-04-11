package com.bnp.cservices.database.config;

import com.bnp.cservices.config.SpringConfig;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The application database's configuration.
 *
 * @author chloe.mahalin@itametis.com
 */
@Import({
    FlywayConfig.class
})
@PropertySource(
        encoding = "UTF-8",
        value = "classpath:database.properties"
)
@EnableTransactionManagement
@Configuration
public class DatabaseConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class.getCanonicalName());

    public final static String UNIT_NAME = "my-unit";

    /**
     * Initiate the database configuration and connexion.
     *
     * @param environment the Spring environment.
     */
    public DatabaseConfig(Environment environment) {
        super(environment);
    }

    /**
     * JPA & HIBERNATE CONFIGURATION - Normaly defined inside properties files. Set the Jpa properties from the property
     * file.
     *
     * @return the properties.
     */
    @Bean
    public Properties jpaProperties() {
        Properties props = new Properties();

        super.putProperties("hibernate.dialect", props);
        super.putProperties("hibernate.hbm2ddl.auto", props);
        props.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        props.setProperty("org.hibernate.flushMode", "AUTO");
        props.setProperty("hibernate.default_schema", super.getContext().getBean("schemaName", String.class));

        LOGGER.info("Hibernate JPA Properties loaded");
        return props;
    }

    /**
     * SPRING ADAPTER - Spring object wrapping the Hibernate loader. Load the vendor. Spring way to map Hibernate stuff
     * behind JPA interface.
     *
     * @return the jpa vendor.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabase(Database.ORACLE);
        jpaVendorAdapter.setShowSql(super.getProperty("hibernate.show.sql", Boolean.class));
        jpaVendorAdapter.setDatabasePlatform(super.getProperty("hibernate.dialect"));
        jpaVendorAdapter.setGenerateDdl(false);

        LOGGER.info("Hibernate JPA Adapter loaded");
        return jpaVendorAdapter;
    }

    /**
     * SPRING AOP WEAVER.
     *
     * @return the weaver.
     */
    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        LOGGER.info("Spring Load Time Weaver loaded");
        return new InstrumentationLoadTimeWeaver();
    }

    /**
     * SPRING CONTAINER - Factory of JPA EntityManagerFactory. An entity Manager factory creates entity Manager. This
     * factory creates the factory.
     *
     * @return the factory of entity Manager factory.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // Ensure DB is up before any instance of Hibernate
        this.getContext().getBean("databaseInitializer", DatabaseInitializer.class).initialize();
        LOGGER.info("Database Schema migrated by Flyway");

        // Spring + Hibernate part
        LocalContainerEntityManagerFactoryBean container = new LocalContainerEntityManagerFactoryBean();

        container.setJpaVendorAdapter(this.jpaVendorAdapter());
        container.setPersistenceUnitName(UNIT_NAME);
        container.setPackagesToScan("com.bnp.cservices.entities");
        container.setDataSource(this.getContext().getBean("dataSource", DataSource.class));
        container.setLoadTimeWeaver(this.loadTimeWeaver());
        container.setJpaProperties(this.jpaProperties());

        // Force reload of Container Factory configuration => so perform a double scan of entity but leave in order to prevent mistake even
        // if those one a very unprobable :
        container.afterPropertiesSet();

        LOGGER.info("Spring Container of EntityManagerFactory loaded");

        LOGGER.info("END OF DATABASE INITIALISATION");
        return container;
    }

    /**
     * The transaction manager. Processes functional transaction grouped under an @Transactionnal annotation.
     *
     * @return the transaction manager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setPersistenceUnitName(UNIT_NAME);
        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());

        return transactionManager;
    }

    /**
     * The type of exception managed for persistence.
     *
     * @return the exception.
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Return the unit name.
     *
     * @return the unit name.
     */
    @Bean
    public String unitName() {
        return UNIT_NAME;
    }

    ///!!!!!!!!!!!!!!!!!! DATA SOURCE JNDI OR NOT !!!!!!!!!!!!!!!!!!///
    /**
     * Configure Data source / Connection pool. BONECP. For dev purpose only. The Datasource must be retrieved from
     * JNDI.
     *
     * @return the Connection pool configuration.
     */
    @Bean
    public BoneCPConfig connectionPoolConfig() {
        LOGGER.info("STARTING OF DATABASE INITIALISATION");

        BoneCPConfig config = new BoneCPConfig();

        try {
            // load the database driver (make sure this is in your classpath!)
            Class.forName(super.getProperty("database.driver"));
        } catch (Exception e) {
            LOGGER.error("No driver found in classpath");
        }

        // Database instance
        config.setJdbcUrl(super.getProperty("database.url"));

        // Database user
        config.setUsername(super.getProperty("database.user"));
        config.setPassword(super.getProperty("database.password"));

        // Database configuration
        config.setMinConnectionsPerPartition(super.getProperty("database.min.connections.per.partition", Integer.class));
        config.setMaxConnectionsPerPartition(super.getProperty("database.max.connections.per.partition", Integer.class));
        config.setPartitionCount(super.getProperty("database.partition.number", Integer.class));
        config.setAcquireIncrement(super.getProperty("database.acquire.increment", Integer.class));

        //With oracle, to avoid sql error
        // This should keep idle connections from timing out
        config.setConnectionTestStatement("select 1 from DUAL");
        config.setTransactionRecoveryEnabled(true);
        // Disable bugged BoneCP 0.8.0 unclosed connection tracking
        config.setDisableConnectionTracking(true);

        LOGGER.info("Bone-CP Configuration loaded");
        return config;
    }

    /**
     * Load the Data source / Connection pool for the referential database.
     *
     * @return the data source.
     */
    @Bean
    public DataSource dataSource() {
        LOGGER.info("DataSource loaded");
        try {
            DataSource jndiDataSource = new JndiDataSourceLookup().getDataSource(this.getContext().getBean("datasourceJndiName", String.class));
            LOGGER.info("DB - JNDI datasource is OK.");
            if (jndiDataSource == null) {
                LOGGER.info("DB - JNDI datasource returned null object - Bone-CP DataSource loaded");
                jndiDataSource = new BoneCPDataSource(this.connectionPoolConfig());
            }
            return jndiDataSource;
        } catch (BeansException | DataSourceLookupFailureException e) {
            LOGGER.info("Error when lookup JNDI datasource => Bone-CP DataSource loaded");
            return new BoneCPDataSource(this.connectionPoolConfig());
        }
    }

}
