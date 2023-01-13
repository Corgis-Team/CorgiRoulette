package com.andersen.corgisteam.corgiroulette.servlet.command.impl.pair;

import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.service.PairService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeOpponentCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ChangeOpponentCommand.class);

    private static final String USER_TO_CHANGE_ID_PARAMETER = "userToChangeId";
    private static final String USER_TO_SAVE_PARAMETER = "userToSaveId";

    private static final String FIND_OPPONENTS_SHOW_PATH = "/index.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final UserService userService;
    private final PairService pairService;

    public ChangeOpponentCommand(UserService userService, PairService pairService) {
        this.userService = userService;
        this.pairService = pairService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userToChangeId = Long.parseLong(request.getParameter(USER_TO_CHANGE_ID_PARAMETER));
            User userToChange = userService.get(userToChangeId);

            long userTwoId = Long.parseLong(request.getParameter(USER_TO_SAVE_PARAMETER));
            User userToSave = userService.get(userTwoId);

            Pair pair = new Pair(userToSave, userToChange);
            pair = pairService.changeOpponent(pair);
            request.setAttribute("pair", pair);
            request.getRequestDispatcher(FIND_OPPONENTS_SHOW_PATH).forward(request, response);
        } catch (NumberFormatException | EntityNotFoundException e) {
            log.warn("Can't receive user's details cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }

    }
}
