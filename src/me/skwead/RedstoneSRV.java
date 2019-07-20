package me.skwead;

import me.skwead.claim.RegionManager;
import me.skwead.claim.commands.Claim;
import me.skwead.jsonUtils.JSONUtils;
import me.skwead.utils.ChatUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class RedstoneSRV extends JavaPlugin {

    private File claimsFile = new File(getDataFolder(), "/claims.json");

    public File getClaimsFile() { return claimsFile; }

    private RedstoneSRV plugin = this;
    private ChatUtils chatUtils = new ChatUtils(plugin);
    private RegionManager regionManager = new RegionManager(plugin);
    private Claim claimCmd = new Claim(plugin);

    public RegionManager getRegionManager() {
        return regionManager;
    }
    public ChatUtils getChatUtils() { return chatUtils; }

    @Override
    public void onEnable(){

        chatUtils.consoleMessage("&4[RedstoneSRV] &e[INFO] &9A ligar...");
        try {
            plugin.getChatUtils().consoleMessage("&c Conteúdo do ficheiro: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getCommand("claim").setExecutor(claimCmd);

        chatUtils.consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &9Ligado!");
    }
}
