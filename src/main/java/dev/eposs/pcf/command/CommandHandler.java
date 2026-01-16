package dev.eposs.pcf.command;

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Set;

/**
 * Base contract for all bot commands.
 * <p>
 * Implementations provide the JDA {@link CommandData} used during command
 * registration and the execution logic for incoming interactions.
 */
public interface CommandHandler {

    /**
     * Provides the JDA command definition for this command.
     *
     * @return the command metadata used to register the command
     */
    CommandData getCommandData();

    /**
     * Retrieves the set of target guild IDs where the command will be registered as a guild-specific command.
     * If the set is empty, the command will be registered across all available guilds.
     *
     * @return a set of guild IDs as strings indicating the target guilds for command registration, or an empty set if the command should be registered globally.
     */
    Set<String> getTargetGuildIDs();

    /**
     * Executes the command for a generic interaction event.
     * Implementations may down-cast the event to a concrete type as needed.
     *
     * @param genericEvent the incoming interaction
     * @throws Exception if execution fails
     */
    void execute(GenericCommandInteractionEvent genericEvent) throws Exception;
}
