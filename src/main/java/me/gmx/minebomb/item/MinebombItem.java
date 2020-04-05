package me.gmx.minebomb.item;

import me.gmx.minebomb.config.Lang;
import me.gmx.minebomb.util.ItemMetadata;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MinebombItem extends ItemStack {

    private List<String> lore;
    private int size;

    public MinebombItem(int number){
        new MinebombItem(number,5);
    }

    public MinebombItem(int number, int size){
        super(Material.MAGMA_CREAM,number);
        lore = new ArrayList<String>();
        if (size > 10)
            this.size = 10;
        else if (size < 1)
            this.size = 1;
        else
            this.size = size;
        for (String s : Lang.MINEBOMB_ITEM_LORE.getStringList()){
            lore.add(s.replace("%size%",String.valueOf(this.size)));
        }
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(Lang.MINEBOMB_ITEM_NAME.toString());
        meta.setLore(lore);
        setItemMeta(meta);
    }

    public void setSize(int size){
        if (size < 0)
            this.size = 1;
        else
            this.size = size;
        List<String> nL = new ArrayList<String>();
        for (String s : getItemMeta().getLore()){
            nL.add(s.replace("%size%",String.valueOf(size)));
        }
        ItemMeta i = getItemMeta();
        i.setLore(nL);
        setItemMeta(i);


    }
    public void give(Player p){
        p.getInventory().addItem(ItemMetadata.newWithNBTData(this,"minebomb",String.valueOf(this.size)));
    }


    public void toss(Player player){
        if (this.getAmount() > 1){
            this.setAmount(this.getAmount()-1);
        }
        Item thrown = player.getWorld().dropItem(player.getLocation(), this.clone().asOne());
        thrown.setVelocity(player.getLocation().getDirection());
        thrown.setCustomName(Lang.MINEBOMB_PROJECTILE_NAME.toString());
        thrown.setCustomNameVisible(true);
        thrown.setPickupDelay(Integer.MAX_VALUE);

    }

}
