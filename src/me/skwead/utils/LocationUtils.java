package me.skwead.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class LocationUtils {

    public Location getBlockChunk(Block block){
        return new Location(block.getWorld(), block.getChunk().getX(), 0, block.getChunk().getZ());
    }
}
