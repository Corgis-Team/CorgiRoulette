package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class NewTeamFormCommand implements Command {

    private static final String ADD_TEAM_PATH = "/WEB-INF/jsp/team/addTeam.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(ADD_TEAM_PATH).forward(request, response);
    }
}
