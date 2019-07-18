package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.jsonUtils.JSONUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RegionManager {
    private Map<Location, UUID> claim = new HashMap();
    private RedstoneSRV plugin;
    private JSONUtils jsonUtils = new JSONUtils();

    public RegionManager(RedstoneSRV plugin) {
        this.plugin = plugin;
    }

    public void claim(Location centreChunk, int radious, Player owner){
        UUID uuid = owner.getUniqueId();
//        int r = 16*radious;

        for(int i = -radious; i<=radious; i++){ //iteração x
            for(int j = -radious; j<=radious; j++){ //iteração z
                Location l = new Location(centreChunk.getWorld(), centreChunk.getX() + i, centreChunk.getY(), centreChunk.getZ() + j);
                claim.put(l, uuid);

                JSONObject newClaim = new JSONObject();
                newClaim.put("Owner", uuid);
                newClaim.put("X", centreChunk.getX() + i);
                newClaim.put("Z", centreChunk.getZ() + j);

                try{
                    addToClaimsFile(newClaim);
                } catch (Exception e){
                    e.printStackTrace();
                }
                plugin.getChatUtils().consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &9Adicionado terreno!");
            }
        }
    }

    public void addToClaimsFile(JSONObject object) throws IOException {
        FileWriter writer = new FileWriter(plugin.getClaimsFile());
        JSONParser p = new JSONParser();

        try {
//            JSONObject finalObj = jsonUtils.addJSONobj(jsonUtils.getJSONfromFile(plugin.getClaimsFile().getPath()), object, "Claim");
//            JSONObject finalObj = jsonUtils.addJSONobj((JSONObject) p.parse(plugin.getClaimsFile().toString()), object, "Claim");
            JSONObject finalObj = jsonUtils.addJSONobj((JSONObject) p.parse(new FileReader(plugin.getClaimsFile().toString())), object, "Claim");
            writer.write(finalObj.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}

/*
"Location":{
    "Location Owner": "",
    "Location X": "",
    "Location Z": ""
  }
 */
