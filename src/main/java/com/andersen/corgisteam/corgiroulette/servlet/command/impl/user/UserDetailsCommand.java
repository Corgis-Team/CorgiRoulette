package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserDetailsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsCommand.class);
    private static final String TEAM_DETAILS_PATH = "/WEB-INF/jsp/user/userDetails.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";
    private static final String ID_PARAMETER = "id";
    private static final String USER_PARAMETER = "user";
    private static final String MARKS_PARAMETER = "marks";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private final UserService userService;
    private final MarkService markService;

    public UserDetailsCommand(UserService userService, MarkService markService) {
        this.userService = userService;
        this.markService = markService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(ID_PARAMETER));
            User user = userService.get(userId);
            List<Mark> marks = markService.getAllByUserId(userId);

            request.setAttribute(MARKS_PARAMETER, marks);
            request.setAttribute(USER_PARAMETER, user);
            request.getRequestDispatcher(TEAM_DETAILS_PATH).forward(request, response);
        } catch (QueryExecutionException | EntityNotFoundException e) {
            log.warn("Can't receive user's details cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
