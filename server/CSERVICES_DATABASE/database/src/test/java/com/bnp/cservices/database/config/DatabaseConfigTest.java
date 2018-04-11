package com.bnp.cservices.database.config;

import static com.bnp.cservices.database.config.DatabaseConfig.UNIT_NAME;
import java.util.Properties;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseConfigTest {

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private ApplicationContext context;

    @Mock
    private Environment environment;

    private DatabaseConfig config;

    @Before
    public void prepare() {
        this.config = new DatabaseConfig(this.environment);
        this.config.setApplicationContext(this.context);

        Mockito.when(this.environment.getProperty("database.driver")).thenReturn("oracle.jdbc.OracleDriver");

        Mockito.when(this.environment.getProperty("database.url")).thenReturn("jdbcUrl");
        Mockito.when(this.environment.getProperty("database.user")).thenReturn("user");
        Mockito.when(this.environment.getProperty("database.password")).thenReturn("password");

        Mockito.when(this.environment.getProperty("database.min.connections.per.partition", Integer.class)).thenReturn(4);
        Mockito.when(this.environment.getProperty("database.max.connections.per.partition", Integer.class)).thenReturn(5);
        Mockito.when(this.environment.getProperty("database.partition.number", Integer.class)).thenReturn(6);
        Mockito.when(this.environment.getProperty("database.acquire.increment", Integer.class)).thenReturn(7);

        Mockito.when(this.environment.getProperty("hibernate.dialect")).thenReturn("org.hibernate.dialect.Oracle10gDialect");
        Mockito.when(this.environment.getProperty("hibernate.hbm2ddl.auto")).thenReturn("validate");
        Mockito.when(this.environment.getProperty("hibernate.schema")).thenReturn("schema");
        Mockito.when(this.environment.getProperty("database.schema")).thenReturn("schema");
        Mockito.when(this.environment.getProperty("hibernate.default_schema")).thenReturn("schema");

        Mockito.when(this.context.getBean("schemaName", String.class)).thenReturn("schema");

        Mockito.when(this.environment.getProperty("hibernate.show.sql", Boolean.class)).thenReturn(false);
    }

    @Test
    public void unit_name_should_be_referential_unit_name() {
        Assertions.assertThat(UNIT_NAME).isEqualTo("my-unit");
    }

    @Test
    public void jpa_properties_should_be_filled() {
        Properties properties = this.config.jpaProperties();
        Assertions.assertThat(properties.getProperty("hibernate.dialect")).isEqualTo("org.hibernate.dialect.Oracle10gDialect");
        Assertions.assertThat(properties.getProperty("hibernate.hbm2ddl.auto")).isEqualTo("validate");
        Assertions.assertThat(properties.getProperty("hibernate.default_schema")).isEqualTo("schema");
    }

    @Test
    public void jpa_vendor_adapter_should_be_filled() {
        JpaVendorAdapter vendor = this.config.jpaVendorAdapter();

        Assertions.assertThat(vendor).isNotNull();
        Assertions.assertThat(vendor).isInstanceOf(HibernateJpaVendorAdapter.class);
        Assertions.assertThat(vendor.getJpaDialect()).isInstanceOf(HibernateJpaDialect.class);
        //Assertions.assertThat(vendor.getJpaPropertyMap().get("hibernate.connection.release_mode")).isEqualTo("ON_CLOSE");
        Assertions.assertThat(vendor.getJpaPropertyMap().get("hibernate.dialect")).isEqualTo("org.hibernate.dialect.Oracle10gDialect");
    }

    @Test
    public void load_time_weaver_should_be_filled() {
        LoadTimeWeaver timeWeaver = this.config.loadTimeWeaver();

        Assertions.assertThat(timeWeaver).isNotNull();
        Assertions.assertThat(timeWeaver).isInstanceOf(InstrumentationLoadTimeWeaver.class);
    }

    @Test
    public void exception_translation_should_be_filled() {
        PersistenceExceptionTranslationPostProcessor translation = this.config.exceptionTranslation();

        Assertions.assertThat(translation).isNotNull();
        Assertions.assertThat(translation).isInstanceOf(PersistenceExceptionTranslationPostProcessor.class);
    }

}
