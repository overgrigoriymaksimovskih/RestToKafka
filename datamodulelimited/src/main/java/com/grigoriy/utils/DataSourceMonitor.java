package com.grigoriy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataSourceMonitor implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceMonitor.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting DataSource Monitor...");

        if (dataSource instanceof BasicDataSource) { // Commons DBCP2
            BasicDataSource dbcpDataSource = (BasicDataSource) dataSource;
            logger.info("Using Commons DBCP2:");
            logger.info("  URL: {}", dbcpDataSource.getUrl());
            logger.info("  Username: {}", dbcpDataSource.getUsername());
            logger.info("  Initial Size: {}", dbcpDataSource.getInitialSize());
            logger.info("  Max Total: {}", dbcpDataSource.getMaxTotal());
            logger.info("  Max Idle: {}", dbcpDataSource.getMaxIdle());
            logger.info("  Min Idle: {}", dbcpDataSource.getMinIdle());
            logger.info("  Max Wait Millis: {}", dbcpDataSource.getMaxWaitMillis());
            logger.info("  Test On Borrow: {}", dbcpDataSource.getTestOnBorrow());
            logger.info("  Validation Query: {}", dbcpDataSource.getValidationQuery());
        } else {
            logger.warn("Unknown DataSource type: {}", dataSource.getClass().getName());
        }

        // Проверка соединения
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Connection to database successful!");
        } catch (SQLException e) {
            logger.error("Failed to connect to the database: {}", e.getMessage());
        }

        logger.info("DataSource Monitor started successfully.");
    }
}

