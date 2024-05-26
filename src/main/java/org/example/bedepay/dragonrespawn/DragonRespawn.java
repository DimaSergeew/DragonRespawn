package org.example.bedepay.dragonrespawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DragonRespawn extends JavaPlugin implements Listener {
    private int respawnTime;
    private Map<String, String> announcements = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();

        // Регистрация команды
        registerCommands();

        getServer().getPluginManager().registerEvents(this, this);

        // Печать сообщения в консоль
        printStartupMessage();
    }

    public void loadConfig() {  // Изменено на public
        FileConfiguration config = getConfig();
        respawnTime = config.getInt("respawn-time");

        announcements.put("initial", config.getString("announcements.initial"));
        announcements.put("every-5-minutes", config.getString("announcements.every-5-minutes"));
        announcements.put("last-minute", config.getString("announcements.last-minute"));
        announcements.put("dragon-respawned", config.getString("announcements.dragon-respawned"));
    }

    private void registerCommands() {
        try {
            final Field bukkitCommandMap = getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

            commandMap.register(getName(), new DragonRespawnReloadCommand(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            new DragonRespawnTask(this, respawnTime, announcements).runTaskTimer(this, 0, 20);
        }
    }

    private void printStartupMessage() {
        String version = this.getDescription().getVersion();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "******************************************");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "*                                        *");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "*      " + ChatColor.YELLOW + "DragonRespawn Plugin Loaded" + ChatColor.GREEN + "      *");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "*      " + ChatColor.YELLOW + "Version: " + version + ChatColor.GREEN + "                    *");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "*      " + ChatColor.YELLOW + "Developed by: bedepay" + ChatColor.GREEN + "            *");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "*                                        *");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "******************************************");
    }
}
