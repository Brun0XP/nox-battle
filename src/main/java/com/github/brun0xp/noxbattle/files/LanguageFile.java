package com.github.brun0xp.noxbattle.files;

import com.github.brun0xp.noxbattle.Main;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class LanguageFile extends AbstractFile{

    public LanguageFile(Main main, String resource) {
        super(main, resource);
    }

    public String getString(String path) {
        return this.replace(this.getFile().getString("prefix") + " " + this.getFile().getString(path));
    }

    public List<String> getStringList(String path){
        List<String> strings = new ArrayList<>();
        for (String str : this.getFile().getStringList(path)) {
            strings.add(this.replace(this.getFile().getString("prefix") + " " + str));
        }
        return strings;
    }

    public String replace(String message) {
        String replaced = message
                .replace("{command_help}", this.main.getConfig().getString("commands.help"))
                .replace("{command_duel}", this.main.getConfig().getString("commands.duel"))
                .replace("{command_accept}", this.main.getConfig().getString("commands.accept"))
                .replace("{command_decline}", this.main.getConfig().getString("commands.decline"))
                .replace("{command_quit}", this.main.getConfig().getString("commands.quit"))
                .replace("{command_admin}", this.main.getConfig().getString("commands.admin"));

        return ChatColor.translateAlternateColorCodes('&', replaced);
    }
}
