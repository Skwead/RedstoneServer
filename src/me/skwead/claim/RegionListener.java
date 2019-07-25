package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.utils.LocationUtils;
import me.skwead.utils.chat.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionListener implements Listener {

    private RedstoneSRV plugin;

    public RegionListener(RedstoneSRV plugin) {this.plugin = plugin; }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Location location = new LocationUtils().getBlockChunk(e.getBlock());
        UUID owner = plugin.getRegionManager().getClaimOwner(location);

        if((owner != null) && (/*!*/owner.equals(e.getPlayer().getUniqueId()))){
            e.setCancelled(true);
            plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão pode destruir blocos em terreno protegido.");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
//        plugin.getRegionManager().updateInClaims(e);
//
//        //Para se entrar num terreno
//        plugin.getRegionManager().getInClaim().forEach(player ->{
//            if ((e.getPlayer().getUniqueId().equals(player))&&(e.getFrom().equals(e.getTo())))
//                e.setCancelled(true);
//                plugin.getChatUtils().playerMessage(Bukkit.getPlayer(player), "&cNão podes entrar em terrenos.");
//        });
        Map<Location, UUID> claims = plugin.getRegionManager().getClaims();

        for(Location l: claims.keySet()){
//            claims.keySet().contains(e.getFrom().getChunk())  //começa no terreno
//            claims.keySet().contains(e.getTo().getChunk())    //acaba no terreno

            Location from = new Location(e.getFrom().getWorld(), e.getFrom().getBlock().getLocation().getChunk().getX(), 0, e.getFrom().getBlock().getLocation().getChunk().getZ());
            Location to = new Location(e.getTo().getWorld(), e.getTo().getBlock().getLocation().getChunk().getX(), 0, e.getTo().getBlock().getLocation().getChunk().getZ());

            //TODO: Se as coordenadas dos chunks de xf e xi forem diferentes ele entra ou sai :nigga_think:
            if(from!=to){
                if(!claims.containsKey(from) && claims.containsKey(to)){//entra
                    plugin.getChatUtils().log(MessageType.INFO,"Entrou.");
                    plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão podes entrar aí!");
                    e.setCancelled(true);
                }
                if(claims.containsKey(from) && !claims.containsKey(to)) {//sai
                    plugin.getChatUtils().log(MessageType.INFO,"Saiu.");
                }
            }
        }
    }

}
