package dev.eposs.pcf.event;

import dev.eposs.pcf.PCF;
import dev.eposs.pcf.button.ButtonRegistry;
import dev.eposs.pcf.command.CommandRegistry;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * EventListener serves as the primary bridge for handling various events triggered by the bot's interaction
 * with Discord through the JDA library. This class extends {@code ListenerAdapter} and overrides specific
 * event-handling methods to provide custom implementations for application logic.
 * </p>
 * <p>
 * It includes the mechanism to set up and manage commands, handle slash command interactions, message context
 * interactions, and button interactions. Additionally, it leverages an {@code ExceptionHandler} to ensure that
 * errors during event processing are appropriately managed.
 * </p>
 */
public class EventListener extends ListenerAdapter {
    private final ExceptionHandler exceptionHandler;

    public EventListener(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        CommandRegistry.setupGlobalCommands(event);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        CommandRegistry.setupGuildCommands(event.getGuild());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        CommandRegistry.setupGuildCommands(event.getGuild());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        PCF.LOGGER.info("{} ({}) used /{}", event.getUser().getName(), event.getUser().getId(), event.getFullCommandName());
        CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
            try {
                cmd.execute(event);
            } catch (Exception e) {
                exceptionHandler.handleException(e, event);
            }
        });
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        PCF.LOGGER.info("{} ({}) used message context command \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getName());
        CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
            try {
                cmd.execute(event);
            } catch (Exception e) {
                exceptionHandler.handleException(e, event);
            }
        });
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String customId = event.getButton().getCustomId();
        if (customId == null) return;

        PCF.LOGGER.info("{} ({}) used button \"{}\"", event.getUser().getName(), event.getUser().getId(), customId);

        ButtonRegistry.getButton(customId).ifPresent(action -> {
            try {
                action.execute(event);
            } catch (Exception e) {
                exceptionHandler.handleException(e, event);
            }
        });
    }
}
