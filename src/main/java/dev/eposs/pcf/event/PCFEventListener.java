package dev.eposs.pcf.event;

import dev.eposs.pcf.PhoenixCommandFramework;
import dev.eposs.pcf.button.ButtonRegistry;
import dev.eposs.pcf.command.CommandRegistry;
import dev.eposs.pcf.entityselect.EntitySelectRegistry;
import dev.eposs.pcf.modal.ModalRegistry;
import dev.eposs.pcf.stringselect.StringSelectRegistry;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

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
public class PCFEventListener extends ListenerAdapter {
    ThreadFactory threadFactory = Thread.ofVirtual().name("PCF-Event-Thread").factory();

    private final IExceptionHandler exceptionHandler;

    public PCFEventListener(IExceptionHandler exceptionHandler) {
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
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used /{}", event.getUser().getName(), event.getUser().getId(), event.getFullCommandName());
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        threadFactory.newThread(() -> {
            String customId = event.getButton().getCustomId();
            if (customId == null) return;

            PhoenixCommandFramework.LOGGER.info("{} ({}) used button \"{}\"", event.getUser().getName(), event.getUser().getId(), customId);

            ButtonRegistry.getButton(customId).ifPresent(action -> {
                try {
                    action.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used message context command \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getName());
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used user context command \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getName());
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used modal \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getModalId());
            ModalRegistry.getModal(event.getModalId()).ifPresent(modal -> {
                try {
                    modal.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used string select \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getSelectMenu().getCustomId());
            StringSelectRegistry.getStringSelect(event.getSelectMenu().getCustomId()).ifPresent(action -> {
                try {
                    action.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        threadFactory.newThread(() -> {
            PhoenixCommandFramework.LOGGER.info("{} ({}) used entity select \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getSelectMenu().getCustomId());
            EntitySelectRegistry.getEntitySelect(event.getSelectMenu().getCustomId()).ifPresent(action -> {
                try {
                    action.execute(event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }
}
