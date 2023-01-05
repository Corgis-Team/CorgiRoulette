package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.repository.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTeamCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(CreateTeamCommand.class);

    private TeamService teamService;

    private static final String TEAM_LIST_PATH_FORMAT = "%s%s/teams";
    private static final String TEAMS_LIST_PATH = "/teams";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private static final String ID_PARAMETER = "id";

    public DeleteTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter(ID_PARAMETER));
            teamService.delete(id);
            response.sendRedirect(String.format(TEAM_LIST_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
        } catch (QueryExecutionException e) {
            log.warn("Can't delete team cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        } catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as id to delete team");
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
