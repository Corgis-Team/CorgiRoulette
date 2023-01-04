package com.andersen.corgisteam.corgiroulette.repository;

import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;

class TeamRepositoryImplTest {

    private final TeamRepository teamRepository = new TeamRepositoryImpl();

    @BeforeAll
    static void beforeAll() {
        Flyway.configure()
            .dataSource(DatabaseConfig.getDataSource())
            .load()
            .migrate();
    }

    @Test
    void save() {
        Team team = new Team("Blue");
        team = teamRepository.save(team);
        Assertions.assertEquals(1, team.getId());
    }

    @Test
    void saveWithEmptyName() {
        Team team = new Team();
        Assertions.assertThrows(QueryExecutionException.class, () -> teamRepository.save(team));
    }

    @AfterAll
    static void afterAll() {
        try {
            DatabaseConfig.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}