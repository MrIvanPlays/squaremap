package net.pl3x.map.command.commands;

import cloud.commandframework.Command;
import cloud.commandframework.extra.confirmation.CommandConfirmationManager;
import cloud.commandframework.meta.CommandMeta;
import net.pl3x.map.Pl3xMap;
import net.pl3x.map.command.CommandManager;
import net.pl3x.map.command.Pl3xMapCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

public final class ConfirmCommand extends Pl3xMapCommand {

    private final CommandConfirmationManager<CommandSender> confirmationManager = new CommandConfirmationManager<>(
            15L,
            TimeUnit.SECONDS,
            context -> context.getCommandContext().getSender().sendMessage(ChatColor.RED + "Confirmation required. Confirm using /pl3xmap confirm."),
            sender -> sender.sendMessage(ChatColor.RED + "You don't have any pending commands.")
    );

    public ConfirmCommand(final @NonNull Pl3xMap plugin, final @NonNull CommandManager commandManager) {
        super(plugin, commandManager);
    }

    @Override
    public void register() {
        this.confirmationManager.registerConfirmationProcessor(this.commandManager);

        final Command<CommandSender> confirm = this.commandManager.commandBuilder("pl3xmap")
                .literal("confirm")
                .meta(CommandMeta.DESCRIPTION, "Confirm a pending command")
                .handler(this.confirmationManager.createConfirmationExecutionHandler())
                .build();
        this.commandManager.command(confirm);
    }
}
