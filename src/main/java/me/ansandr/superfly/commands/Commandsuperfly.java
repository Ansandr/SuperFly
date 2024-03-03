package me.ansandr.superfly.commands;

import me.ansandr.superfly.SuperFly;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.ansandr.superfly.util.MessageManager.tl;

public class Commandsuperfly implements CommandExecutor {

    private SuperFly plugin;

    public Commandsuperfly(SuperFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equals("reload") && sender.isOp()) {
                plugin.onReload();
                sender.sendMessage(tl("config_reloaded"));
                return true;
            }
        }

        return true;
    }
}
