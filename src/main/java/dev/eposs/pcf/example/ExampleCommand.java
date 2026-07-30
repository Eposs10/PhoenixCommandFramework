package dev.eposs.pcf.example;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.handler.SlashCommandInteractionHandler;
import dev.eposs.pcf.api.handler.SubCommandGroupHandler;
import dev.eposs.pcf.api.handler.SubCommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;
import java.util.Set;

public class ExampleCommand extends SlashCommandInteractionHandler {
    @Override
    public List<SubCommandGroupHandler> getSubCommandGroups() {
        return List.of(
                new ExampleSubCommandGroup()
        );
    }

    @Override
    public List<SubCommandHandler> getSubCommands() {
        return List.of();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("example", "Example command")
                .addSubcommandGroups(getSubCommandGroupData());
    }

    @Override
    public Set<String> getTargetGuildIDs() {
        return Set.of();
    }
}
