package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.mapper.UserMapper;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldContainsNumberException;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldLengthExceedException;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final int FIELD_MAX_LENGTH = 30;
    private static final String NUMBERS_REGEXP = ".*\\d.*";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PairGenerator pairGenerator;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PairGenerator pairGenerator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pairGenerator = pairGenerator;
  //      pairGenerator.generatePairs();
    }

    @Override
    public void save(RequestUserDto requestUserDto) {
        User user = userMapper.userDtoToEntity(requestUserDto);
        validate(user);
        userRepository.save(user);
      //  pairGenerator.generatePairs();
        log.info("Successfully created user with id {}", user.getId());
    }

    @Override
    public void update(RequestUserDto requestUserDto) {
        User user = userMapper.userDtoToEntity(requestUserDto);
        validate(user);
        userRepository.update(user);
  //      pairGenerator.generatePairs();
        log.info("Successfully updated user with id {}", user.getId());
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Successfully showed all users");
        return users;
    }

    @Override
    public User get(long id) {
        User user = userRepository.findById(id);
        log.info("Successfully found user with id {}", id);
        return user;
    }

    @Override
    public List<User> getAllByFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new RequiredFieldIsEmptyException("Name field is required");
        }

        List<User> users = userRepository.findAllByFullName(fullName);
        log.info("Successfully found users with name {}", fullName);
        return users;
    }

    @Override
    public List<User> getAllByTeamId(long teamId) {
        List<User> users = userRepository.findAllByTeamId(teamId);
        log.info("Successfully found users with team id {}", teamId);
        return users;
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id);
        userRepository.delete(id);
//        pairGenerator.generatePairs();
        log.info("Team with id {} was successfully deleted", user.getId());
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new RequiredFieldIsEmptyException(String.format("Required field is empty. Name: %s", user.getName()));
        }

        if (user.getName().length() > FIELD_MAX_LENGTH) {
            throw new FieldLengthExceedException(String.format("Name length is greater than %d", FIELD_MAX_LENGTH));
        }

        if (user.getSurname() == null || user.getSurname().isBlank()) {
            throw new RequiredFieldIsEmptyException(String.format("Required field is empty. Surname: %s", user.getSurname()));
        }

        if (user.getSurname().length() > FIELD_MAX_LENGTH) {
            throw new FieldLengthExceedException(String.format("Surname length is greater than %d", FIELD_MAX_LENGTH));
        }

        if (user.getSurname().matches(NUMBERS_REGEXP)) {
            throw new FieldContainsNumberException(String.format("Required field is contains numbers. Surname: %s",
                    user.getSurname()));
        }

        if (user.getName().matches(NUMBERS_REGEXP)) {
            throw new FieldContainsNumberException(String.format("Required field is contains numbers. Name: %s",
                    user.getName()));
        }
    }

    @Override
    public List<User> getUsersWhereIsChosenFalse() {
        List<User> users = userRepository.getUsersWhereIsChosenFalse();
        log.info("Successfully found all users that haven't picked before");
        return users;
    }

    @Override
    public List<User> getUsersWhichWereOpponentsBefore(long userId) {
        List<User> users = userRepository.getUsersWhichWereOpponentsBefore(userId);
        log.info("Successfully found all users that haven't picked before as pair with user with id {}", userId);
        return users;
    }

    @Override
    public void updateStatusChosenUser(long userId) {
        userRepository.updateStatusChosenUser(userId);
        log.info("Successfully update status for user with id {}", userId);
    }

    @Override
    public void changeStatusForAllUsers() {
        userRepository.changeStatusForAllUsers();
        log.info("Successfully update status for all users");
    }
}