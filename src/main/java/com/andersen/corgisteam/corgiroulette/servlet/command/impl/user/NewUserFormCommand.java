package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class NewUserFormCommand implements Command {

    private static final String ADD_USER_PATH = "/WEB-INF/jsp/user/addUser.jsp";

    private static final String TEAM_ATTRIBUTE = "teams";

    private final TeamService teamService;

    public NewUserFormCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(TEAM_ATTRIBUTE, teamService.getAll());
        request.getRequestDispatcher(ADD_USER_PATH).forward(request, response);
    }
}
