package com.example.demo.config;

import com.example.demo.services.DatabaseMigrationService;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Order(0)
public class DatabaseMigrationConfig {

    private final DatabaseMigrationService databaseMigrationService;

    public DatabaseMigrationConfig(DatabaseMigrationService databaseMigrationService) {
        this.databaseMigrationService = databaseMigrationService;
    }

    @Bean
    @Order(0)
    public void setDatabaseMigrationBegin() {
        databaseMigrationService.setDatabaseMigrationTablesPath("database/migration/table");

        databaseMigrationService.createTableFromResource("public", "crew");
        databaseMigrationService.createTableFromResource("public", "member");

    }
}
