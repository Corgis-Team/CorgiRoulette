package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class CreateUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(CreateUserCommand.class);

    private static final String NAME_PARAMETER = "name";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String TEAM_PARAMETER = "team_id";

    private static final String USER_LIST_PATH_FORMAT = "%s%s/users";
    private static final String NEW_USER_FORM_PATH_FORMAT = "%s/users/new";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestUserDto user = new RequestUserDto();
            user.setName(request.getParameter(NAME_PARAMETER));
            user.setSurname(request.getParameter(SURNAME_PARAMETER));
            user.setTeamId(Long.parseLong(request.getParameter(TEAM_PARAMETER)));

            userService.save(user);
            response.sendRedirect(String.format(USER_LIST_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
        }
        catch (ValidationException e) {
            log.warn("Can't create user cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(String.format(NEW_USER_FORM_PATH_FORMAT, request.getServletPath()))
                .forward(request, response);
        }
    }
}
