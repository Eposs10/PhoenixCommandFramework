package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.SubCommandHandler;
import dev.eposs.pcf.internal.registry.AbstractRegistry;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.List;

public class SubCommandHandlerRegistry extends AbstractRegistry<SubCommandHandler> {
    @Override
    public void register(SubCommandHandler subCommandHandler) {
        handlers.put(subCommandHandler.getSubcommandData().getName(), subCommandHandler);
    }

    public List<SubcommandData> getSubCommandData() {
        return handlers.values().stream().map(SubCommandHandler::getSubcommandData).toList();
    }
}
