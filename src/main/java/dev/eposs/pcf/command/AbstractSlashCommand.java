package dev.eposs.pcf.command;

import dev.eposs.pcf.PCF;
import dev.eposs.pcf.permission.PermissionChecker;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience base class for slash commands that provides
 * a default implementation for sub-command handling and execution.
 * <p>
 * Subclasses can register {@link SubCommandHandler} instances in the constructor via
 * {@link SlashCommandHandler#registerSubCommand(SubCommandHandler)} or
 * {@link SlashCommandHandler#registerSubCommands(SubCommandHandler...)}.
 * <p>
 * Subclasses can also register {@link SubCommandGroupHandler} instances via
 * {@link SlashCommandHandler#registerSubCommandGroup(SubCommandGroupHandler)} or
 * {@link SlashCommandHandler#registerSubCommandGroups(SubCommandGroupHandler...)}.
 */
public abstract class AbstractSlashCommand implements SlashCommandHandler {
    /**
     * Mutable registry of sub-commands mapped by their name.
     */
    private final Map<String, SubCommandHandler> subCommands = new HashMap<>();
    /**
     * Mutable registry of sub-command groups mapped by their name.
     */
    private final Map<String, SubCommandGroupHandler> subCommandGroups = new HashMap<>();

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
     * Provides access to the mutable sub-command group registry.
     *
     * @return a map of sub-command group name to handler
     */
    @Override
    public Map<String, SubCommandGroupHandler> getSubCommandGroups() {
        return subCommandGroups;
    }

    /**
     * Default execution that safely casts the generic interaction to a slash
     * command interaction, enforces owner-only access, defers the reply with
     * the appropriate ephemeral state, and dispatches to the chosen sub-command.
     *
     * @param pcf          the PCF instance
     * @param genericEvent the incoming interaction event
     * @throws Exception if a sub-command execution throws
     */
    @Override
    public void execute(PCF pcf, @NotNull GenericCommandInteractionEvent genericEvent) throws Exception {
        if (!(genericEvent instanceof SlashCommandInteractionEvent event)) return;
        PermissionChecker permissionChecker = new PermissionChecker(pcf, event);
        if (!permissionChecker.isBotOwner()) return;

        event.deferReply(isEphemeral(event)).queue();
        executeSubCommand(pcf, event);
    }
}
