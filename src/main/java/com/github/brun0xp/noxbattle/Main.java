package com.github.brun0xp.noxbattle;

import com.github.brun0xp.noxbattle.commands.BattleCommand;
import com.github.brun0xp.noxbattle.database.Database;
import com.github.brun0xp.noxbattle.database.MySQL;
import com.github.brun0xp.noxbattle.database.SQLite;
import com.github.brun0xp.noxbattle.files.LanguageFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class Main extends JavaPlugin {

    private Main main;
    private LanguageFile language;
    private Database database;

    @Override
    public void onEnable() {
        this.setMain(this);
        this.setupFiles();
        this.setupSQL();
        this.getCommand("1v1").setExecutor(new BattleCommand(this));
    }

    @Override
    public void onDisable() {

    }

    private void setupFiles() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.setLanguage(new LanguageFile(this.getMain(), "lang-" + this.getConfig().getString("language") + ".yml"));
        this.getLanguage().getFile().options().copyDefaults(true);
        this.getLanguage().saveLang();
    }

    private void setupSQL() {
        if (this.getConfig().getBoolean("mysql.enabled")) {
            String hostname = this.getConfig().getString("mysql.hostname");
            String port = this.getConfig().getString("mysql.port");
            String database = this.getConfig().getString("mysql.database");
            String username = this.getConfig().getString("mysql.username");
            String password = this.getConfig().getString("mysql.password");
            this.setDatabase(new MySQL(hostname, port, database, username, password));
        } else {
            this.setDatabase(new SQLite(this.getMain(),"noxbattle"));
        }
    }
}
