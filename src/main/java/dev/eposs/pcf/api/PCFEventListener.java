package dev.eposs.pcf.api;

import dev.eposs.pcf.internal.DefaultExceptionHandler;
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

public class PCFEventListener extends ListenerAdapter {
    ThreadFactory threadFactory = Thread.ofVirtual()
            .name("PCF-Event-Thread")
            .uncaughtExceptionHandler((t, e) -> PCF.LOGGER.error("Uncaught exception in {}", t.getName(), e))
            .factory();

    private final PCF pcf;
    private final ExceptionHandler exceptionHandler;

    /**
     * Constructs a new instance of {@code PCFEventListener} with the specified {@code PCF} instance
     * and exception handler.
     *
     * @param pcf              the {@link PCF} instance
     * @param exceptionHandler the {@link ExceptionHandler}, must not be null
     */
    public PCFEventListener(PCF pcf, ExceptionHandler exceptionHandler) {
        this.pcf = pcf;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Constructs a new {@code PCFEventListener} with the specified {@code PCF} instance.
     * The {@link DefaultExceptionHandler} is used as the default exception handler.
     *
     * @param pcf the {@link PCF} instance
     */
    public PCFEventListener(PCF pcf) {
        this.pcf = pcf;
        this.exceptionHandler = new DefaultExceptionHandler();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        pcf.COMMANDS.setupGlobalCommands(event);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        pcf.COMMANDS.setupGuildCommands(event.getGuild());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        pcf.COMMANDS.setupGuildCommands(event.getGuild());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used /{}", event.getUser().getName(), event.getUser().getId(), event.getFullCommandName());
            pcf.COMMANDS.get(event.getName()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
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

            PCF.LOGGER.info("{} ({}) used button \"{}\"", event.getUser().getName(), event.getUser().getId(), customId);

            pcf.BUTTONS.get(customId).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used message context command \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getName());
            pcf.COMMANDS.get(event.getName()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used user context command \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getName());
            pcf.COMMANDS.get(event.getName()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used modal \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getModalId());
            pcf.MODALS.get(event.getModalId()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used string select \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getSelectMenu().getCustomId());
            pcf.STRING_SELECT_MENUS.get(event.getSelectMenu().getCustomId()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        threadFactory.newThread(() -> {
            PCF.LOGGER.info("{} ({}) used entity select \"{}\"", event.getUser().getName(), event.getUser().getId(), event.getSelectMenu().getCustomId());
            pcf.ENTITY_SELECT_MENUS.get(event.getSelectMenu().getCustomId()).ifPresent(handler -> {
                try {
                    handler.executeRaw(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }
}
