package com.andersen.corgisteam.corgiroulette.servlet.command.impl.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.exception.EntityNotFoundException;
import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class TeamDetailsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(TeamDetailsCommand.class);
    private static final String TEAM_DETAILS_PATH = "/WEB-INF/jsp/team/teamDetails.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";
    private static final String ID_PARAMETER = "id";
    private static final String TEAM_PARAMETER = "team";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private final TeamService teamService;

    public TeamDetailsCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long teamId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Team team = teamService.get(teamId);

            request.setAttribute(TEAM_PARAMETER, team);
            request.getRequestDispatcher(TEAM_DETAILS_PATH).forward(request, response);
        }
        catch (QueryExecutionException | EntityNotFoundException e) {
            log.warn("Can't get team's details cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as id to get team's details");
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
