package me.skwead;

import me.skwead.claim.RegionManager;
import me.skwead.claim.commands.Claim;
import me.skwead.utils.ChatUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

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

        setupJSON();

        getCommand("claim").setExecutor(claimCmd);

        chatUtils.consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &9Ligado!");
    }

    private void setupJSON(){
        for(File file : this.getDataFolder().listFiles()){
            try{
                JSONParser jsonParser = new JSONParser();
                Object parsed = jsonParser.parse(new FileReader(file.getPath()));
                JSONObject jsonObject = (JSONObject) parsed;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
