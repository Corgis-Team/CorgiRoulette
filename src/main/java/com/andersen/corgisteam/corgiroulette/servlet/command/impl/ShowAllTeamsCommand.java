package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAllTeamsCommand implements Command {
    private static final String TEAMS_SHOW_PATH = "/WEB-INF/jsp/showTeams.jsp";

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