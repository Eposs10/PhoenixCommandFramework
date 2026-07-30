package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.internal.CommandInteractionHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Central registry for all bot commands.
 * <p>
 * Commands are separated into two scopes:
 * <ul>
 *   <li>GLOBAL – available in all guilds where the bot is present.</li>
 *   <li>GUILD – registered per guild.</li>
 * </ul>
 * This class also provides helpers to publish the registered commands to Discord via JDA.
 */
public class CommandInteractionHandlerRegistry {

    private final GenericCommandInteractionHandlerRegistry globalCommandInteractionHandlerRegistry = new GenericCommandInteractionHandlerRegistry();
    private final GenericCommandInteractionHandlerRegistry guildCommandInteractionHandlerRegistry = new GenericCommandInteractionHandlerRegistry();

    /**
     * Registers a single command for the given scope.
     *
     * @param type    the registration scope (GLOBAL or GUILD)
     * @param command the command instance to register
     */
    public void register(Type type, CommandInteractionHandler<?> command) {
        switch (type) {
            case GLOBAL -> globalCommandInteractionHandlerRegistry.register(command);
            case GUILD -> guildCommandInteractionHandlerRegistry.register(command);
        }
    }

    /**
     * Registers multiple commands for the given scope.
     *
     * @param type     the registration scope (GLOBAL or GUILD)
     * @param commands the command instances to register
     */
    public void registerAll(Type type, List<CommandInteractionHandler<?>> commands) {
        commands.forEach(command -> register(type, command));
    }

    /**
     * Looks up a command by name from the registered GLOBAL and GUILD collections.
     *
     * @param id the command name to resolve
     * @return an Optional containing the command if found, otherwise empty
     */
    public Optional<CommandInteractionHandler<?>> get(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();
        var commandInteractionHandler = globalCommandInteractionHandlerRegistry.get(id);
        if (commandInteractionHandler.isPresent()) return commandInteractionHandler;
        else return guildCommandInteractionHandlerRegistry.get(id);
    }

    /**
     * Publishes the currently registered global commands to Discord.
     *
     * @param event the ReadyEvent fired when JDA is ready
     */
    public void setupGlobalCommands(@NotNull ReadyEvent event) {
        event.getJDA().updateCommands().addCommands(globalCommandInteractionHandlerRegistry.getCommandData()).queue();
        PCF.LOGGER.info("Updated global commands for {}", event.getJDA().getSelfUser().getName());
    }

    /**
     * Publishes the currently registered guild commands to a specific guild.
     *
     * @param guild the guild to update
     */
    public void setupGuildCommands(@NotNull Guild guild) {
        guild.updateCommands().addCommands(
                guildCommandInteractionHandlerRegistry.getCommandData(commandInteractionHandler -> {
                    if (commandInteractionHandler.getTargetGuildIDs().isEmpty()) return true;
                    return commandInteractionHandler.getTargetGuildIDs().contains(guild.getId());
                })
        ).queue();
        PCF.LOGGER.info("Updated guild ({} - {}) commands for {}", guild.getName(), guild.getId(), guild.getJDA().getSelfUser().getName());
    }

    /**
     * Command registration scope.
     */
    public enum Type {
        /**
         * Commands available globally across all guilds.
         */
        GLOBAL,
        /**
         * Commands registered for a specific guild.
         */
        GUILD
    }
}
