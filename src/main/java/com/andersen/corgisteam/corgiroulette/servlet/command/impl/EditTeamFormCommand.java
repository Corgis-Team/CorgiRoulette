package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditTeamFormCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditTeamFormCommand.class);

    private static final String EDIT_TEAM_PATH = "/WEB-INF/jsp/team/editTeam.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";
    private static final String TEAM_PARAMETER = "team";
    private static final String ID_PARAMETER = "id";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final TeamService teamService;

    public EditTeamFormCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long teamId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Team team = teamService.get(teamId);

            request.setAttribute(TEAM_PARAMETER, team);
            request.getRequestDispatcher(EDIT_TEAM_PATH).forward(request, response);
        } catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as id", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        } catch (ValidationException e) {
            log.warn("Can't update team cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
