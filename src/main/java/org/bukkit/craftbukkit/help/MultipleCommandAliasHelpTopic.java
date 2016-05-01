package org.bukkit.craftbukkit.help;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.MultipleCommandAlias;
import org.bukkit.help.HelpTopic;

/**
 * This is a help topic implementation for {@link MultipleCommandAlias} commands.
 */
public class MultipleCommandAliasHelpTopic extends HelpTopic {

    private final MultipleCommandAlias alias;

    public MultipleCommandAliasHelpTopic(MultipleCommandAlias alias) {
        this.alias = alias;

        name = "/" + alias.getLabel();

        // Build short text
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alias.getCommands().length; i++) {
            if (i != 0) {
                sb.append(ChatColor.GOLD).append(" > ").append(ChatColor.WHITE);// >> /* https://pmd.github.io/pmd-5.4.1/pmd-java/rules/java/strings.html#InefficientStringBuffering */
            }
            sb.append('/');// 1 symbol ~ char
            sb.append(alias.getCommands()[i].getLabel());
        }
        shortText = sb.toString();

        // Build full text
        fullText = ChatColor.GOLD + "Alias for: " + ChatColor.WHITE + getShortText();
    }

    public boolean canSee(CommandSender sender) {
        if (amendedPermission == null) {
            if (sender.getClass().equals(ConsoleCommandSender.class)) {
                return true;
            }

            for (Command command : alias.getCommands()) {
                if (!command.testPermissionSilent(sender)) {
                    return false;
                }
            }

            return true;
        } else {
            return sender.hasPermission(amendedPermission);
        }
    }
}
