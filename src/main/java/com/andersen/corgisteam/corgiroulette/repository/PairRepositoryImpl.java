package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PairRepositoryImpl implements PairRepository{

    private static final String QUERY_FOR_PAIRS = "SELECT * FROM pairs";
    private static final String QUERY_FOR_SAVE_PAIR = "INSERT INTO pairs VALUES (?,?)";
    private static final String QUERY_FOR_CHECK = "SELECT * FROM pairs WHERE (user_id = ? and opponent_id = ?) "
            + "OR (user_id = ? and opponent_id = ?)";


    private static final String ADD_PAIR_OPPONENTS = "INSERT INTO users_opponents VALUES (?,?)";
    private static final String DELETE_USERS_OPPONENTS = "DELETE FROM users_opponents " +
            "WHERE chosen_user_id = ? OR opponent_user_id = ?";
    private static final String DELETE_PAIR_QUERY = "DELETE FROM users_opponents " +
            "WHERE (chosen_user_id = ? AND opponent_user_id = ?) OR (opponent_user_id = ? AND chosen_user_id = ?)";

    private final UserRepository userRepository;

    public PairRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(Pair pair) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_SAVE_PAIR)) {
            statement.setLong(1, pair.getUser().getId());
            statement.setLong(2, pair.getOpponent().getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't save pair. No rows affected. Pair: %s", pair));
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save pair. Pair: %s", pair), e);
        }
    }

    @Override
    public List<Pair> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_PAIRS)) {

            ResultSet res = statement.executeQuery();

            List<Pair> pairs = new ArrayList<>();
            while (res.next()) {
                pairs.add(mapRowToPair(res));
            }
            return pairs;
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't find all pairs", e);
        }
    }

    @Override
    public boolean checkPair(Pair pair) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_CHECK)) {
            statement.setLong(1, pair.getUser().getId());
            statement.setLong(2, pair.getOpponent().getId());
            statement.setLong(4, pair.getUser().getId());
            statement.setLong(3, pair.getOpponent().getId());
            ResultSet res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't check pair. Pair: %s", pair), e);
        }
    }

    @Override
    public void createPairInBattleTable(long userId, long opponentId) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PAIR_OPPONENTS)) {
            statement.setLong(1, userId);
            statement.setLong(2, opponentId);
            statement.execute();
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't create pair of opponents", e);
        }
    }

    @Override
    public void deleteUserOpponent(long userId) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_OPPONENTS)) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format(
                    "Can't delete all pairs of opponents with for user. User id: %s", userId), e);
        }
    }

    @Override
    public void deletePair(Pair pair) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PAIR_QUERY)) {
            statement.setLong(1, pair.getUser().getId());
            statement.setLong(2, pair.getOpponent().getId());

            statement.setLong(3, pair.getUser().getId());
            statement.setLong(4, pair.getOpponent().getId());

            statement.execute();
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't delete pair. User id: %s, Opponent id: %s",
                    pair.getUser().getId(), pair.getOpponent().getId()), e);
        }
    }

    private Pair mapRowToPair(ResultSet res) throws SQLException {
        long userId = res.getLong("user_id");
        long opponentId = res.getLong("opponent_id");
        User user = userRepository.findById(userId);
        User opponent = userRepository.findById(opponentId);
        return new Pair(user, opponent);
    }
}