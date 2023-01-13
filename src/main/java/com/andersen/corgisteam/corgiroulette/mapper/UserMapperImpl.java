package com.andersen.corgisteam.corgiroulette.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;

public class UserMapperImpl implements UserMapper {

    private final TeamRepository teamRepository;

    public UserMapperImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public RequestUserDto userEntityToDto(User user) {
        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setId(user.getId());
        requestUserDto.setName(user.getName());
        requestUserDto.setSurname(user.getSurname());
        requestUserDto.setTeamId(user.getTeam().getId());
        return requestUserDto;
    }

    @Override
    public User userDtoToEntity(RequestUserDto requestUserDto) {
        User user = new User();
        user.setId(requestUserDto.getId());
        user.setName(requestUserDto.getName());
        user.setSurname(requestUserDto.getSurname());
        user.setChosen(requestUserDto.isChosen());

        if (requestUserDto.getTeamId() > 0) {
            user.setTeam(teamRepository.findById(requestUserDto.getTeamId()));
        }

        user.setLastDuel(LocalDateTime.now());
        return user;
    }

    @Override
    public List<RequestUserDto> userEntitiesToDtos(List<User> users) {
        List<RequestUserDto> requestUserDtoList = new ArrayList<>();

        for (User user : users) {
            requestUserDtoList.add(userEntityToDto(user));
        }

        return requestUserDtoList;
    }
}
