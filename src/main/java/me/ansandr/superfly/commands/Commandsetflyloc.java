package me.ansandr.superfly.commands;

import me.ansandr.superfly.Fly;
import me.ansandr.superfly.SuperFly;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.ansandr.superfly.FlyingManager.storage;

public class Commandsetflyloc implements CommandExecutor {

    private SuperFly plugin;

    public Commandsetflyloc(SuperFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player)sender;

        if (sender.hasPermission("superfly.fly")) {
            sender.sendMessage("In developing");

            //Fly fly = new Fly(player,);
            //storage.put(player, fly);
        }

        return true;
    }
}
