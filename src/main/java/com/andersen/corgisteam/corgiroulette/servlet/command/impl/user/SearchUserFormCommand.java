package com.andersen.corgisteam.corgiroulette.servlet.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class SearchUserFormCommand implements Command {

    private static final String SEARCH_USER_PATH = "/WEB-INF/jsp/user/searchUser.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SEARCH_USER_PATH).forward(request, response);
    }
}