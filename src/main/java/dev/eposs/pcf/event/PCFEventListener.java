package dev.eposs.pcf.event;

import dev.eposs.pcf.PCF;
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

public class PCFEventListener extends ListenerAdapter {
    ThreadFactory threadFactory = Thread.ofVirtual()
            .name("PCF-Event-Thread")
            .uncaughtExceptionHandler((t, e) -> PCF.LOGGER.error("Uncaught exception in {}", t.getName(), e))
            .factory();

    private final PCF pcf;
    private final IExceptionHandler exceptionHandler;

    /**
     * Constructs a new instance of {@code PCFEventListener} with the specified {@code PCF} instance
     * and exception handler.
     *
     * @param pcf              the {@link PCF} instance
     * @param exceptionHandler the {@link IExceptionHandler}, must not be null
     */
    public PCFEventListener(PCF pcf, IExceptionHandler exceptionHandler) {
        this.pcf = pcf;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Constructs a new {@code PCFEventListener} with the specified {@code PCF} instance. 
     * The {@link PCFDefaultExceptionHandler} is used as the default exception handler.
     *
     * @param pcf the {@link PCF} instance
     */
    public PCFEventListener(PCF pcf) {
        this.pcf = pcf;
        this.exceptionHandler = new PCFDefaultExceptionHandler();
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
            PCF.LOGGER.info("{} ({}) used /{}", event.getUser().getName(), event.getUser().getId(), event.getFullCommandName());
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(pcf, event);
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

            ButtonRegistry.getButton(customId).ifPresent(action -> {
                try {
                    action.execute(pcf, event);
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
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(pcf, event);
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
            CommandRegistry.getCommand(event.getName()).ifPresent(cmd -> {
                try {
                    cmd.execute(pcf, event);
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
            ModalRegistry.getModal(event.getModalId()).ifPresent(modal -> {
                try {
                    modal.execute(pcf, event);
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
            StringSelectRegistry.getStringSelect(event.getSelectMenu().getCustomId()).ifPresent(action -> {
                try {
                    action.execute(pcf, event);
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
            EntitySelectRegistry.getEntitySelect(event.getSelectMenu().getCustomId()).ifPresent(action -> {
                try {
                    action.execute(pcf, event);
                } catch (Exception e) {
                    exceptionHandler.handleException(e, event);
                }
            });
        }).start();
    }
}
