package me.gmx.minebomb.item;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.gmx.minebomb.Minebomb;
import me.gmx.minebomb.WorldGuardHook;
import me.gmx.minebomb.config.Settings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MinebombItemThrown implements Item {

private int size;
private Player player;

    public MinebombItemThrown(int size, Player player){
        super();
        this.size = size;
        this.player = player;

    }

    public Player getOwn(){
        return player;
    }

    public void explode(){


    }







}
