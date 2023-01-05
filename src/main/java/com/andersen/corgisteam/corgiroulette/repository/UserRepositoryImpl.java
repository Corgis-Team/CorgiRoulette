package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String QUERY_FOR_SAVING = "INSERT INTO users(name, surname, team_id, is_chosen, last_duel) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String QUERY_FOR_ALL_USERS = "SELECT * FROM users";

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
                    user, e.getMessage()));
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_ALL_USERS)) {

            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (res.next()) {
                users.add(mapRowToUsers(res));
            }

            return users;
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't get all users");
        }
    }

    private User mapRowToUsers(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String surname = res.getString("surname");
        Team team = (Team) res.getObject("team");
        return new User(id, name, surname, team);
    }
}