package me.skwead.claim.commands;

import me.skwead.RedstoneSRV;
import me.skwead.utils.chat.MessageType;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Claim implements CommandExecutor {

    private RedstoneSRV plugin;

    public Claim(RedstoneSRV plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("claim")){// /claim radious
            if(!(sender instanceof Player)){
                plugin.getChatUtils().log(MessageType.ERROR,"Comando só para pessoas!");
                return true;
            }
            Player player = (Player) sender;
            int radious = Integer.parseInt(args[0])-1;
            Location chunk = new Location(player.getWorld(), player.getLocation().getChunk().getX(), 0,
                                            player.getLocation().getChunk().getZ());
            plugin.getRegionManager().claim(chunk, radious ,player);
            plugin.getChatUtils().log(MessageType.SUCCESS,"O jogador &3"+player.getName()+"&2 fez um terreno.");
            plugin.getChatUtils().playerMessage(player, "&aTerreno comprado com sucesso.");
            return true;
        }
        return false;
    }
}
