package com.andersen.corgisteam.corgiroulette.servlet.command.impl.mark;

import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateMarkCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UpdateMarkCommand.class);

    private static final String EDIT_MARK_PATH = "/WEB-INF/jsp/mark/editMark.jsp";
    private static final String USERS_MARKS_PATH_FORMAT = "%s%s/marks";

    private static final String ID_PARAMETER = "id";
    private static final String MARK_PARAMETER = "mark";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final MarkService markService;

    public UpdateMarkCommand(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mark mark = new Mark();

            mark.setId(Long.parseLong(request.getParameter(ID_PARAMETER)));
            mark.setMark(Double.parseDouble(request.getParameter(MARK_PARAMETER)));

            markService.update(mark);
            response.sendRedirect(String.format(USERS_MARKS_PATH_FORMAT, request.getContextPath(), request.getServletPath()));
        } catch (NumberFormatException e) {
            log.warn("Can't parse given parameters cause: ", e);

            long markId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Mark mark = markService.get(markId);

            request.setAttribute(MARK_PARAMETER, mark);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(EDIT_MARK_PATH).forward(request,response);
        }
    }
}
