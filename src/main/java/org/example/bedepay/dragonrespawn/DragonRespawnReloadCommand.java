package org.example.bedepay.dragonrespawn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public class DragonRespawnReloadCommand extends Command implements PluginIdentifiableCommand {
    private final DragonRespawn plugin;

    public DragonRespawnReloadCommand(DragonRespawn plugin) {
        super("dragonrespawnreload");
        this.plugin = plugin;
        this.setDescription("Reloads the DragonRespawn configuration.");
        this.setUsage("/dragonrespawnreload");
        this.setPermission("dragonrespawn.reload");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("dragonrespawn.reload")) {
            plugin.reloadConfig();
            plugin.loadConfig();
            sender.sendMessage(ChatColor.GREEN + "DragonRespawn config reloaded!");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
