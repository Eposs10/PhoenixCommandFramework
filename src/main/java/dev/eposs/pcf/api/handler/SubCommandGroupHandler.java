package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.registry.SubCommandHandlerRegistry;
import dev.eposs.pcf.internal.InteractionHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.List;
import java.util.Optional;

public abstract class SubCommandGroupHandler implements InteractionHandler<SlashCommandInteractionEvent> {

    private final SubCommandHandlerRegistry subCommandHandlerRegistry = new SubCommandHandlerRegistry();

    public SubCommandGroupHandler() {
        subCommandHandlerRegistry.registerAll(getSubCommands());
    }

    public abstract SubcommandGroupData getSubcommandGroupData();

    public abstract List<SubCommandHandler> getSubCommands();

    public List<SubcommandData> getSubCommandData() {
        return subCommandHandlerRegistry.getSubCommandData();
    }

    /// Resolves the requested sub-command from the event and executes it if present.
    ///
    /// @param pcf   the [PCF] instance
    /// @param event the slash command interaction
    /// @throws Exception if the sub-command execution throws
    @Override
    public void execute(PCF pcf, SlashCommandInteractionEvent event) throws Exception {
        String name = event.getSubcommandName();
        if (name == null) return;

        Optional<SubCommandHandler> subCommand = subCommandHandlerRegistry.get(name);
        if (subCommand.isPresent()) subCommand.get().execute(pcf, event);
        else throw new IllegalArgumentException("Sub-command not found: " + name);
    }
}
