package dev.eposs.pcf.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * Represents the behavior and definition of a single slash sub-command.
 * Implementations provide the JDA {@link SubcommandData} used for command
 * registration and the logic to execute when the sub-command is invoked.
 */
public interface SubCommandHandler {

    /**
     * The sub-command metadata used to register this sub-command with JDA.
     *
     * @return the sub-command definition
     */
    SubcommandData getSubCommandData();

    /**
     * Executes the sub-command logic for the given event.
     *
     * @param event the slash command interaction event
     * @throws Exception if command execution fails
     */
    void execute(SlashCommandInteractionEvent event) throws Exception;
}

