package dev.eposs.pcf.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.Arrays;
import java.util.Map;

/**
 * Represents the behavior and definition of a single slash sub-command group.
 * Implementations provide the JDA {@link SubcommandGroupData} used for command
 * registration and the logic to execute when a sub-command within this group is invoked.
 * <p>
 * Sub-command groups contain multiple {@link SubCommandHandler} instances.
 */
public interface SubCommandGroupHandler {

    /**
     * The sub-command group metadata used to register this group with JDA.
     *
     * @return the sub-command group definition
     */
    SubcommandGroupData getSubCommandGroupData();

    /**
     * Returns the mutable registry of known sub-commands within this group.
     *
     * @return map of sub-command name to its action
     */
    Map<String, SubCommandHandler> getSubCommands();

    /**
     * Registers a single {@link SubCommandHandler} using its declared name.
     *
     * @param action the sub-command to add
     */
    default void registerSubCommand(SubCommandHandler action) {
        getSubCommands().put(action.getSubCommandData().getName(), action);
    }

    /**
     * Convenience to add all sub-commands at once.
     *
     * @param actions the sub-commands to add
     */
    default void registerSubCommands(SubCommandHandler... actions) {
        Arrays.stream(actions).forEach(this::registerSubCommand);
    }

    /**
     * Resolves the requested sub-command from the event and executes it if present.
     *
     * @param event the slash command interaction
     * @throws Exception if the sub-command execution throws
     */
    default void executeSubCommand(SlashCommandInteractionEvent event) throws Exception {
        String name = event.getSubcommandName();
        if (name == null) return;

        SubCommandHandler subCommand = getSubCommands().get(name);
        if (subCommand != null) subCommand.execute(event);
    }
}
