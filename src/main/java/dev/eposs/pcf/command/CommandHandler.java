package dev.eposs.pcf.command;

import dev.eposs.pcf.PhoenixCommandFramework;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

/**
 * Base contract for all bot commands.
 * <p>
 * Implementations provide the JDA {@link CommandData} used during command
 * registration and the execution logic for incoming interactions.
 */
public interface CommandHandler {

    /**
     * Provides the JDA command definition for this command.
     *
     * @return the command metadata used to register the command
     */
    CommandData getCommandData();

    /**
     * Executes the command for a generic interaction event.
     * Implementations may down-cast the event to a concrete type as needed.
     *
     * @param genericEvent the incoming interaction
     * @throws Exception if execution fails
     */
    void execute(GenericCommandInteractionEvent genericEvent) throws Exception;

    /**
     * Convenience guard that checks if the invoking user is not the owner.
     * If the user is not the owner, an ephemeral "Missing permission" message
     * is sent and {@code true} is returned.
     *
     * @param event the interaction event
     * @return {@code true} if the user is NOT the owner (permission denied), otherwise {@code false}
     */
    default boolean userIsNotOwner(@NotNull GenericCommandInteractionEvent event) {
        if (event.getUser().getId().equals(PhoenixCommandFramework.getBotOwnerID())) return false;

        if (event.isAcknowledged()) event.getHook().sendMessage("Missing permission").setEphemeral(true).queue();
        else event.reply("Missing permission").setEphemeral(true).queue();
        return true;
    }

    /**
     * Convenience guard that checks if the invoking user is not in the trusted
     * users list. The configured owner is always considered trusted.
     * If the user is not trusted, an ephemeral "Missing permission" message is
     * sent and {@code true} is returned.
     *
     * @param event the interaction event
     * @return {@code true} if the user is NOT trusted (permission denied), otherwise {@code false}
     */
    default boolean userIsNotTrusted(@NotNull GenericCommandInteractionEvent event) {
        if (!PhoenixCommandFramework.isTrustedUser(event.getUser().getId())) return false;

        if (event.isAcknowledged()) event.getHook().sendMessage("Missing permission").setEphemeral(true).queue();
        else event.reply("Missing permission").setEphemeral(true).queue();
        return true;
    }
    
    default boolean userIsNotGuildAdmin(@NotNull GenericCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member != null && member.hasPermission(Permission.ADMINISTRATOR)) return false; 
        
        if (event.isAcknowledged()) event.getHook().sendMessage("Missing permission").setEphemeral(true).queue();
        else event.reply("Missing permission").setEphemeral(true).queue();
        return true;
    }
}
