package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class UserRepositoryImpl implements UserRepository {

    private final TeamRepository teamRepository;

    private static final String QUERY_FOR_SAVING = "INSERT INTO users(name, surname, team_id, is_chosen, last_duel) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String QUERY_FOR_UPDATE = "UPDATE users SET name = ?, surname = ? WHERE id = ?";
    private static final String QUERY_FOR_ALL_USERS = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USERS_BY_FULL_NAME_QUERY = "SELECT * FROM users WHERE " +
            "LOWER(CONCAT(CONCAT(name, ' '), surname)) LIKE CONCAT(?) OR " +
            "LOWER(CONCAT(CONCAT(surname, ' '), name)) LIKE CONCAT(?)";
    private static final String FIND_USERS_BY_TEAM_ID = "SELECT * FROM users WHERE team_id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";

    public UserRepositoryImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    @Override
    public void save(User user) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_SAVING,
                     Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setLong(3, user.getTeam().getId());
            statement.setBoolean(4, user.isChosen());
            statement.setTimestamp(5, Timestamp.valueOf(user.getLastDuel()));
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't save user. No rows affected. User: %s", user));
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }

            connection.commit();
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save user. No rows affected. User: %s\nReason: %s",
                    user, e.getMessage()), e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_UPDATE)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setLong(3, user.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't update user. No rows affected. User: %s", user));
            }

            connection.commit();
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't update user. No rows affected. User: %s\nReason:%s",
                    user, e.getMessage()), e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_ALL_USERS)) {

            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (res.next()) {
                users.add(mapRowToUser(res));
            }

            return users;
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't get all users", e);
        }
    }
    
    @Override
    public User findById(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            statement.setLong(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                User user = mapRowToUser(res);
                connection.commit();
                return user;
            } else {
                throw new QueryExecutionException(String.format("User not found. User id: %s", id));
            }

        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("User not found. User id: %s", id), e);
        }
    }

    @Override
    public List<User> findAllByFullName(String fullName) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_FULL_NAME_QUERY)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            statement.setString(1, "%" + fullName + "%");
            statement.setString(2, "%" + fullName + "%");

            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (res.next()) {
                users.add(mapRowToUser(res));
            }

            if (users.isEmpty()) {
                throw new QueryExecutionException(String.format("No users with name %s were found", fullName));
            }

            connection.commit();

            return users;
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("No users with name %s were found", fullName), e);
        }
    }

    @Override
    public List<User> findAllByTeamId(long teamId) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_TEAM_ID)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            statement.setLong(1, teamId);

            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (res.next()) {
                users.add(mapRowToUser(res));
            }

            connection.commit();

            return users;
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("No users with team id %s were found", teamId), e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(format("User not found. User id: %s", id));
            }
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't delete user. User id: %s", id), e);
        }
    }

    private User mapRowToUser(ResultSet res) throws SQLException {
        long id = res.getLong("id");
        String name = res.getString("name");
        String surname = res.getString("surname");
        int teamId = res.getInt("team_id");
        Team team = teamRepository.findById(teamId);
        boolean isChosen = res.getBoolean("is_chosen");
        LocalDateTime lastDuel = res.getTimestamp("last_duel").toLocalDateTime();
        return new User(id, name, surname, team, isChosen, lastDuel);
    }
}