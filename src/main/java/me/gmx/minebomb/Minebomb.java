package me.gmx.minebomb;

import me.gmx.minebomb.cmd.CmdMinebomb;
import me.gmx.minebomb.config.Lang;
import me.gmx.minebomb.config.Settings;
import me.gmx.minebomb.core.BConfig;
import me.gmx.minebomb.handler.MinebombHandler;
import me.gmx.minebomb.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Minebomb extends JavaPlugin {
    public BConfig mainConfig;
    public BConfig langConfig;
    Logger logger;
    private static Minebomb ins;
    public MinebombHandler handler;

    @Override
    public void onEnable(){
        ins = this;
        this.logger = getLogger();
        this.mainConfig = new BConfig(this,"Settings.yml");
        this.langConfig = new BConfig(this,"Lang.yml");
        Lang.setConfig(this.langConfig);
        Settings.setConfig(this.mainConfig);
        this.handler = new MinebombHandler(getInstance());
        registerListeners();
        registerCommands();
        this.logger.log(Level.INFO, String.format("[%s] Successfully enabled version %s!", new Object[] { getDescription().getName(), getDescription().getVersion() }));

    }

    @Override
    public void onDisable(){
        MinebombHandler.killAll();
    }

    public static Minebomb getInstance(){
        return ins;
    }


    public void registerCommands(){
        getCommand("minebomb").setExecutor(new CmdMinebomb(getInstance()));

    }
    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerListener(getInstance()),getInstance());
    }
}
