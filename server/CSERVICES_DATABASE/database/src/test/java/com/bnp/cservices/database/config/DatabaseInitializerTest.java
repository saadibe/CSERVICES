package com.bnp.cservices.database.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseInitializerTest {

    @Mock
    private Flyway flyway;

    private DatabaseInitializer databaseInitializer;

    @Before
    public void prepare() {
        this.databaseInitializer = new DatabaseInitializer(this.flyway);
    }

    @Test
    public void flyway_should_check_schema_baseline() {
        this.databaseInitializer.initialize();

        Mockito.verify(this.flyway, Mockito.times(1)).isBaselineOnMigrate();
    }

    @Test
    public void flyway_should_do_schema_baseline() {
        Mockito.when(this.flyway.isBaselineOnMigrate()).thenReturn(true);

        this.databaseInitializer.initialize();

        Mockito.verify(this.flyway, Mockito.times(1)).baseline();
    }

    @Test
    public void flyway_should_keep_migration_on_schema_baseline_exception() {
        Mockito.when(this.flyway.isBaselineOnMigrate()).thenReturn(true);
        Mockito.when(this.flyway.isBaselineOnMigrate()).thenThrow(new FlywayException());

        this.databaseInitializer.initialize();

        Mockito.verify(this.flyway, Mockito.times(1)).migrate();
    }

    @Test
    public void flyway_should_migrate_on_schema_baseline_ok() {
        Mockito.when(this.flyway.isBaselineOnMigrate()).thenReturn(true);

        this.databaseInitializer.initialize();

        Mockito.verify(this.flyway, Mockito.times(1)).migrate();
    }

    @Test
    public void flyway_should_finish_on_migration_exception() {
        Mockito.when(this.flyway.isBaselineOnMigrate()).thenReturn(true);
        Mockito.when(this.flyway.migrate()).thenThrow(new FlywayException());

        this.databaseInitializer.initialize();
    }

}
