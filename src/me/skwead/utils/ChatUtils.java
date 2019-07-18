package me.skwead.utils;

import me.skwead.RedstoneSRV;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {
    private RedstoneSRV plugin;

    public ChatUtils(RedstoneSRV instance) {
        plugin = instance;
    }

    public void consoleMessage(String message){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void playerMessage(Player recipient, String message){
        recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
