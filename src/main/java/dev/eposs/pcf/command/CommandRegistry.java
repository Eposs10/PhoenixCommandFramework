package dev.eposs.pcf.command;

import dev.eposs.pcf.PhoenixCommandFramework;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
public class CommandRegistry {
    private CommandRegistry() {
    }

    /**
     * Holds globally available commands mapped by their command name.
     */
    private static final Map<String, CommandHandler> GLOBAL_COMMANDS = new ConcurrentHashMap<>();
    /**
     * Holds per-guild commands mapped by their command name.
     */
    private static final Map<String, CommandHandler> GUILD_COMMANDS = new ConcurrentHashMap<>();

    /**
     * Registers a single command for the given scope.
     *
     * @param type    the registration scope (GLOBAL or GUILD)
     * @param command the command instance to register
     */
    private static void register(@NotNull Type type, CommandHandler command) {
        switch (type) {
            case GLOBAL -> GLOBAL_COMMANDS.put(command.getCommandData().getName(), command);
            case GUILD -> GUILD_COMMANDS.put(command.getCommandData().getName(), command);
        }
    }

    /**
     * Registers multiple commands for the given scope.
     *
     * @param type     the registration scope (GLOBAL or GUILD)
     * @param commands the command instances to register
     */
    public static void register(@NotNull Type type, @NotNull CommandHandler... commands) {
        for (CommandHandler command : commands) register(type, command);
    }

    /**
     * Looks up a command by name from the registered GLOBAL and GUILD collections.
     *
     * @param name the command name to resolve
     * @return an Optional containing the command if found, otherwise empty
     */
    @NotNull
    public static Optional<CommandHandler> getCommand(String name) {
        CommandHandler command = GLOBAL_COMMANDS.get(name);
        if (command == null) command = GUILD_COMMANDS.get(name);
        return Optional.ofNullable(command);
    }

    /**
     * Publishes the currently registered global commands to Discord.
     *
     * @param event the ReadyEvent fired when JDA is ready
     */
    public static void setupGlobalCommands(@NotNull ReadyEvent event) {
        event.getJDA().updateCommands().addCommands(GLOBAL_COMMANDS.values().stream().map(CommandHandler::getCommandData).toList()).queue();
        PhoenixCommandFramework.LOGGER.info("Updated global commands for {}", event.getJDA().getSelfUser().getName());
    }

    /**
     * Publishes the currently registered guild commands to a specific guild.
     *
     * @param guild the guild to update
     */
    public static void setupGuildCommands(@NotNull Guild guild) {
        guild.updateCommands().addCommands(GUILD_COMMANDS.values().stream()
                .filter(commandHandler -> {
                    if (commandHandler.getTargetGuildIDs().isEmpty()) return true;
                    return commandHandler.getTargetGuildIDs().contains(guild.getId());
                })
                .map(CommandHandler::getCommandData).toList()).queue();
        PhoenixCommandFramework.LOGGER.info("Updated guild ({} - {}) commands for {}", guild.getName(), guild.getId(), guild.getJDA().getSelfUser().getName());
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
