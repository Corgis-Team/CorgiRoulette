package com.andersen.corgisteam.corgiroulette.database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.flywaydb.core.Flyway;

@WebListener
public class FlywayListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Flyway.configure()
            .dataSource(DatabaseConfig.getDataSource())
            .load()
            .migrate();
    }
}
