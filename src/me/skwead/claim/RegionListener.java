package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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

}
