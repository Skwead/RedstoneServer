//https://hub.spigotmc.org/javadocs/spigot/index.html?overview-summary.html

package me.skwead;

import me.skwead.claim.RegionListener;
import me.skwead.claim.RegionManager;
import me.skwead.claim.commands.Claim;
import me.skwead.utils.chat.ChatUtils;
import me.skwead.utils.chat.MessageType;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class RedstoneSRV extends JavaPlugin {

    private File claimsFile = new File(getDataFolder(), "/claims.json");

    public File getClaimsFile() { return claimsFile; }

    private RedstoneSRV plugin = this;
    private ChatUtils chatUtils = new ChatUtils(plugin);
    private RegionManager regionManager = new RegionManager(plugin);
    private Claim claimCmd = new Claim(plugin);
    private RegionListener regionListener = new RegionListener(plugin);

    public RegionManager getRegionManager() {
        return regionManager;
    }
    public ChatUtils getChatUtils() { return chatUtils; }

    @Override
    public void onEnable(){

        chatUtils.log(MessageType.INFO, "A ligar...");

        regionManager.setupClaims();

        getCommand("claim").setExecutor(claimCmd);
        getCommand("unclaim").setExecutor(claimCmd);

        getServer().getPluginManager().registerEvents(regionListener, this);

        chatUtils.log(MessageType.SUCCESS,"Servidor ligado.");
    }
}
