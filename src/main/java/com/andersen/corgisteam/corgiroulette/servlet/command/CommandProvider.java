package com.andersen.corgisteam.corgiroulette.servlet.command;

import java.util.HashMap;
import java.util.Map;

import com.andersen.corgisteam.corgiroulette.service.TeamService;

import com.andersen.corgisteam.corgiroulette.servlet.command.impl.CreateTeamCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.NewTeamFormCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.NotFoundCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.ShowAllTeamsCommand;

public class CommandProvider {
    private static final String NEW_USER_FORM_COMMAND = "/users/new";
    private static final String SEARCH_USER_COMMAND = "/users/search";
    private static final String SEARCH_USER_RESULTS_COMMAND = "/users/search/results";
    private static final String USER_DETAILS_COMMAND = "/users/details";
    private static final String NEW_TEAM_FORM_COMMAND = "/teams/new";
    private static final String CREATE_TEAM_COMMAND = "/teams/create";
    private static final String DELETE_TEAM_COMMAND = "/teams/delete";
    private static final String SEARCH_TEAM_COMMAND = "/teams/search";
    private static final String SEARCH_TEAM_RESULTS_COMMAND = "/teams/search/results";
    private static final String TEAM_DETAILS_COMMAND = "/teams/details";
    private static final String EDIT_TEAM_FORM_COMMAND = "/teams/edit";
    private static final String UPDATE_TEAM_COMMAND = "/teams/update";

    private static final String SHOW_ALL_TEAMS_COMMAND = "/teams";
    private final Command notFoundCommand;

    private final Map<String, Command> commandMap;

    public CommandProvider(TeamService teamService, UserService userService) {
        commandMap = new HashMap<>();
        commandMap.put(NEW_TEAM_FORM_COMMAND, new NewTeamFormCommand());
        commandMap.put(CREATE_TEAM_COMMAND, new CreateTeamCommand(teamService));
        commandMap.put(DELETE_TEAM_COMMAND, new DeleteTeamCommand(teamService));
        commandMap.put(SHOW_ALL_TEAMS_COMMAND,  new ShowAllTeamsCommand(teamService));
        commandMap.put(SEARCH_TEAM_COMMAND, new SearchTeamFormCommand());
        commandMap.put(SEARCH_TEAM_RESULTS_COMMAND, new TeamSearchResultsCommand(teamService));
        commandMap.put(TEAM_DETAILS_COMMAND, new TeamDetailsCommand(teamService, userService));
        commandMap.put(EDIT_TEAM_FORM_COMMAND, new EditTeamFormCommand(teamService));
        commandMap.put(UPDATE_TEAM_COMMAND, new UpdateTeamCommand(teamService));

        commandMap.put(NEW_USER_FORM_COMMAND, new CreateUserCommand(userService));
        commandMap.put(SEARCH_USER_COMMAND, new SearchUserFormCommand());
        commandMap.put(SEARCH_USER_RESULTS_COMMAND, new UserSearchResultsCommand(userService));
        commandMap.put(USER_DETAILS_COMMAND, new UserDetailsCommand(userService, teamService));
        notFoundCommand = new NotFoundCommand();
    }

    public Command getCommand(String commandName) {
        if (!commandMap.containsKey(commandName)) {
            return notFoundCommand;
        }
        return commandMap.get(commandName);
    }
}