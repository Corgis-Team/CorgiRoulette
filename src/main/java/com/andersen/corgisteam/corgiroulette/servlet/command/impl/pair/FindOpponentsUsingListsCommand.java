package com.andersen.corgisteam.corgiroulette.servlet.command.impl.pair;

import com.andersen.corgisteam.corgiroulette.service.FindOpponentsUsingList;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindOpponentsUsingListsCommand implements Command {
    private static final String FIND_OPPONENTS_SHOW_PATH = "index.jsp";

    private final FindOpponentsUsingList findOpponents;

    public FindOpponentsUsingListsCommand(FindOpponentsUsingList findOpponents) {
        this.findOpponents = findOpponents;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pair", findOpponents.getPair());
        request.getRequestDispatcher(FIND_OPPONENTS_SHOW_PATH).forward(request, response);
    }
}
