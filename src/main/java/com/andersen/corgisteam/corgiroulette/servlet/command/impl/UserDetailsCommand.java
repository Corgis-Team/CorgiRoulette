package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.repository.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDetailsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsCommand.class);

    private final UserService userService;
    private final TeamService teamService;

    private static final String TEAM_DETAILS_PATH = "/WEB-INF/jsp/user/userDetails.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String USER_PARAMETER = "user";
    private static final String TEAM_NAME_PARAMETER = "team_name";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    public UserDetailsCommand(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(ID_PARAMETER));
            UserDto userDto = userService.get(userId);

            request.setAttribute(USER_PARAMETER, userDto);
            request.setAttribute(TEAM_NAME_PARAMETER, teamService.get(userDto.getTeamId()).getName());
            request.getRequestDispatcher(TEAM_DETAILS_PATH).forward(request, response);
        } catch (QueryExecutionException e) {
            log.warn("Can't receive user's details cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
