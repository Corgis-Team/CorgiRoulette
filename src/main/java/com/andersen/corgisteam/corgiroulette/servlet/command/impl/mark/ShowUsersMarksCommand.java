package com.andersen.corgisteam.corgiroulette.servlet.command.impl.mark;

import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUsersMarksCommand implements Command {

    private static final String USERS_MARKS_PATH = "/WEB-INF/jsp/mark/showUsersMarks.jsp";

    private static final String MARKS_PARAMETER = "marks";

    private final MarkService markService;

    public ShowUsersMarksCommand(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(MARKS_PARAMETER, markService.getAllMarksGroupedByUser());
        request.getRequestDispatcher(USERS_MARKS_PATH).forward(request, response);
    }
}
