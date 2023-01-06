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

    private Pair mapRowToPair(ResultSet res) throws SQLException {
        long userId = res.getLong("user_id");
        long opponentId = res.getLong("opponent_id");
        User user = userRepository.findById(userId);
        User opponent = userRepository.findById(opponentId);
        return new Pair(user, opponent);
    }
}