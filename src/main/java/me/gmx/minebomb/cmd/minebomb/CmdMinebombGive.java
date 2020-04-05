package me.gmx.minebomb.cmd.minebomb;

import me.gmx.minebomb.config.Lang;
import me.gmx.minebomb.core.BSubCommand;
import me.gmx.minebomb.item.MinebombItem;
import me.gmx.minebomb.util.ItemMetadata;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.text.ParseException;

public class CmdMinebombGive extends BSubCommand {

    public CmdMinebombGive() {
        this.aliases.add("give");
        this.permission = "minebomb.give";
        this.correctUsage = "/minebomb give [player] [amount] [size]";
    }

    public void execute(){
        if (args.length != 3){
            sendCorrectUsage();
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null){
            msg(Lang.MSG_NULLPLAYER.toMsg());
            return;
        }
        int i = 1;
        if (args[2].equalsIgnoreCase("small")){
            i = 3;
        }else if (args[2].equalsIgnoreCase("medium")) {
            i = 4;
        }else if (args[2].equalsIgnoreCase("large")){
            i = 5;
        }else{
            try{
                 i = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                msg("&4Please enter valid numbers for size and quantity!");
                return;
            }
        }

        try{
            if (Integer.parseInt(args[1]) > 64){
                int a = Integer.parseInt(args[1]);
                MinebombItem bomb = new MinebombItem(64,i);
                while(a > 64){
                    bomb.give(Bukkit.getPlayer(args[0]));
                    a-=64;
                }
                 bomb = new MinebombItem(a,i);
                bomb.give(Bukkit.getPlayer(args[0]));
            }else{
                MinebombItem bomb = new MinebombItem(Integer.parseInt(args[1]),i);
                bomb.give(Bukkit.getPlayer(args[0]));
            }


        }catch(NumberFormatException e){
            msg("&4Please enter valid numbers for size and quantity!");
            return;
        }



    }

}
