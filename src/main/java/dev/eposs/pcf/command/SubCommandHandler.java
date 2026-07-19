package dev.eposs.pcf.command;

import dev.eposs.pcf.PCF;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

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
     * @param pcf   the {@link PCF} instance
     * @param event the slash command interaction event
     * @throws Exception if command execution fails
     */
    void execute(PCF pcf, @NotNull SlashCommandInteractionEvent event) throws Exception;
}

