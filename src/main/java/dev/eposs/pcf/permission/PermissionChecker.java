package dev.eposs.pcf.permission;

import dev.eposs.pcf.PhoenixCommandFramework;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

public class PermissionChecker {
    private final IReplyCallback interaction;

    public PermissionChecker(@NotNull IReplyCallback interaction) {
        this.interaction = interaction;
    }

    public boolean replyAfterCheck(boolean check) {
        if (check) return true;

        if (interaction.isAcknowledged()) {
            interaction.getHook().sendMessage("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        } else {
            interaction.reply("Missing permission").useComponentsV2(false).setEphemeral(true).queue();
        }
        return false;
    }

    public boolean isBotOwner() {
        boolean check = interaction.getUser().getId().equals(PhoenixCommandFramework.getBotOwnerID());
        return replyAfterCheck(check);
    }

    public boolean isTrusted() {
        boolean check = PhoenixCommandFramework.isTrustedUser(interaction.getUser().getId());
        return replyAfterCheck(check);
    }

    public boolean isGuildAdmin() {
        Member member = interaction.getMember();
        if (member == null) return false;
        boolean check = member.hasPermission(Permission.ADMINISTRATOR);
        return replyAfterCheck(check);
    }
}
