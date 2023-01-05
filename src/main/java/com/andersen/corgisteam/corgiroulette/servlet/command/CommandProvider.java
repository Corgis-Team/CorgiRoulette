package com.andersen.corgisteam.corgiroulette.servlet.command;

import java.util.HashMap;
import java.util.Map;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.*;

public class CommandProvider {

    private static final String NEW_USER_FORM_COMMAND = "/users/new";
    private static final String NEW_TEAM_FORM_COMMAND = "/teams/new";
    private static final String CREATE_TEAM_COMMAND = "/teams/create";
    private static final String SEARCH_TEAM_COMMAND = "/teams/search";
    private static final String SEARCH_TEAM_RESULTS_COMMAND = "/teams/search/results";
    private static final String TEAM_DETAILS_COMMAND = "/teams/details";

    private final Command notFoundCommand;

    private final Map<String, Command> commandMap;

    public CommandProvider(TeamService teamService, UserService userService) {
        commandMap = new HashMap<>();
        commandMap.put(NEW_TEAM_FORM_COMMAND, new NewTeamFormCommand());
        commandMap.put(CREATE_TEAM_COMMAND, new CreateTeamCommand(teamService));
        commandMap.put(SEARCH_TEAM_COMMAND, new SearchTeamFormCommand());
        commandMap.put(SEARCH_TEAM_RESULTS_COMMAND, new TeamSearchResultsCommand(teamService));
        commandMap.put(TEAM_DETAILS_COMMAND, new TeamDetailsCommand(teamService));

        commandMap.put(NEW_USER_FORM_COMMAND, new CreateUserCommand(userService));

        notFoundCommand = new NotFoundCommand();
    }

    public Command getCommand(String commandName) {
        if (!commandMap.containsKey(commandName)) {
            return notFoundCommand;
        }
        return commandMap.get(commandName);
    }
}