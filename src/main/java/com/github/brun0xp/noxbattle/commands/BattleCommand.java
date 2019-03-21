package com.github.brun0xp.noxbattle.commands;

import com.github.brun0xp.noxbattle.Main;
import com.github.brun0xp.noxbattle.utils.Arena;
import com.github.brun0xp.noxbattle.utils.Battle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class BattleCommand implements CommandExecutor {

    private final Main main;

    public BattleCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            this.help(commandSender);
            return true;
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase(this.getMain().getConfig().getString("commands.accept"))) {
                if (commandSender instanceof Player) {
                    Player challenged = (Player) commandSender;
                    this.accept(challenged);
                } else {
                    commandSender.sendMessage(this.getMain().getLanguage().getString("command.duel.error-console"));
                }
            }
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase(this.getMain().getConfig().getString("commands.duel"))) {
                if (commandSender instanceof Player) {
                    Player challenger = (Player) commandSender;
                    Player challenged = Bukkit.getPlayer(strings[1]);
                    this.duel(challenger, challenged);
                } else {
                    commandSender.sendMessage(this.getMain().getLanguage().getString("commands.duel.error-console"));
                }
                return true;
            }
        }
        return true;
    }

    private void help(CommandSender commandSender) {
        if (commandSender.hasPermission("noxbattle.command.help")) {
            for (String str : this.getMain().getLanguage().getStringList("commands.help.player")) {
                commandSender.sendMessage(str);
            }
        } else {
            commandSender.sendMessage(this.getMain().getLanguage().getString("commands.no-permission"));
        }
    }

    private void duel(Player challenger, Player challenged) {
        if (!challenger.hasPermission("noxbattle.command.duel")) {
            challenger.sendMessage(this.getMain().getLanguage().getString("commands.no-permission"));
            return;
        }
        if (Battle.isBattling(challenger)) {
            challenger.sendMessage(this.getMain().getLanguage().getString("commands.duel.wait"));
            return;
        }
        if (Battle.isBattling(challenged)) {
            challenger.sendMessage(this.getMain().getLanguage().getString("player-already-challenged"));
            return;
        }
        Battle newBattle = new Battle(challenger, challenged, new Arena(this.getMain()));
        newBattle.init();
    }

    private void accept(Player challenged) {

    }
}
