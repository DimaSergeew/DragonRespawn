package org.example.bedepay.dragonrespawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class DragonRespawnTask extends BukkitRunnable {
    private final DragonRespawn plugin;
    private final int respawnTime;
    private final Map<String, String> announcements;
    private int timeLeft;

    public DragonRespawnTask(DragonRespawn plugin, int respawnTime, Map<String, String> announcements) {
        this.plugin = plugin;
        this.respawnTime = respawnTime;
        this.announcements = announcements;
        this.timeLeft = respawnTime;
    }

    @Override
    public void run() {
        if (timeLeft <= 0) {
            World endWorld = Bukkit.getWorld("world_the_end");
            if (endWorld != null) {
                Location spawnLocation = new Location(endWorld, 0, 65, 0);
                endWorld.spawn(spawnLocation, EnderDragon.class);
                Bukkit.broadcastMessage(announcements.get("dragon-respawned"));
            }
            cancel();
            return;
        }

        if (timeLeft == respawnTime) {
            Bukkit.broadcastMessage(announcements.get("initial").replace("{minutes}", String.valueOf(timeLeft / 60)));
        } else if (timeLeft % 300 == 0) {
            Bukkit.broadcastMessage(announcements.get("every-5-minutes").replace("{minutes}", String.valueOf(timeLeft / 60)));
        } else if (timeLeft == 60) {
            Bukkit.broadcastMessage(announcements.get("last-minute"));
        }

        timeLeft--;
    }
}