package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.SubCommandGroupHandler;
import dev.eposs.pcf.internal.registry.AbstractRegistry;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.List;

public class SubCommandGroupHandlerRegistry extends AbstractRegistry<SubCommandGroupHandler> {
    @Override
    public void register(SubCommandGroupHandler subCommandGroupHandler) {
        handlers.put(subCommandGroupHandler.getSubcommandGroupData().getName(), subCommandGroupHandler);
    }

    public List<SubcommandGroupData> getSubCommandGroupData() {
        return handlers.values().stream().map(SubCommandGroupHandler::getSubcommandGroupData).toList();
    }
}
