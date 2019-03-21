package com.github.brun0xp.noxbattle.utils;

import com.github.brun0xp.noxbattle.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Battle {

    private static Set<Battle> battles = new HashSet<>();
    private final Main main;
    private final Player challenger;
    private final Player challenged;
    private final Arena arena;
    private BukkitTask inviteExpire;

    public Battle(Main main, Player challenger, Player challenged, Arena arena) {
        this.main = main;
        this.challenger = challenger;
        this.challenged = challenged;
        this.arena = arena;
        battles.add(this);
    }

    public void init() {
        challenger.sendMessage(this.getMain().getLanguage().getString("commands.duel.invitation-sent")
                .replace("{challenged_player}", challenged.getName()));
        challenged.sendMessage(this.getMain().getLanguage().getString("commands.duel.invitation-received")
                .replace("{challenger_player}", challenger.getName()));
        this.inviteExpire = Bukkit.getScheduler().runTaskLaterAsynchronously(this.getMain(), () -> {
            challenger.sendMessage(this.getMain().getLanguage().getString("commands.duel.invitation-expired-sender")
                    .replace("{challenged_player}", challenged.getName()));
            challenged.sendMessage(this.getMain().getLanguage().getString("commands.duel.invitation-expired-receiver")
                    .replace("{challenger_player}", challenger.getName()));
            cancel();
        }, 20 * this.getMain().getConfig().getLong("battle.time.invite"));
    }

    public void cancel() {
        battles.remove(this);
    }

    public void accept() {
        this.getInviteExpire().cancel();
    }

    public static boolean isBattling(Player player) {
        return getBattle(player) != null;
    }

    public static Battle getBattle(Player player) {
        for (Battle battle : battles) {
            if (battle.getChallenger().equals(player) || battle.getChallenged().equals(player)) {
                return battle;
            }
        }
        return null;
    }
}
