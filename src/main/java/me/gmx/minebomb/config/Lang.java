package me.gmx.minebomb.config;

import me.gmx.minebomb.core.BConfig;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public enum Lang {

    PREFIX("&3[&1Mine &2Bomb&3]&r "),
    MSG_NOACCESS("&4You do not have access to this command!"),
    MSG_RELOADED("&3Config reloaded."),
    MSG_PLAYERONLY("Only players can use this command!"),
    MSG_USAGE_SUBCOMMAND("Invalid Command usage. Correct usage: %usage%"),
    MSG_USAGE_MINEBOMB("/minebomb give [player] [size] [quantity]"),
    MSG_NULLPLAYER("&4Cannot find specified player!"),
    MINEBOMB_ITEM_LORE("&4Minebomb woohoo/&2Size: %size%/&3Right click to use"),
    MINEBOMB_ITEM_NAME("&4Mine&2Bomb"),
    MINEBOMB_PROJECTILE_NAME("&4&lMine&a&lBomb"),
    MINEBOMB_EXPL_MESSAGE("&4You can get more minebombs from /buy and /vote!"),
    LANG_CONSOLE("Console only!");

    private String defaultValue;
    private static BConfig config;

    Lang(String str){
        defaultValue = str;
    }
    public String getPath() { return name().replace("_", "."); }

    public String getDefaultValue() { return this.defaultValue; }

    public String toString() { return fixColors(config.getConfig().getString(getPath())); }

    public static void setConfig(BConfig paramBConfig) {
        config = paramBConfig;
        load();
    }

    public String toMsg() {
        boolean bool = true;
        if (bool) {
            return fixColors(config.getConfig().getString(PREFIX.getPath()) + config.getConfig()
                    .getString(getPath()));
        }
        return fixColors(config.getConfig().getString(getPath()));
    }

    public List<String> getStringList(){
        return Arrays.asList(toString().split("/"));
    }
    private static void load() {
        for (Lang lang : values()) {
            if (config.getConfig().getString(lang.getPath()) == null) {
                config.getConfig().set(lang.getPath(), lang.getDefaultValue());
            }
        }
        config.save();
    }


    public static String fixColors(String paramString) {
        if (paramString == null)
            return "";
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }

}
