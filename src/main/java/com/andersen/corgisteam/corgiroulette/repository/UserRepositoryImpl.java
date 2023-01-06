package com.andersen.corgisteam.corgiroulette.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;

public class UserRepositoryImpl implements UserRepository {

    private static final String QUERY_FOR_SAVING = "INSERT INTO users(name, surname, team_id, is_chosen, last_duel) " +
        "VALUES (?, ?, ?, ?, ?)";
    private static final String QUERY_FOR_UPDATE = "UPDATE users SET name = ?, surname = ?, team_id = ? WHERE id = ?";
    private static final String QUERY_FOR_ALL_USERS = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USERS_BY_FULL_NAME_QUERY = "SELECT * FROM users WHERE " +
        "LOWER(CONCAT(CONCAT(name, ' '), surname)) LIKE CONCAT(?) OR " +
        "LOWER(CONCAT(CONCAT(surname, ' '), name)) LIKE CONCAT(?)";
    private static final String FIND_USERS_BY_TEAM_ID = "SELECT * FROM users WHERE team_id = ?";
    private static final String QUERY_FOR_HANDLE_PAIR = "UPDATE users SET is_chosen = true, last_duel = ? " +
            "WHERE id in (?,?)";
    private static final String QUERY_FOR_UPDATE_ALL = "UPDATE users SET is_chosen = false";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    
    private final TeamRepository teamRepository;

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

            if (user.getTeam() != null) {
                statement.setLong(3, user.getTeam().getId());
            }
            else {
                statement.setNull(3, Types.BIGINT);
            }

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
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save user. No rows affected. User: %s", user), e);
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

            if (user.getTeam() != null) {
                statement.setLong(3, user.getTeam().getId());
            }
            else {
                statement.setNull(3, Types.BIGINT);
            }

            statement.setLong(4, user.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't update user. No rows affected. User: %s", user));
            }

            connection.commit();
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't update user. No rows affected. User: %s", user), e);
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
        }
        catch (SQLException e) {
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
            }
            else {
                throw new EntityNotFoundException(String.format("User not found. User id: %s", id));
            }

        }
        catch (SQLException e) {
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
                throw new EntityNotFoundException(String.format("No users with name %s were found", fullName));
            }

            connection.commit();

            return users;
        }
        catch (SQLException e) {
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
        }
        catch (SQLException e) {
            throw new QueryExecutionException(String.format("No users with team id %s were found", teamId), e);
        }
    }

    @Override
    public void handlePair(long userId, long opponentId) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_HANDLE_PAIR)) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, userId);
            statement.setLong(3, opponentId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't handle pair of user id: %s & %s", userId, opponentId));
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't handle pair of user id: %s & %s", userId, opponentId), e);
        }
    }

    @Override
    public void refresh() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_UPDATE_ALL)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException("Can't refresh chosen status for all users");
            }
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't refresh chosen status for all users", e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("User not found. User id: %s", id));
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
        long teamId = res.getLong("team_id");
        Team team = res.wasNull() ? null : teamRepository.findById(teamId);
        boolean isChosen = res.getBoolean("is_chosen");
        LocalDateTime lastDuel = res.getTimestamp("last_duel").toLocalDateTime();
        return new User(id, name, surname, team, isChosen, lastDuel);
    }
}