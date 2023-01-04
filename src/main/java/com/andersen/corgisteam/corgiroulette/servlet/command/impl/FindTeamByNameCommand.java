package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindTeamByNameCommand implements Command {

    private TeamService teamService;

    public static final String SEARCH_TEAM_PATH = "/WEB-INF/jsp/team/searchTeam.jsp";
    private static final String TEAM_SEARCH_RESULTS = "/WEB-INF/jsp/team/searchTeamResults.jsp";

    private static final String NAME_PARAMETER = "name";
    private static final String TEAMS_PARAMETER = "teams";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    public FindTeamByNameCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter(NAME_PARAMETER);
        if (name == null) {
            request.getRequestDispatcher(SEARCH_TEAM_PATH).forward(request, response);
        } else {
            try {
                List<Team> teams = teamService.getAllByName(name);
                request.setAttribute(TEAMS_PARAMETER, teams);
                request.getRequestDispatcher(TEAM_SEARCH_RESULTS).forward(request, response);
            } catch (QueryExecutionException e) {
                request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
                request.getRequestDispatcher(SEARCH_TEAM_PATH).forward(request, response);
            }
        }
    }
}
