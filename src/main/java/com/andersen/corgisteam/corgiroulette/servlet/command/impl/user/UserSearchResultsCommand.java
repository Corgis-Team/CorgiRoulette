package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class UserSearchResultsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UserSearchResultsCommand.class);
    private static final String USER_SEARCH_RESULTS = "/WEB-INF/jsp/user/userSearchResults.jsp";
    private static final String SEARCH_USER_PATH = "/WEB-INF/jsp/user/searchUser.jsp";
    private static final String FULL_NAME_PARAMETER = "full_name";
    private static final String USERS_PARAMETER = "users";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private final UserService userService;

    public UserSearchResultsCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String fullName = request.getParameter(FULL_NAME_PARAMETER);
            List<User> userList = userService.getAllByFullName(fullName);

            request.setAttribute(USERS_PARAMETER, userList);
            request.getRequestDispatcher(USER_SEARCH_RESULTS).forward(request, response);
        }
        catch (QueryExecutionException | ValidationException e) {
            log.warn("Can't receive users search results cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(SEARCH_USER_PATH).forward(request, response);
        }
    }
}
