package de.telemans.minecraft.itemblocker;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ItemBlockerCommandExecuter implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getLogger().info(sender.getName() + " is using ibshow!");
        return false;
    }
}
