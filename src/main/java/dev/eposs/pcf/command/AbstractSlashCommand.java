package dev.eposs.pcf.command;

import dev.eposs.pcf.permission.PermissionChecker;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience base class for slash commands that provides
 * a default implementation for sub-command handling and execution.
 * <p>
 * Subclasses can register {@link SubCommandHandler} instances in the constructor via
 * {@link SlashCommandHandler#registerSubCommand(SubCommandHandler)} or
 * {@link SlashCommandHandler#registerSubCommands(SubCommandHandler...)}.
 */
public abstract class AbstractSlashCommand implements SlashCommandHandler {
    /**
     * Mutable registry of sub-commands mapped by their name.
     */
    private final Map<String, SubCommandHandler> subCommands = new HashMap<>();

    /**
     * Provides access to the mutable sub-command registry.
     *
     * @return a map of sub-command name to action
     */
    @Override
    public Map<String, SubCommandHandler> getSubCommands() {
        return subCommands;
    }

    /**
     * Default execution that safely casts the generic interaction to a slash
     * command interaction, enforces owner-only access, defers the reply with
     * the appropriate ephemeral state, and dispatches to the chosen sub-command.
     *
     * @param genericEvent the incoming interaction event
     * @throws Exception if a sub-command execution throws
     */
    @Override
    public void execute(GenericCommandInteractionEvent genericEvent) throws Exception {
        if (!(genericEvent instanceof SlashCommandInteractionEvent event)) return;
        PermissionChecker permissionChecker = new PermissionChecker(event);
        if (!permissionChecker.isBotOwner()) return;

        event.deferReply(isEphemeral(event)).queue();
        executeSubCommand(event);
    }
}
