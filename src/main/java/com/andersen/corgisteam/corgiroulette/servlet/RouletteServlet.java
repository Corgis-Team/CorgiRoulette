package com.andersen.corgisteam.corgiroulette.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.TeamServiceImpl;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import com.andersen.corgisteam.corgiroulette.servlet.command.CommandProvider;

@WebServlet(name = "RouletteServlet", value = "/roulette/*")
public class RouletteServlet extends HttpServlet {

    private final CommandProvider commandProvider;

    public RouletteServlet() {
        TeamRepository teamRepository = new TeamRepositoryImpl();
        TeamService teamService = new TeamServiceImpl(teamRepository);
        this.commandProvider = new CommandProvider(teamService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getPathInfo();
        Command command = commandProvider.getCommand(commandName);
        command.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}