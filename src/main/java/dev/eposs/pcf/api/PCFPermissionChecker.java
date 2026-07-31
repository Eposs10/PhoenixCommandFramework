package dev.eposs.pcf.api;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

/// A utility class to check permissions for a given interaction.
///
/// This class provides methods to verify if a user has certain privileges (e.g., bot owner, trusted user, guild admin)
/// and automatically sends a "Missing permission" reply if the check fails.
public class PCFPermissionChecker {
    private final PCF pcf;
    private final IReplyCallback interaction;

    /// Constructs a `PermissionChecker` for the specified interaction.
    ///
    /// @param interaction the interaction to check permissions for
    public PCFPermissionChecker(PCF pcf, @NotNull IReplyCallback interaction) {
        this.pcf = pcf;
        this.interaction = interaction;
    }

    /// Checks the result of a permission check and sends a reply if it failed.
    ///
    /// @param check the result of the permission check
    /// @return `true` if the check passed, `false` otherwise
    protected boolean replyAfterCheck(boolean check) {
        if (check) return true;

        if (interaction.isAcknowledged()) {
            interaction.getHook().sendMessage("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        } else {
            interaction.reply("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        }
        return false;
    }

    /// Checks if the user who triggered the interaction is the bot owner.
    /// Sends a "Missing permission" reply if the check fails.
    ///
    /// @return `true` if the user is the bot owner, `false` otherwise
    public boolean isBotOwner() {
        boolean check = interaction.getUser().getId().equals(pcf.getBotOwnerID());
        return replyAfterCheck(check);
    }

    /// Checks if the user who triggered the interaction is a trusted user.
    /// Sends a "Missing permission" reply if the check fails.
    ///
    /// @return `true` if the user is trusted, `false` otherwise
    public boolean isTrusted() {
        boolean check = pcf.isTrustedUser(interaction.getUser().getId());
        return replyAfterCheck(check);
    }

    /// Checks if the member who triggered the interaction has specific permissions in the guild.
    /// Sends a "Missing permission" reply if the check fails.
    ///
    /// @return `true` if the member is a guild admin, `false` otherwise
    /// @throws IllegalStateException if the member on the interaction is `null`
    public boolean hasPermissions(Permission... permissions) throws IllegalStateException {
        Member member = interaction.getMember();
        if (member == null) throw new IllegalStateException("Member is null. This happens if the interaction is not from a guild.");
        boolean check = member.hasPermission(permissions);
        return replyAfterCheck(check);
    }
}
