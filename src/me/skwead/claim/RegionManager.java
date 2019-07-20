package me.skwead.claim;

import me.skwead.RedstoneSRV;
import me.skwead.jsonUtils.JSONUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
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

                String x = String.valueOf(centreChunk.getX() + i);
                String z = String.valueOf(centreChunk.getZ() + j);

                JSONObject newClaim = new JSONObject();
                newClaim.put("Owner", uuid.toString());
                newClaim.put("X", x);
                newClaim.put("Z", z);

                /**/plugin.getChatUtils().consoleMessage("&cOwner: &4" + uuid.toString());
                /**/plugin.getChatUtils().consoleMessage("&cX: &4" + x);
                /**/plugin.getChatUtils().consoleMessage("&cZ: &4" + z);

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
        /**/plugin.getChatUtils().consoleMessage("&cPós Parser FW: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));

        try {
            /**/plugin.getChatUtils().consoleMessage("&cFicheiro de config: &4"+plugin.getClaimsFile().getPath());
            /**/plugin.getChatUtils().consoleMessage("&cConteúdo do ficheiro: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
            JSONArray all = (JSONArray) p.parse(new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath())); //Here the file is already empty
            /**/plugin.getChatUtils().consoleMessage("&cConteúdo do json: &4"+all.toString());
            all.add(object);
            /**/plugin.getChatUtils().consoleMessage("&cNovo conteúdo: &4"+all.toString());
            /**/plugin.getChatUtils().consoleMessage("&cAntes FW: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
            try{
                FileWriter writer = new FileWriter(plugin.getClaimsFile());
                /**/plugin.getChatUtils().consoleMessage("&cPós FW: &4"+new JSONUtils().getStringFromFile(plugin.getClaimsFile().getPath()));
                writer.write(all.toString());
                writer.flush();
                writer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            /**/plugin.getChatUtils().consoleMessage("&cConteúdo do json: &4"+all.toJSONString());
            /**/FileReader r = new FileReader(plugin.getClaimsFile());
            /**/plugin.getChatUtils().consoleMessage("&cConteúdo do ficheiro: &4"+r.toString());
//            plugin.saveResource(plugin.getClaimsFile().getName(), true);
            /**/plugin.getChatUtils().consoleMessage("&cConfig salva. $4"+r.toString());
            /**/r.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
