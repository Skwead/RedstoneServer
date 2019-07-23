package me.skwead.utils.chat;

import com.mojang.brigadier.Message;
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

    public void log(MessageType type, String message){
        switch (type){
            case SUCCESS:
                consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &2"+message);
                break;
            case INFO:
                consoleMessage("&4[RedstoneSRV] &e[INFO] &6"+message);
                break;
            case ERROR:
                consoleMessage("&4[RedstoneSRV] &c[ERRO] &4"+message);
                break;
        }
    }
}
