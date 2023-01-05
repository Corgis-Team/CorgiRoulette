package com.andersen.corgisteam.corgiroulette.servlet.command;

import java.util.HashMap;
import java.util.Map;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.CreateTeamCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.NewTeamFormCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.NotFoundCommand;
import com.andersen.corgisteam.corgiroulette.servlet.command.impl.ShowAllTeamsCommand;

public class CommandProvider {
    private static final String NEW_TEAM_FORM_COMMAND = "/teams/new";
    private static final String CREATE_TEAM_COMMAND = "/teams/create";

    private static final String SHOW_ALL_TEAMS_COMMAND = "/teams";
    private final Command notFoundCommand;

    private final Map<String, Command> commandMap;

    public CommandProvider(TeamService teamService) {
        commandMap = new HashMap<>();
        commandMap.put(NEW_TEAM_FORM_COMMAND, new NewTeamFormCommand());
        commandMap.put(CREATE_TEAM_COMMAND, new CreateTeamCommand(teamService));
        commandMap.put(SHOW_ALL_TEAMS_COMMAND,  new ShowAllTeamsCommand(teamService));

        notFoundCommand = new NotFoundCommand();
    }

    public Command getCommand(String commandName) {
        if (!commandMap.containsKey(commandName)) {
            return notFoundCommand;
        }

        return commandMap.get(commandName);
    }
}
