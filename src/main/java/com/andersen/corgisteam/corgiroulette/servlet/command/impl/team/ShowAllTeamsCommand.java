package com.andersen.corgisteam.corgiroulette.servlet.command.impl.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class ShowAllTeamsCommand implements Command {

    private static final String TEAMS_SHOW_PATH = "/WEB-INF/jsp/team/showTeams.jsp";

    private final TeamService teamService;

    public ShowAllTeamsCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("teams", teamService.getAll());
        request.getRequestDispatcher(TEAMS_SHOW_PATH).forward(request, response);
    }
}
