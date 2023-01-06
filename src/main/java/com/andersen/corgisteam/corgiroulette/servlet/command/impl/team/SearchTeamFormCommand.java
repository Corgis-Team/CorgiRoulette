package com.andersen.corgisteam.corgiroulette.servlet.command.impl.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class SearchTeamFormCommand implements Command {

    private static final String SEARCH_TEAM_PATH = "/WEB-INF/jsp/team/searchTeam.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SEARCH_TEAM_PATH).forward(request, response);
    }
}
