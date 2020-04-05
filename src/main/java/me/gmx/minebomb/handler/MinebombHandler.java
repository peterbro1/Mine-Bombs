package me.gmx.minebomb.handler;

import me.gmx.minebomb.Minebomb;
import me.gmx.minebomb.item.MinebombItemThrown;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinebombHandler {

    private Minebomb ins;
    private static HashMap<Item,Player> _backend;
    public MinebombHandler(Minebomb ins){
       this.ins = ins;
       _backend = new HashMap<Item,Player>();
       init();
    }

    public void init(){

    }

    public static void killAll(){
        for (Item i : _backend.keySet()){
            i.remove();
        }
        _backend.clear();
    }

    public static void add(Item a,Player p){
        _backend.put(a,p);
    }
    public static void remove(Item a){
        if (_backend.containsKey(a))
            _backend.remove(a);
    }

    public static void removeFromPlayer(Player p){
        for (Map.Entry<Item,Player> e :  _backend.entrySet()){
            if (e.getValue().equals(p)){
                e.getKey().remove();
                _backend.remove(e.getKey());
            }
        }
    }

    public static int count(Player p){
        int i = 0;
        for (Map.Entry e : _backend.entrySet()){
            if (e.getValue().equals(p))
                i++;
        }
        return i;
    }
    public static boolean isBomb(UUID uuid){
        for (Item e :  _backend.keySet()){
            if (e.getUniqueId().equals(uuid)){
                return true;
            }
        }
        return false;
    }





}
