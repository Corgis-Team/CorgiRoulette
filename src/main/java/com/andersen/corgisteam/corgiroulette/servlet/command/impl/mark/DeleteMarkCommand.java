package com.andersen.corgisteam.corgiroulette.servlet.command.impl.mark;

import com.andersen.corgisteam.corgiroulette.repository.exception.QueryExecutionException;
import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteMarkCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DeleteMarkCommand.class);
    private static final String USERS_MARKS_PATH_FORMAT = "%s%s/marks";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private static final String ID_PARAMETER = "id";

    private final MarkService markService;

    public DeleteMarkCommand(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(ID_PARAMETER));
            markService.delete(userId);
            response.sendRedirect(String.format(USERS_MARKS_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
        } catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as long cause ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        } catch (QueryExecutionException e) {
            log.warn("Can't delete mark cause ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
