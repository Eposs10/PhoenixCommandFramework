package dev.eposs.pcf.internal;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Set;

/// Base contract for all bot commands.
public interface CommandInteractionHandler<T> extends InteractionHandler<T> {

    /// Provides the JDA command definition for this command.
    CommandData getCommandData();

    /// Set of target guild IDs where the command will be registered as a guild-specific command.
    ///
    /// If the set is empty, the command will be registered across all available guilds.
    ///
    /// Only applicable for guild-specific commands.
    Set<String> getTargetGuildIDs();
}
