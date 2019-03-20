package com.github.brun0xp.noxbattle.commands;

import com.github.brun0xp.noxbattle.Main;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@Getter
public class BattleCommand implements CommandExecutor {

    private final Main main;

    public BattleCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            this.help (commandSender);
            return true;
        }
        return true;
    }

    private void help(CommandSender commandSender) {
        for (String str : this.getMain().getLanguage().getStringList("commands.help.player")) {
            commandSender.sendMessage(str);
        }
    }
}
