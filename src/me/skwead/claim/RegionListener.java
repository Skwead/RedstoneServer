package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class RegionListener implements Listener {

    private RegionManager rm;
    private RedstoneSRV plugin;

    public RegionListener(RegionManager rm, RedstoneSRV plugin) {
        this.rm = rm; this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        /*TODO: COrrigir o erro:
        [11:34:38 ERROR]: Could not pass event BlockBreakEvent to RedstoneSRV v_alfa_1.1.3.6-(claim system) java.lang.NullPointerException: null
        at me.skwead.claim.RegionListener.onBreak(RegionListener.java:29) ~[?:?]
        */
        Location location = new LocationUtils().getBlockChunk(e.getBlock());
        UUID owner = plugin.getRegionManager().getClaimOwner(location);
        plugin.getChatUtils().consoleMessage("&cEvento! &4"+location.toString());
//        plugin.getChatUtils().consoleMessage("&cDono: &4"+ Bukkit.getOfflinePlayer(owner).getName());

        if((/*!*/owner.equals(e.getPlayer().getUniqueId()))&&(owner != null)){
            e.setCancelled(true);
            plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão pode destruir blocos em terreno protegido.");
        }
    }

}
