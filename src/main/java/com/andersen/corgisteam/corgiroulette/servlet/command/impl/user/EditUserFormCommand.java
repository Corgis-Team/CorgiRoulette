package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class EditUserFormCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditUserFormCommand.class);

    private static final String EDIT_USER_PATH = "/WEB-INF/jsp/user/editUser.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String USER_PARAMETER = "user";
    private static final String TEAM_ATTRIBUTE = "teams";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final UserService userService;
    private final TeamService teamService;

    public EditUserFormCommand(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(ID_PARAMETER));
            User user = userService.get(userId);

            request.setAttribute(USER_PARAMETER, user);
            request.setAttribute(TEAM_ATTRIBUTE, teamService.getAll());
            request.getRequestDispatcher(EDIT_USER_PATH).forward(request, response);
        }
        catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as id", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (ValidationException e) {
            log.warn("Can't update team cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
