package com.andersen.corgisteam.corgiroulette.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.lang.String.format;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;

public class TeamRepositoryImpl implements TeamRepository {

    private static final String SAVE_TEAM_QUERY = "INSERT INTO teams (name) VALUES (?)";

    @Override

    public Team save(Team team) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_TEAM_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, team.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(format("Can't save team. No rows affected. Team: %s", team));
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                team.setId(generatedKeys.getLong(1));
            }
            else {
                throw new QueryExecutionException(format("Can't save team. No ID obtained. Team: %s", team));
            }

            return team;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save team. Team: %s",team), e);
        }
    }

    @Override
    public List<Team> findAll() {
        return null;
    }

    @Override
    public Team findById(long id) {
        return null;
    }

    @Override
    public void update(Team team) {

    }

    @Override
    public void delete(long id) {

    }
}
