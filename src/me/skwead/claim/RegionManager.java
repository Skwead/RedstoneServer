package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.utils.jsonUtils.JSONUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RegionManager {
    private Map<UUID, Location> claims = new HashMap();
    private RedstoneSRV plugin;

    public RegionManager(RedstoneSRV plugin) {
        this.plugin = plugin;
    }

    public void claim(Location centreChunk, int radious, Player owner){
        UUID uuid = owner.getUniqueId();
//        int r = 16*radious;

        for(int i = -radious; i<=radious; i++){ //iteração x
            for(int j = -radious; j<=radious; j++){ //iteração z
                Location l = new Location(centreChunk.getWorld(), centreChunk.getX() + i, centreChunk.getY(), centreChunk.getZ() + j);
                claims.put(uuid, l);

                String x = String.valueOf(centreChunk.getX() + i);
                String z = String.valueOf(centreChunk.getZ() + j);

                JSONObject newClaim = new JSONObject();
                newClaim.put("Owner", uuid.toString());
                newClaim.put("World", centreChunk.getWorld().getName());
                newClaim.put("X", x);
                newClaim.put("Z", z);

                addClaim(newClaim);

                try{
                    addToClaimsFile(newClaim);
                } catch (Exception e){
                    e.printStackTrace();
                }
                plugin.getChatUtils().consoleMessage("&4[RedstoneSRV] &a[SUCESSO] &9Adicionado terreno! &a" + newClaim.toString());
            }
        }
    }

    public void addToClaimsFile(JSONObject object) throws IOException {
        JSONParser p = new JSONParser();

        try {
            JSONArray all = (JSONArray) p.parse(new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
            all.add(object);
            try{
                FileWriter writer = new FileWriter(plugin.getClaimsFile());
                writer.write(all.toString());
                writer.flush();
                writer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setupClaims(){
        try {
            JSONArray claims = new JSONUtils().getJSONArrayfromFile(plugin.getClaimsFile().getPath());
            claims.forEach(claim -> addClaim(claim));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addClaim(Object JSONclaim){
        JSONParser p = new JSONParser();
        try {
            JSONObject c = (JSONObject) p.parse(JSONclaim.toString());
            plugin.getRegionManager().getClaims().put(UUID.fromString((String) c.get("Owner")), new Location(
                    plugin.getServer().getWorld(c.get("World").toString()),
                    Double.valueOf((String) c.get("X")), 0, Double.valueOf((String) c.get("Z"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID getClaimOwner(Location l){ //claim<UUID, Location>
        for(Map.Entry<UUID, Location> entry : claims.entrySet()){
            if(entry.getValue() == l) return entry.getKey();
        }
        return null;
    }

    public Map<UUID, Location> getClaims() {
        return claims;
    }
}

/*
[
  {
      "Location":{
          "Location Owner": "d3d2d76f-1f91-454f-9bf9-bb1cf2f2f385",
          "Location X": "-11",
          "Location Z": "2"
    }
  }
]
 */
