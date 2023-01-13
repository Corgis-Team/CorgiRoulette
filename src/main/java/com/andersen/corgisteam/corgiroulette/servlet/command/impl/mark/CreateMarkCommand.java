package com.andersen.corgisteam.corgiroulette.servlet.command.impl.mark;

import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.service.MarkService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.service.exception.ValidationException;
import com.andersen.corgisteam.corgiroulette.servlet.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateMarkCommand implements Command {

    private static final String USER_ONE_ID_PARAMETER = "userOneId";
    private static final String USER_TWO_ID_PARAMETER = "userTwoId";
    private static final String USER_ONE_MARK_PARAMETER = "markOne";
    private static final String USER_TWO_MARK_PARAMETER = "markTwo";

    private final MarkService markService;

    private final UserService userService;

    public CreateMarkCommand(MarkService markService, UserService userService) {
        this.markService = markService;
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userOneId = Long.parseLong(request.getParameter(USER_ONE_ID_PARAMETER));
            long userTwoId = Long.parseLong(request.getParameter(USER_TWO_ID_PARAMETER));
            double userOneMark = Double.parseDouble(request.getParameter(USER_ONE_MARK_PARAMETER));
            double userTwoMark = Double.parseDouble(request.getParameter(USER_TWO_MARK_PARAMETER));

            createMarkEntity(userOneId, userOneMark);
            createMarkEntity(userTwoId, userTwoMark);
            response.sendRedirect(String.format("%s%s", request.getContextPath(), request.getServletPath()));
        } catch (ValidationException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void createMarkEntity(long userId, double userMark) {
        if (userMark > 0.0) {
            Mark mark = new Mark();

            User user = userService.get(userId);

            mark.setMark(userMark);
            mark.setUser(user);
            mark.setDateTime(LocalDateTime.now());

            markService.create(mark);
        }
    }
}
