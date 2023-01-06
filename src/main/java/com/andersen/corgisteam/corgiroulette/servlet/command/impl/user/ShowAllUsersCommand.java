package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class ShowAllUsersCommand implements Command {

    private static final String USERS_SHOW_PATH = "/WEB-INF/jsp/user/showUsers.jsp";

    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userService.getAll());
        request.getRequestDispatcher(USERS_SHOW_PATH).forward(request, response);
    }
}
