package com.andersen.corgisteam.corgiroulette.mapper;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    private final TeamRepository teamRepository;

    public UserMapperImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public UserDto userEntityToModel(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setTeamId(user.getTeam().getId());
        return userDto;
    }

    @Override
    public User userModelToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setTeam(teamRepository.findById(userDto.getTeamId()));
        user.setChosen(false);
        user.setLastDuel(LocalDateTime.now());
        return user;
    }

    @Override
    public List<UserDto> userEntitiesToModels(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            userDtoList.add(userEntityToModel(user));
        }

        return userDtoList;
    }
}
