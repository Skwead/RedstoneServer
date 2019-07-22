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
        /*TODO: COrrigir o erro:
        [11:34:38 ERROR]: Could not pass event BlockBreakEvent to RedstoneSRV v_alfa_1.1.3.6-(claim system) java.lang.NullPointerException: null
        at me.skwead.claim.RegionListener.onBreak(RegionListener.java:29) ~[?:?]
        */
        Location location = new LocationUtils().getBlockChunk(e.getBlock());
        UUID owner = plugin.getRegionManager().getClaimOwner(location);
        plugin.getChatUtils().consoleMessage("&cEvento! &4"+location.toString());
//        plugin.getChatUtils().consoleMessage("&cDono: &4"+ Bukkit.getOfflinePlayer(owner).getName());

        //bug here
        if((/*!*/owner.equals(e.getPlayer().getUniqueId()))&&(owner != null)){
            e.setCancelled(true);
            plugin.getChatUtils().playerMessage(e.getPlayer(), "&cNão pode destruir blocos em terreno protegido.");
        }
    }

}
