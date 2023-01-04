package com.andersen.corgisteam.corgiroulette.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConfig {

    private static final HikariConfig config = new HikariConfig("/db.properties");
    private static final HikariDataSource dataSource = new HikariDataSource(config);

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
