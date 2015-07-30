package me.bman7842.socialcitizens;
 
import me.bman7842.socialcitizens.commands.ChatRelatedCommands;
import me.bman7842.socialcitizens.commands.GameChangingCommands;
import me.bman7842.socialcitizens.commands.pokeCommands;
import me.bman7842.socialcitizens.data.StoredData;
import me.bman7842.socialcitizens.listener.ChatEvent;
import me.bman7842.socialcitizens.utils.ChatAbuseCheck;
import me.bman7842.socialcitizens.data.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    
    //Recieve data from other classes
    GameChangingCommands gamechangingcmds;
    ChatRelatedCommands chatcmds;
    ChatEvent cEvent;
    pokeCommands pokecmd;

    //SettingsManager\\
    SettingsManager settings;

    public void onEnable() {
        //Config File setup and loading\\
        loadConfigData();

    	// Configuring Classes/Gaining Access to them:
    	gamechangingcmds = new GameChangingCommands(this);
    	chatcmds = new ChatRelatedCommands(this);
        cEvent = new ChatEvent();
        pokecmd = new pokeCommands(this);

    	//Reigstering Events\\
    	PluginManager pm = this.getServer().getPluginManager();
    	pm.registerEvents(gamechangingcmds, this);
    	pm.registerEvents(cEvent, this);

    	//Registering Commands\\
    	getCommand("cc").setExecutor(chatcmds);
    	getCommand("shout").setExecutor(chatcmds);
    	getCommand("news").setExecutor(chatcmds);
    	getCommand("trade").setExecutor(gamechangingcmds);
        getCommand("poke").setExecutor(pokecmd);

    }

    public void loadConfigData()
    {
        settings = SettingsManager.getInstance();
        settings.setup(this);
        try {
            ChatAbuseCheck.setAntiSwearEnabled(settings.getConfig().getBoolean("anti-swear_enabled"));
        } catch (Exception e) {
            Bukkit.getLogger().warning("There is an error in your config, your anti-swear-enabled is not properly setup!");
            ChatAbuseCheck.setAntiSwearEnabled(true);
        }
        try {
            for (String word : settings.getConfig().getStringList("blockedwords")) {
                ChatAbuseCheck.addSwearWords(word);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("There is an error in your config, your blockedwords is not properly setup!");
            ChatAbuseCheck.setAntiSwearEnabled(false);
        }
        try {
            if (settings.getConfig().getBoolean("News-Enabled") == true) {
                StoredData.setNewsEnabled(true);
                StoredData.setNews(settings.getConfig().getString("current-news"));
            } else {
                StoredData.setNewsEnabled(false);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("There is an error in your config, your MOTD/News configs are not properly setup!");
            StoredData.setNewsEnabled(false);
        }
        try {
            StoredData.setPokeDelayTime(settings.getConfig().getInt("poke_delay_time_seconds"));
        } catch (Exception e) {
            Bukkit.getLogger().warning("There is an error in your config, your poke dleay time is not properly setup!");
        }
    }

    public void saveData()
    {
        //TODO: SAVA DATA METHOD
    }
}