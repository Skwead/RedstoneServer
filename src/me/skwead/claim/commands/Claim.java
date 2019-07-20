package me.skwead.claim.commands;

import me.skwead.RedstoneSRV;
import me.skwead.jsonUtils.JSONUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Claim implements CommandExecutor {

    private RedstoneSRV plugin;

    public Claim(RedstoneSRV plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("claim")){// /claim radious
            if(!(sender instanceof Player)){
                plugin.getChatUtils().consoleMessage("&4[RedstoneSRV] &4[ERRO] &fComando só para pessoas!");
                return true;
            }
            Player player = (Player) sender;
            int radious = Integer.parseInt(args[0])-1;
            /**/try{
            /**/    plugin.getChatUtils().consoleMessage("&ccomando! Conteúdo: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
            /**/} catch (IOException e) {
            /**/    e.printStackTrace();
            /**/}
            Location chunk = new Location(player.getWorld(), player.getLocation().getChunk().getX(), 0,
                                            player.getLocation().getChunk().getZ());
            /**/plugin.getChatUtils().consoleMessage("&cclaim gerado " + chunk.getX() + " " + chunk.getZ() + " ");

            //logs no RegionManager.java
            plugin.getRegionManager().claim(chunk, radious ,player); plugin.getChatUtils().consoleMessage("&c claim adicionado");
            plugin.getChatUtils().consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &9O jogador &3"+player.getName()+"&9 fez um terreno.");
            plugin.getChatUtils().playerMessage(player, "&aTerreno comprado com sucesso.");
            return true;
        }
        return false;
    }
}
