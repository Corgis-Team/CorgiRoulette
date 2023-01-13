package com.andersen.corgisteam.corgiroulette.servlet.command.impl.mark;

import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMarkFormCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditMarkFormCommand.class);

    private static final String EDIT_MARK_PATH = "/WEB-INF/jsp/mark/editMark.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFound.jsp";

    private static final String MARK_PARAMETER = "mark";
    private static final String ID_PARAMETER = "id";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final MarkService markService;

    public EditMarkFormCommand(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long markId = Long.parseLong(request.getParameter(ID_PARAMETER));
            Mark mark = markService.get(markId);

            request.setAttribute(MARK_PARAMETER, mark);
            request.getRequestDispatcher(EDIT_MARK_PATH).forward(request, response);
        } catch (NumberFormatException e) {
            log.warn("Can't parse given parameter as long cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        } catch (ValidationException e) {
            log.warn("Can't validate entered data cause: ", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
