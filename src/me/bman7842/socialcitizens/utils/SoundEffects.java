package me.bman7842.socialcitizens.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 6/5/2015.
 */
public class SoundEffects {

    public static void playTradeOpenSound(Player p)
    {
        Location location = p.getLocation();
        p.getLocation().getWorld()
                .playSound(location, Sound.CHEST_OPEN, 1.0F, 1.0F);
    }

    public static void playTradeCloseSound(Player p)
    {
        Location location = p.getLocation();
        p.getLocation().getWorld()
                .playSound(location, Sound.CHEST_CLOSE, 1.0F, 1.0F);
    }

    public static void playMessageSound(Player p)
    {
        Location location = p.getLocation();
        p.getLocation().getWorld()
                .playSound(location, Sound.ARROW_HIT, 1.0F, 1.0F);
    }

    public static void playErrorSound(Player p)
    {
        Location location = p.getLocation();
        p.getLocation().getWorld()
                .playSound(location, Sound.GHAST_SCREAM, 1.0F, 1.0F);
    }
}
