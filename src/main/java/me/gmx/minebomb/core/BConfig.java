package me.gmx.minebomb.core;

import me.gmx.minebomb.util.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class BConfig {

    private JavaPlugin main;
    private String name;
    private FileConfiguration fileConfiguration;
    private File file;

    public BConfig(JavaPlugin pl, String str){
        this.main = pl;
        this.name = str;
        load();
    }

    private void load(){
        if (!this.main.getDataFolder().exists()){
            FileUtils.mkdir(this.main.getDataFolder());
        }

        File f1 = new File(this.main.getDataFolder(),this.name);
        if (!f1.exists()){
            FileUtils.copy(this.main.getResource(this.name),f1);
        }
        this.file = f1;
        this.fileConfiguration = YamlConfiguration.loadConfiguration(f1);
        this.fileConfiguration.options().copyDefaults(true);

    }

    public void save() {
        this.fileConfiguration.options().copyDefaults(true);
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getConfig() { return this.fileConfiguration; }

}
