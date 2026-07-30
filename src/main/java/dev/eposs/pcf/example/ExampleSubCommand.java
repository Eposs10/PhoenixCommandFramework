package dev.eposs.pcf.example;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.handler.SubCommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class ExampleSubCommand extends SubCommandHandler {

    @Override
    public SubcommandData getSubcommandData() {
        return new SubcommandData("example", "Example sub-command")
                .addOptions(EPHEMERAL_OPTION);
    }

    @Override
    public void execute(PCF pcf, SlashCommandInteractionEvent event) throws Exception {

    }
}
