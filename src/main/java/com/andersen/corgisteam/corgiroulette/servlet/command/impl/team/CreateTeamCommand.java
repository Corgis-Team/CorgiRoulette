package com.andersen.corgisteam.corgiroulette.servlet.command.impl.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class CreateTeamCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(CreateTeamCommand.class);

    private static final String NAME_PARAMETER = "name";

    private static final String TEAM_LIST_PATH_FORMAT = "%s%s/teams";
    private static final String NEW_TEAM_FORM_PATH_FORMAT = "%s/teams/new";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final TeamService teamService;

    public CreateTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Team team = new Team();
            team.setName(request.getParameter(NAME_PARAMETER));

            teamService.create(team);
            response.sendRedirect(String.format(TEAM_LIST_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
        }
        catch (ValidationException e) {
            log.warn("Can't create team cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(String.format(NEW_TEAM_FORM_PATH_FORMAT, request.getServletPath()))
                .forward(request, response);
        }
    }
}
