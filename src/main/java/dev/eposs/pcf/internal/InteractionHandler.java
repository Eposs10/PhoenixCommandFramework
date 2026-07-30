package dev.eposs.pcf.internal;

import dev.eposs.pcf.api.PCF;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public interface InteractionHandler<T extends GenericEvent> {

    /// Executes the interaction handler.
    ///
    /// @param pcf   the [PCF] instance
    /// @param event the incoming interaction
    /// @throws Exception if execution fails
    void execute(PCF pcf, T event) throws Exception;

    @SuppressWarnings("unchecked")
    default void executeRaw(PCF pcf, GenericEvent event) throws Exception {
        execute(pcf, (T) event);
    }

    /// Standard optional boolean option to control whether a command response should be ephemeral. If omitted, the default behavior should be ephemeral.
    ///
    /// @see isEphemeral
    OptionData EPHEMERAL_OPTION = new OptionData(
            OptionType.BOOLEAN,
            "ephemeral",
            "Should the response be ephemeral? Default: true",
            false
    );

    /// Determines whether the response to the slash command should be ephemeral.
    /// If the "ephemeral" option is not provided in the slash command interaction,
    /// the response defaults to ephemeral (true).
    ///
    /// @param event the [SlashCommandInteractionEvent] containing the slash command interaction data
    /// @return `true` if the response is ephemeral, `false` otherwise
    /// @see EPHEMERAL_OPTION
    default boolean isEphemeral(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping ephemeral = event.getOption("ephemeral");

        if (ephemeral == null) return true;
        return ephemeral.getAsBoolean();
    }
}
