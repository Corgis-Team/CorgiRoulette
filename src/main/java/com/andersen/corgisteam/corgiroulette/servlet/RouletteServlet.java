package com.andersen.corgisteam.corgiroulette.servlet;

import com.andersen.corgisteam.corgiroulette.mapper.UserMapper;
import com.andersen.corgisteam.corgiroulette.mapper.UserMapperImpl;
import com.andersen.corgisteam.corgiroulette.repository.*;
import com.andersen.corgisteam.corgiroulette.repository.impl.MarkRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.repository.impl.PairRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.repository.impl.TeamRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.repository.impl.UserRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.service.*;
import com.andersen.corgisteam.corgiroulette.service.impl.*;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import com.andersen.corgisteam.corgiroulette.servlet.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RouletteServlet", value = "/roulette/*")
public class RouletteServlet extends HttpServlet {

    private CommandProvider commandProvider;

    @Override
    public void init() throws ServletException {
        super.init();

        TeamRepository teamRepository = new TeamRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl(teamRepository);
        PairRepository pairRepository = new PairRepositoryImpl(userRepository);
        MarkRepository markRepository = new MarkRepositoryImpl(userRepository);

        TeamService teamService = new TeamServiceImpl(teamRepository, userRepository);
        UserMapper userMapper = new UserMapperImpl(teamRepository);
        PairGenerator pairGenerator = new PairGenerator(userRepository, pairRepository);
        UserService userService = new UserServiceImpl(userRepository, userMapper, pairGenerator);
        MarkService markService = new MarkServiceImpl(markRepository);
        FindOpponentsUsingList findOpponentsUsingList = new FindOpponentsUsingList(userService, pairRepository);

        this.commandProvider = new CommandProvider(teamService, userService, markService, findOpponentsUsingList);
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
