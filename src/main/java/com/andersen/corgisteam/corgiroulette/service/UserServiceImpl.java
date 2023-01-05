package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.mapper.UserMapper;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldContainsNumberException;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldLengthExceedException;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final int FIELD_MAX_LENGTH = 100;
    private static final String NUMBERS = ".*\\d.*";

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(UserDto userDto) {
        User user = userMapper.userDtoToEntity(userDto);

        validate(user);

        userRepository.save(user);
        log.info("Successfully created user with id {}", user.getId());
    }

    @Override
    public void update(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        validate(user);
        userRepository.update(user);
        log.info("Successfully updated user with id {}", user.getId());
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Successfully showed all users");
        return users;
    }
    
    public UserDto get(long id) {
        User user = userRepository.findById(id);
        UserDto userDto = userMapper.userEntityToDto(user);
        log.info("Successfully found user with id {}", id);
        return userDto;
    }

    @Override
    public List<UserDto> getAllByFullName(String fullName) {
        if (fullName == null || fullName.isBlank())
            throw new RequiredFieldIsEmptyException("Name field is required");

        List<User> users = userRepository.findAllByFullName(fullName);
        List<UserDto> userDtoList = userMapper.userEntitiesToDtos(users);
        log.info("Successfully found users with name {}", fullName);
        return userDtoList;
    }

    @Override
    public List<UserDto> getAllByTeamId(long teamId) {
        List<User> users = userRepository.findAllByTeamId(teamId);
        List<UserDto> userDtoList = userMapper.userEntitiesToDtos(users);
        log.info("Successfully found users with team id {}", teamId);
        return userDtoList;
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

        if (user.getSurname().matches(NUMBERS)) {
            throw new FieldContainsNumberException(String.format("Required field is contains numbers. Surname: %s",
                    user.getSurname()));
        }

        if (user.getName().matches(NUMBERS)) {
            throw new FieldContainsNumberException(String.format("Required field is contains numbers. Name: %s",
                    user.getName()));
        }
    }
}