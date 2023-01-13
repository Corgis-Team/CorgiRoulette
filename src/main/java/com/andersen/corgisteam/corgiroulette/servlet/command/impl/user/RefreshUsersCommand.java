package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshUsersCommand implements Command {

    private static final String USER_LIST_PATH_FORMAT = "%s%s/users";

    private final UserService userService;

    public RefreshUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.refreshUsers();
        response.sendRedirect(String.format(USER_LIST_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
    }
}
