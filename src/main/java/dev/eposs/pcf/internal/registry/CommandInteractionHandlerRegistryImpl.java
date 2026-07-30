package dev.eposs.pcf.internal.registry;

import dev.eposs.pcf.internal.CommandInteractionHandler;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.List;
import java.util.function.Predicate;

public class CommandInteractionHandlerRegistryImpl extends AbstractRegistry<CommandInteractionHandler> {
    @Override
    public void register(CommandInteractionHandler commandInteractionHandler) {
        handlers.put(commandInteractionHandler.getCommandData().getName(), commandInteractionHandler);
    }

    public List<CommandData> getCommandData() {
        return handlers.values().stream().map(CommandInteractionHandler::getCommandData).toList();
    }

    public List<CommandData> getCommandData(Predicate<CommandInteractionHandler> filter) {
        return handlers.values().stream().filter(filter).map(CommandInteractionHandler::getCommandData).toList();
    }
}
