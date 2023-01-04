package com.andersen.corgisteam.corgiroulette.servlet.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

public class NotFoundCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(NotFoundCommand.class);

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.warn("Incorrect URI - {} ", requestUri);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, String.format("Page %s not found", requestUri));
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
    }
}
