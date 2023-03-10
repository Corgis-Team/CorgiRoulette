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

public class UpdateTeamCommand implements Command {

    public static final String TEAM_EDIT_PATH = "/WEB-INF/jsp/team/editTeam.jsp";
    private static final Logger log = LoggerFactory.getLogger(UpdateTeamCommand.class);
    private static final String TEAM_DETAILS_PATH = "%s%s/teams/details?id=%d";

    private static final String TEAM_PARAMETER = "team";
    private static final String NAME_PARAMETER = "name";
    private static final String ID_PARAMETER = "id";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final TeamService teamService;

    public UpdateTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Team team = new Team();
            team.setId(Long.parseLong(request.getParameter(ID_PARAMETER)));
            team.setName(request.getParameter(NAME_PARAMETER));

            teamService.update(team);
            response.sendRedirect(String.format(TEAM_DETAILS_PATH,
                request.getContextPath(), request.getServletPath(), team.getId()));
        }
        catch (ValidationException e) {
            log.warn("Can't update team cause: ", e);

            long teamId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Team team = teamService.get(teamId);

            request.setAttribute(TEAM_PARAMETER, team);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(TEAM_EDIT_PATH).forward(request, response);
        }
    }
}
