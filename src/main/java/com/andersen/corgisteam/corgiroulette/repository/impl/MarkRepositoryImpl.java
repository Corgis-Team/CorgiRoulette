package com.andersen.corgisteam.corgiroulette.repository.impl;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.dto.ResponseGroupedMarksDto;
import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.MarkRepository;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MarkRepositoryImpl implements MarkRepository {

    private static final String FIND_MARK_BY_ID_QUERY = "SELECT * FROM marks WHERE id = ?";
    private static final String FIND_ALL_MARKS_GROUPED_BY_USER_QUERY = "SELECT user_id, SUM(mark) AS mark " +
            "FROM marks GROUP BY user_id " +
            "ORDER BY mark DESC";
    private static final String FIND_ALL_MARKS_BY_USER_ID_QUERY = "SELECT * FROM marks WHERE user_id = ?" +
            "ORDER BY date_time DESC";
    private static final String SAVE_MARK_QUERY = "INSERT INTO marks(user_id, mark, date_time) VALUES (?,?,?)";
    private static final String UPDATE_MARK_QUERY = "UPDATE marks SET mark = ? WHERE id = ?";
    private static final String DELETE_MARK_QUERY = "DELETE FROM marks WHERE id = ?";

    private final UserRepository userRepository;

    public MarkRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mark save(Mark mark) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_MARK_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, mark.getUser().getId());
            statement.setDouble(2, mark.getMark());
            statement.setTimestamp(3, Timestamp.valueOf(mark.getDateTime()));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't save mark. No rows affected. Mark: %s", mark));
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                mark.setId(generatedKeys.getLong(1));
            } else {
                throw new QueryExecutionException(String.format("Can't save mark. No ID obtained. Mark: %s", mark));
            }

            return mark;
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't save mark. Mark: %s", mark), e);
        }
    }

    @Override
    public List<ResponseGroupedMarksDto> findAllMarksGroupedByUser() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MARKS_GROUPED_BY_USER_QUERY)) {

            ResultSet res = statement.executeQuery();

            List<ResponseGroupedMarksDto> markDtoList = new ArrayList<>();
            while (res.next()) {
                ResponseGroupedMarksDto markDto = new ResponseGroupedMarksDto();
                long userId = res.getLong("user_id");
                User user = res.wasNull() ? null : userRepository.findById(userId);
                markDto.setUser(user);
                markDto.setMark(res.getDouble("mark"));
                markDtoList.add(markDto);
            }

            return markDtoList;
        } catch (SQLException e) {
            throw new QueryExecutionException("Can't get all marks", e);
        }
    }

    @Override
    public Mark findById(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_MARK_BY_ID_QUERY)) {

            statement.setLong(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return mapRowToMark(res);
            } else {
                throw new EntityNotFoundException(String.format("Mark not found. Id: %s", id));
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Mark not found. Id: %s", id), e);
        }
    }

    @Override
    public List<Mark> findAllByUserId(long userId) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MARKS_BY_USER_ID_QUERY)) {

            statement.setLong(1, userId);

            ResultSet res = statement.executeQuery();

            List<Mark> marks = new ArrayList<>();

            while (res.next()) {
                marks.add(mapRowToMark(res));
            }

            return marks;
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Marks not found. User id: %s", userId), e);
        }
    }

    @Override
    public void update(Mark mark) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MARK_QUERY)) {

            statement.setDouble(1, mark.getMark());
            statement.setLong(2, mark.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Can't update mark. No rows affected. Mark: %s", mark));
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't update mark. No rows affected. Mark: %s", mark), e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_MARK_QUERY)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(String.format("Mark not found. Mark id: %s", id));
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(String.format("Can't delete mark. Mark id: %s", id), e);
        }
    }

    private Mark mapRowToMark(ResultSet res) throws SQLException {
        long id = res.getLong("id");
        long userId = res.getLong("user_id");
        User user = res.wasNull() ? null : userRepository.findById(userId);
        double mark = res.getDouble("mark");
        LocalDateTime dateTime = res.getTimestamp("date_time").toLocalDateTime();

        return new Mark(id, user, mark, dateTime);
    }
}
