package dev.eposs.pcf.internal.registry;

import dev.eposs.pcf.internal.CommandInteractionHandler;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.List;
import java.util.function.Predicate;

public class CommandInteractionHandlerRegistryImpl extends AbstractRegistry<CommandInteractionHandler<? extends GenericCommandInteractionEvent>> {
    @Override
    public void register(CommandInteractionHandler<? extends GenericCommandInteractionEvent> commandInteractionHandler) {
        handlers.put(commandInteractionHandler.getCommandData().getName(), commandInteractionHandler);
    }

    public List<CommandData> getCommandData() {
        return handlers.values().stream().map(CommandInteractionHandler::getCommandData).toList();
    }

    public List<CommandData> getCommandData(Predicate<CommandInteractionHandler<? extends GenericCommandInteractionEvent>> filter) {
        return handlers.values().stream().filter(filter).map(CommandInteractionHandler::getCommandData).toList();
    }
}
