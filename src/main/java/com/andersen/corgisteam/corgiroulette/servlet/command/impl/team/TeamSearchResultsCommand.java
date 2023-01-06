package com.andersen.corgisteam.corgiroulette.servlet.command.impl.team;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class TeamSearchResultsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(TeamSearchResultsCommand.class);
    private static final String TEAM_SEARCH_RESULTS = "/WEB-INF/jsp/team/teamSearchResults.jsp";
    private static final String SEARCH_TEAM_PATH = "/WEB-INF/jsp/team/searchTeam.jsp";
    private static final String NAME_PARAMETER = "name";
    private static final String TEAMS_PARAMETER = "teams";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private final TeamService teamService;

    public TeamSearchResultsCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter(NAME_PARAMETER);
            List<Team> teams = teamService.getAllByName(name);
            request.setAttribute(TEAMS_PARAMETER, teams);
            request.getRequestDispatcher(TEAM_SEARCH_RESULTS).forward(request, response);
        }
        catch (QueryExecutionException | ValidationException e) {
            log.warn("Can't get teams matching criteria cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(SEARCH_TEAM_PATH).forward(request, response);
        }
    }
}
