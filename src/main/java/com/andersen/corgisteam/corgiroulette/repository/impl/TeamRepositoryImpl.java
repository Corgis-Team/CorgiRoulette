package com.andersen.corgisteam.corgiroulette.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;

public class TeamRepositoryImpl implements TeamRepository {

    private static final String SAVE_TEAM_QUERY = "INSERT INTO teams (name) VALUES (?)";
    private static final String DELETE_TEAM_QUERY = "DELETE FROM teams WHERE id = ?";
    private static final String QUERY_FOR_ALL_TEAMS = "SELECT * FROM teams";
    private static final String FIND_TEAM_BY_ID_QUERY = "SELECT * FROM teams WHERE id = ?";
    private static final String FIND_TEAM_BY_NAME_QUERY = "SELECT * FROM teams WHERE LOWER(name) LIKE LOWER(?)";
    private static final String UPDATE_TEAM_QUERY = "UPDATE teams SET name = ? WHERE id = ?";

    @Override
    public Team save(Team team) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_TEAM_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, team.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't save team. No rows affected. Team: %s", team));
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                team.setId(generatedKeys.getLong(1));
            }
            else {
                throw new QueryExecutionException(String.format("Can't save team. No ID obtained. Team: %s", team));
            }

            return team;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save team. Team: %s", team), e);
        }
    }

    @Override
    public List<Team> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_ALL_TEAMS)) {

            ResultSet res = statement.executeQuery();

            List<Team> users = new ArrayList<>();
            while (res.next()) {
                users.add(mapRowToTeam(res));
            }

            return users;
        }
        catch (SQLException e) {
            throw new QueryExecutionException("Can't get all teams", e);
        }
    }

    @Override
    public Team findById(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TEAM_BY_ID_QUERY)) {

            statement.setLong(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return mapRowToTeam(res);
            }
            else {
                throw new EntityNotFoundException(String.format("Team not found. Id: %s", id));
            }
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Team not found. Id: %s", id), e);
        }
    }

    @Override
    public List<Team> findByName(String name) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TEAM_BY_NAME_QUERY)) {

            statement.setString(1, "%" + name + "%");

            ResultSet res = statement.executeQuery();

            List<Team> teams = new ArrayList<>();

            while (res.next()) {
                teams.add(mapRowToTeam(res));
            }

            if (teams.isEmpty()) {
                throw new QueryExecutionException(String.format("No teams with name %s were found", name));
            }

            return teams;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("No teams with name %s were found", name), e);
        }
    }

    @Override
    public void update(Team team) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEAM_QUERY)) {

            statement.setString(1, team.getName());
            statement.setLong(2, team.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't update team. No rows affected. Team: %s", team));
            }
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't update team. No rows affected. Team: %s", team), e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEAM_QUERY)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Team not found. Team id: %s", id));
            }
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't delete team. Team id: %s", id), e);
        }
    }

    private Team mapRowToTeam(ResultSet res) throws SQLException {
        long id = res.getLong("id");
        String name = res.getString("name");
        return new Team(id, name);
    }
}
