package dev.eposs.pcf.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Contract for slash commands that support dispatching to sub-commands and
 * provide common utilities for option handling.
 */
public interface SlashCommandHandler extends CommandHandler {

    /**
     * Standard optional boolean option to control whether a command response
     * should be ephemeral. If omitted, the default behavior is ephemeral.
     */
    OptionData EPHEMERAL_OPTION = new OptionData(
            OptionType.BOOLEAN,
            "ephemeral",
            "Should the response be ephemeral? Default: true",
            false
    );

    /**
     * Returns the mutable registry of known sub-commands.
     *
     * @return map of sub-command name to its action
     */
    Map<String, SubCommandHandler> getSubCommands();

    /**
     * Returns the mutable registry of known sub-command groups.
     *
     * @return map of sub-command group name to its handler
     */
    Map<String, SubCommandGroupHandler> getSubCommandGroups();

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
    default void registerSubCommands(@NotNull SubCommandHandler... actions) {
        Arrays.stream(actions).forEach(this::registerSubCommand);
    }

    /**
     * Registers a single {@link SubCommandGroupHandler} using its declared name.
     *
     * @param group the sub-command group to add
     */
    default void registerSubCommandGroup(SubCommandGroupHandler group) {
        getSubCommandGroups().put(group.getSubCommandGroupData().getName(), group);
    }

    /**
     * Convenience to add all sub-command groups at once.
     *
     * @param groups the sub-command groups to add
     */
    default void registerSubCommandGroups(@NotNull SubCommandGroupHandler... groups) {
        Arrays.stream(groups).forEach(this::registerSubCommandGroup);
    }

    /**
     * Collects the {@link SubcommandData} from all registered sub-commands.
     *
     * @return list of sub-command definitions for JDA registration
     */
    default List<SubcommandData> subCommandData() {
        return getSubCommands().values().stream()
                .map(SubCommandHandler::getSubCommandData)
                .toList();
    }

    /**
     * Collects the {@link SubcommandGroupData} from all registered sub-command groups.
     *
     * @return list of sub-command group definitions for JDA registration
     */
    default List<SubcommandGroupData> subCommandGroupData() {
        return getSubCommandGroups().values().stream()
                .map(SubCommandGroupHandler::getSubCommandGroupData)
                .toList();
    }

    /**
     * Resolves the requested sub-command from the event and executes it if present.
     * This handles both direct sub-commands and sub-commands within groups.
     *
     * @param event the slash command interaction
     * @throws Exception if the sub-command execution throws
     */
    default void executeSubCommand(@NotNull SlashCommandInteractionEvent event) throws Exception {
        String subCommandName = event.getSubcommandName();
        if (subCommandName == null) return;

        String subCommandGroupName = event.getSubcommandGroup();

        if (subCommandGroupName != null) {
            SubCommandGroupHandler group = getSubCommandGroups().get(subCommandGroupName);
            if (group != null) {
                group.executeSubCommand(event);
                return;
            }
        }

        SubCommandHandler subCommand = getSubCommands().get(subCommandName);
        if (subCommand != null) subCommand.execute(event);
    }

    /**
     * Determines whether the response to the slash command should be ephemeral.
     * If the "ephemeral" option is not provided in the slash command interaction,
     * the response defaults to ephemeral (true).
     *
     * @param event the {@link SlashCommandInteractionEvent} containing the slash command interaction data
     * @return {@code true} if the response is ephemeral, {@code false} otherwise
     */
    default boolean isEphemeral(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping ephemeral = event.getOption("ephemeral");

        if (ephemeral == null) return true;
        return ephemeral.getAsBoolean();
    }
}
