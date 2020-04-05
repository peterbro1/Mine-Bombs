package me.gmx.minebomb.config;

import me.gmx.minebomb.core.BConfig;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public enum Settings {

    EXPLODE_DELAY("3"),
    PARTICLE_EFFECT("true"),
    MAX_BOMBS("2");



    private String defaultValue;
    private static BConfig config;

    Settings(String str) {
        defaultValue = str;
    }

    public String getPath() { return name().replace("_", "."); }

    public String getDefaultValue() { return this.defaultValue; }

    public String getString(){
        return color(config.getConfig().getString(getPath()));
    }

    public static void setConfig(BConfig paramBConfig) {
        config = paramBConfig;
        load();
    }

    public List<String> getStringList(){
        return Arrays.asList(getString().split("/"));
    }

    public int getNumber() {
        return Integer.parseInt(config.getConfig().getString(getPath()));
    }

    public boolean getBoolean() {

        try {
            return Boolean.valueOf(config.getConfig().getString(getPath()));
        }catch(NullPointerException e) {
            return false;
        }

    }

    private static void load() {
        for (Settings lang : values()) {
            if (config.getConfig().getString(lang.getPath()) == null) {
                config.getConfig().set(lang.getPath(), lang.getDefaultValue());
            }
        }
        config.save();
    }

    private String color(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }
}
