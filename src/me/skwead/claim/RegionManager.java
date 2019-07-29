package me.skwead.claim;

import com.google.gson.JsonArray;
import me.skwead.RedstoneSRV;
import me.skwead.utils.LocationUtils;
import me.skwead.utils.chat.MessageType;
import me.skwead.utils.jsonUtils.JSONUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RegionManager {
    private Map<Location, UUID> claims = new HashMap();
    private List<UUID> inClaim = new ArrayList<>();
    private RedstoneSRV plugin;

    public RegionManager(RedstoneSRV plugin) {
        this.plugin = plugin;
    }

    public void claim(Location centreChunk, int radious, Player owner){
        UUID uuid = owner.getUniqueId();
//        int r = 16*radious;

        for(int i = -radious; i<=radious; i++){ //iteração x
            for(int j = -radious; j<=radious; j++){ //iteração z
                Location l = new Location(centreChunk.getWorld(), centreChunk.getX() + i ,0 ,centreChunk.getZ() + j);

                String x = String.valueOf(centreChunk.getX() + i);
                String z = String.valueOf(centreChunk.getZ() + j);

                JSONObject newClaim = new JSONObject();
                newClaim.put("Owner", uuid.toString());
                newClaim.put("World", centreChunk.getWorld().getName());
                newClaim.put("X", x);
                newClaim.put("Z", z);

                try{
                    addToClaimsFile(newClaim); plugin.getChatUtils().log(MessageType.INFO, "Adicionado ao ficheiro.");
                    claims.put(l, uuid); plugin.getChatUtils().log(MessageType.INFO, "Adicionado ao mapa.");
                } catch (Exception e){
                    e.printStackTrace();
                }

                plugin.getChatUtils().log(MessageType.SUCCESS, "Adicionado terreno! &a" + newClaim.toString());
            }
        }
        plugin.getChatUtils().log(MessageType.INFO, "claims.toString(): &4"+claims.toString());
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

    public void addClaim(Object JSONclaim){ //Passa do Objct para o HashMap<>
        JSONParser p = new JSONParser();
        try {
            JSONObject c = (JSONObject) p.parse(JSONclaim.toString());
            plugin.getRegionManager().getClaims().put(new Location(
                                plugin.getServer().getWorld(c.get("World").toString()),
                                Double.valueOf((String) c.get("X")), 0, Double.valueOf((String) c.get("Z"))), UUID.fromString((String) c.get("Owner")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID getClaimOwner(Location l){
        for(Map.Entry<Location, UUID> entry : claims.entrySet()){
            if(entry.getKey().equals(l)) return entry.getValue();
        }
        return null;
    }

    public void unclaim(Location location, UUID player){

        plugin.getChatUtils().log(MessageType.INFO, "A iniciar validação.");

        if((getClaimOwner(location)!= null) && (getClaimOwner(location).equals(player))){
            plugin.getChatUtils().log(MessageType.INFO, "O dono do terreno é o próprio.");
            try {
                JSONArray claimArr = new JSONUtils().getJSONArrayfromFile(plugin.getClaimsFile().getPath());

                for(int i = 0; i < claimArr.size(); i++){
                    JSONObject claimObj = (JSONObject)claimArr.get(i);
                    Location verify = new Location(Bukkit.getWorld((String)claimObj.get("World")), Double.valueOf((String)claimObj.get("X")), 0,
                            Double.valueOf((String)claimObj.get("X")));

                    plugin.getChatUtils().log(MessageType.INFO, "A iterar... ");
                    plugin.getChatUtils().log(MessageType.INFO, (new LocationUtils().getChunkCoords(verify).toString()));
                    plugin.getChatUtils().log(MessageType.INFO, location.toString());

                    if((new LocationUtils().getChunkCoords(verify)).equals(claims.get(location))){

                        plugin.getChatUtils().log(MessageType.INFO, "É igual.");

                        claims.remove(location);
                        claimArr.remove(i);

                        plugin.getChatUtils().log(MessageType.INFO, "Removidos");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        plugin.getChatUtils().playerMessage(Bukkit.getPlayer(player), "&cNão podes apagar um terreno que não tens.");
    }

    public Map<Location, UUID> getClaims() {
        return claims;
    }
}
