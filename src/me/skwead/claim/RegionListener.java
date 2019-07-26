package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.utils.LocationUtils;
import me.skwead.utils.chat.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.UUID;

public class RegionListener implements Listener {

    private RedstoneSRV plugin;

    public RegionListener(RedstoneSRV plugin) {this.plugin = plugin; }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Map<Location, UUID> claims = plugin.getRegionManager().getClaims();
        Location location = new LocationUtils().getBlockChunk(e.getBlock()); location.setY(0);
        UUID owner = plugin.getRegionManager().getClaimOwner(location);

        if((owner != null) && (!owner.equals(e.getPlayer().getUniqueId()))){
            if((e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) && (!plugin.getRegionManager().getClaimOwner(location).equals(owner))){
            e.setCancelled(true);
            plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão podes destruir blocos fora do teu terreno em modo criativo.");
            }
            e.setCancelled(true);
            plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão podes destruir blocos em terreno protegido.");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Map<Location, UUID> claims = plugin.getRegionManager().getClaims();

        for(Location l: claims.keySet()){

            Location from = new Location(e.getFrom().getWorld(), e.getFrom().getBlock().getLocation().getChunk().getX(), 0, e.getFrom().getBlock().getLocation().getChunk().getZ());
            Location to = new Location(e.getTo().getWorld(), e.getTo().getBlock().getLocation().getChunk().getX(), 0, e.getTo().getBlock().getLocation().getChunk().getZ());

            if(from!=to){
                if(!claims.containsKey(from) && claims.containsKey(to)){//entra
                    plugin.getChatUtils().log(MessageType.INFO,"Entrou.");
                    if(!e.getPlayer().getUniqueId().equals(plugin.getRegionManager().getClaimOwner(to))){
                        plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão podes entrar aí! O dono desse terreno é o &4"+Bukkit.getPlayer(plugin.getRegionManager().getClaimOwner(to)).getName());
                        e.setCancelled(true);
                        return;
                    }
                    e.getPlayer().setGameMode(GameMode.CREATIVE);
                }
                if(claims.containsKey(from) && !claims.containsKey(to)) {//sai
                    plugin.getChatUtils().log(MessageType.INFO,"Saiu.");
                    if(!e.getPlayer().equals(plugin.getRegionManager().getClaimOwner(from))){
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                    }
                }
            }
        }
    }

}
