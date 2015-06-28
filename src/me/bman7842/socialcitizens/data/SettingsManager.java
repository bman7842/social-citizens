package me.bman7842.socialcitizens.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by brand_000 on 5/17/2015.
 */
public class SettingsManager {

    private SettingsManager() { } //Cannot make a new instance of this class

    static SettingsManager instance = new SettingsManager(); //Making the only instance of this class that can be access by other classes

    public static SettingsManager getInstance() //A method so that you can get access to the one intance of this class
    {
        return instance;
    }

    Plugin p;
    FileConfiguration config;
    File cfile;

    public void setup(Plugin p)
    {
        config = p.getConfig(); //Creating config object, saying it is the config of this plugin
        config.options().copyDefaults(true); //Copying the default file you have set
        cfile = new File(p.getDataFolder(), "config.yml"); //Finding the actual file.
        saveConfig(); //Then it's saving or making the configuration file
    }

    public FileConfiguration getConfig() //Giving you access to the configuration object
    {
        return config;
    }

    public void saveConfig()
    {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc()
    {
        return p.getDescription();
    }
}
