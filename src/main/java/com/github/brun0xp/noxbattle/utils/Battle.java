package com.github.brun0xp.noxbattle.utils;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Battle {

    private static Set<Battle> battles = new HashSet<>();
    private final Player challenger;
    private final Player challenged;
    private final Arena arena;

    public Battle(Player challenger, Player challenged, Arena arena) {
        this.challenger = challenger;
        this.challenged = challenged;
        this.arena = arena;
        battles.add(this);
    }

    public void init() {
        //Contagem do convite
    }

    public static boolean isBattling(Player player) {
        for (Battle battle : battles) {
            if (battle.getChallenger().equals(player) || battle.getChallenged().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
