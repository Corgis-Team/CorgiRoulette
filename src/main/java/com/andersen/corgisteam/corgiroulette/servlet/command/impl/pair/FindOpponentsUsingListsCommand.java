package com.andersen.corgisteam.corgiroulette.servlet.command.impl.pair;

import com.andersen.corgisteam.corgiroulette.service.PairService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindOpponentsUsingListsCommand implements Command {
    private static final String FIND_OPPONENTS_SHOW_PATH = "index.jsp";

    private final PairService pairService;

    public FindOpponentsUsingListsCommand(PairService pairService) {
        this.pairService = pairService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pair", pairService.getPair());
        request.getRequestDispatcher(FIND_OPPONENTS_SHOW_PATH).forward(request, response);
    }
}
