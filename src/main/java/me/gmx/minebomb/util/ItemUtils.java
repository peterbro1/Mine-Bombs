package me.gmx.minebomb.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static ItemMeta getMeta(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            item.setItemMeta(meta);
        }
        return meta;
    }

    public static void setLore(ItemStack stack, String lore){
        ItemMeta meta = ItemUtils.getMeta(stack);
        meta.setLore(Arrays.asList(ChatColor.RESET + lore));
        stack.setItemMeta(meta);
    }
    public static void appendLore(ItemStack stack, String lore){
        ItemMeta meta = ItemUtils.getMeta(stack);
        List<String> lore_current = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
        lore_current.add(ChatColor.RESET + lore);
        meta.setLore(lore_current);
        stack.setItemMeta(meta);
    }
}
