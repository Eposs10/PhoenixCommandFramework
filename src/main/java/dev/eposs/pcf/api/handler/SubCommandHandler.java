package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.internal.InteractionHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class SubCommandHandler implements InteractionHandler<SlashCommandInteractionEvent> {
    public abstract SubcommandData getSubcommandData();
}
