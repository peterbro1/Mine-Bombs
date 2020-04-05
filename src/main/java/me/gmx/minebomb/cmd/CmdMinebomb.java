package me.gmx.minebomb.cmd;

import me.gmx.minebomb.Minebomb;
import me.gmx.minebomb.cmd.minebomb.CmdMinebombGive;
import me.gmx.minebomb.config.Lang;
import me.gmx.minebomb.core.BCommand;
import me.gmx.minebomb.core.BSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdMinebomb extends BCommand implements CommandExecutor {
    public CmdMinebomb(Minebomb ins) {
        super(ins);
        this.subcommands.add(new CmdMinebombGive());

    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg3.length < 1) {
            arg0.sendMessage(Lang.MSG_USAGE_MINEBOMB.toMsg());
            return true;
        }



        for (BSubCommand cmd : this.subcommands) {
            if (cmd.aliases.contains(arg3[0])) {
                cmd.execute(arg0,arg3);
                return true;
            }
        }
        arg0.sendMessage(Lang.MSG_USAGE_MINEBOMB.toMsg());

        return true;
    }


}
