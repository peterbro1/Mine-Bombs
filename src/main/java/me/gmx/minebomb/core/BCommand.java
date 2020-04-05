package me.gmx.minebomb.core;

import me.gmx.minebomb.Minebomb;

import java.util.ArrayList;

public class BCommand {
    public Minebomb main;
    public ArrayList<BSubCommand> subcommands;

    public BCommand(Minebomb ins){
        this.main = ins;
        subcommands = new ArrayList<BSubCommand>();
    }
}
