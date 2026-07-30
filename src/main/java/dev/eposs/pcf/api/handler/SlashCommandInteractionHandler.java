package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.registry.SubCommandGroupHandlerRegistry;
import dev.eposs.pcf.api.registry.SubCommandHandlerRegistry;
import dev.eposs.pcf.internal.CommandInteractionHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.List;
import java.util.Optional;

public abstract class SlashCommandInteractionHandler implements CommandInteractionHandler<SlashCommandInteractionEvent> {

    private final SubCommandGroupHandlerRegistry subCommandGroupHandlerRegistry = new SubCommandGroupHandlerRegistry();
    private final SubCommandHandlerRegistry subCommandHandlerRegistry = new SubCommandHandlerRegistry();

    public SlashCommandInteractionHandler() {
        subCommandGroupHandlerRegistry.registerAll(getSubCommandGroups());
        subCommandHandlerRegistry.registerAll(getSubCommands());
    }

    public abstract List<SubCommandGroupHandler> getSubCommandGroups();

    public abstract List<SubCommandHandler> getSubCommands();

    public List<SubcommandGroupData> getSubCommandGroupData() {
        return subCommandGroupHandlerRegistry.getSubCommandGroupData();
    }

    public List<SubcommandData> getSubCommandData() {
        return subCommandHandlerRegistry.getSubCommandData();
    }

    /// Resolves the requested sub-command from the event and executes it if present.
    /// This handles both direct sub-commands and sub-commands within groups.
    ///
    /// @param pcf   the [PCF] instance
    /// @param event the slash command interaction
    /// @throws Exception if the sub-command execution throws
    @Override
    public void execute(PCF pcf, SlashCommandInteractionEvent event) throws Exception {
        String subCommandName = event.getSubcommandName();
        if (subCommandName == null) throw new IllegalArgumentException("This command does not have sub-commands, please implement SlashCommandInteractionHandler#execute");

        String subCommandGroupName = event.getSubcommandGroup();

        if (subCommandGroupName != null) {
            Optional<SubCommandGroupHandler> group = subCommandGroupHandlerRegistry.get(subCommandGroupName);
            if (group.isPresent()) group.get().execute(pcf, event);
            else throw new IllegalArgumentException("Sub-command group not found: " + subCommandGroupName);
        }

        Optional<SubCommandHandler> subCommand = subCommandHandlerRegistry.get(subCommandName);
        if (subCommand.isPresent()) subCommand.get().execute(pcf, event);
        else throw new IllegalArgumentException("Sub-command not found: " + subCommandName);
    }
}
