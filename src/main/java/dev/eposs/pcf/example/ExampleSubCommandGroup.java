package dev.eposs.pcf.example;

import dev.eposs.pcf.api.handler.SubCommandGroupHandler;
import dev.eposs.pcf.api.handler.SubCommandHandler;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.List;

public class ExampleSubCommandGroup extends SubCommandGroupHandler {
    
    @Override
    public SubcommandGroupData getSubcommandGroupData() {
        return new SubcommandGroupData("example", "Example sub-command group")
                .addSubcommands(getSubCommandData());
    }

    @Override
    public List<SubCommandHandler> getSubCommands() {
        return List.of(
                new ExampleSubCommand()
        );
    }
}
