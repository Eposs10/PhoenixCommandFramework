package dev.eposs.pcf.permission;

import dev.eposs.pcf.PhoenixCommandFramework;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class to check permissions for a given interaction.
 * <p>
 * This class provides methods to verify if a user has certain privileges (e.g., bot owner, trusted user, guild admin)
 * and automatically sends a "Missing permission" reply if the check fails.
 */
public class PermissionChecker {
    protected final IReplyCallback interaction;

    /**
     * Constructs a {@code PermissionChecker} for the specified interaction.
     *
     * @param interaction the interaction to check permissions for
     */
    public PermissionChecker(@NotNull IReplyCallback interaction) {
        this.interaction = interaction;
    }

    /**
     * Checks the result of a permission check and sends a reply if it failed.
     *
     * @param check the result of the permission check
     * @return {@code true} if the check passed, {@code false} otherwise
     */
    protected boolean replyAfterCheck(boolean check) {
        if (check) return true;

        if (interaction.isAcknowledged()) {
            interaction.getHook().sendMessage("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        } else {
            interaction.reply("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        }
        return false;
    }

    /**
     * Checks if the user who triggered the interaction is the bot owner.
     *
     * @return {@code true} if the user is the bot owner, {@code false} otherwise
     */
    public boolean isBotOwner() {
        boolean check = interaction.getUser().getId().equals(PhoenixCommandFramework.getBotOwnerID());
        return replyAfterCheck(check);
    }

    /**
     * Checks if the user who triggered the interaction is a trusted user.
     *
     * @return {@code true} if the user is trusted, {@code false} otherwise
     */
    public boolean isTrusted() {
        boolean check = PhoenixCommandFramework.isTrustedUser(interaction.getUser().getId());
        return replyAfterCheck(check);
    }

    /**
     * Checks if the member who triggered the interaction has administrative permissions in the guild.
     *
     * @return {@code true} if the member is a guild admin, {@code false} otherwise
     */
    public boolean isGuildAdmin() {
        Member member = interaction.getMember();
        if (member == null) return false;
        boolean check = member.hasPermission(Permission.ADMINISTRATOR);
        return replyAfterCheck(check);
    }
}
