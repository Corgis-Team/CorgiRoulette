package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeamDetailsCommand implements Command {

    private TeamService teamService;

    private static final String TEAM_DETAILS_PATH = "/WEB-INF/jsp/team/teamDetails.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String TEAM_PARAMETER = "team";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    public TeamDetailsCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            long teamId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Team team = teamService.get(teamId);

            request.setAttribute(TEAM_PARAMETER, team);
            request.getRequestDispatcher(TEAM_DETAILS_PATH).forward(request, response);
        } catch (QueryExecutionException e){
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(SearchTeamFormCommand.SEARCH_TEAM_PATH).forward(request, response);
        }
    }
}
