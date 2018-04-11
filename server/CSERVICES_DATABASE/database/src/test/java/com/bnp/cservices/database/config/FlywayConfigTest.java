package com.bnp.cservices.database.config;

import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@RunWith(MockitoJUnitRunner.class)
public class FlywayConfigTest {

    @Mock
    private ApplicationContext context;

    @Mock
    private Environment environment;

    @Mock
    private DataSource dataSource;

    private FlywayConfig config;

    @Before
    public void prepare() {
        this.config = new FlywayConfig(this.environment);
        this.config.setApplicationContext(this.context);

        Mockito.when(this.context.getBean("schemaName", String.class)).thenReturn("schema");
        Mockito.when(this.environment.getProperty("flyway.sql.script.prefix")).thenReturn("");

    }

    @Test
    public void flyway_should_be_filled() {
        Mockito.when(this.context.getBean("dataSource", DataSource.class)).thenReturn(this.dataSource);

        Flyway flyway = this.config.flyway();
        Assertions.assertThat(flyway).isNotNull();
        Assertions.assertThat(flyway).isExactlyInstanceOf(Flyway.class);
        Assertions.assertThat(flyway.getSqlMigrationPrefix()).isEqualTo("");
        Assertions.assertThat(flyway.getSchemas()[0]).isEqualTo("schema");
    }

    @Test
    public void database_initializer_should_be_filled() {
        DatabaseInitializer databaseInitializer = this.config.databaseInitializer();
        Assertions.assertThat(databaseInitializer).isNotNull();
        Assertions.assertThat(databaseInitializer).isExactlyInstanceOf(DatabaseInitializer.class);
    }
}
