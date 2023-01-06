package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class UserDetailsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsCommand.class);
    private static final String TEAM_DETAILS_PATH = "/WEB-INF/jsp/user/userDetails.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";
    private static final String ID_PARAMETER = "id";
    private static final String USER_PARAMETER = "user";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private final UserService userService;

    public UserDetailsCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(ID_PARAMETER));
            User user = userService.get(userId);

            request.setAttribute(USER_PARAMETER, user);
            request.getRequestDispatcher(TEAM_DETAILS_PATH).forward(request, response);
        }
        catch (QueryExecutionException | EntityNotFoundException e) {
            log.warn("Can't receive user's details cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
