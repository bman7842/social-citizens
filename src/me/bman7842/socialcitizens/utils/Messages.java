package me.bman7842.socialcitizens.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by brand_000 on 6/5/2015.
 */
public class Messages {

    public static void sendErrorMessage(UUID player, String error) {
        Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR: " + ChatColor.GRAY + error);
        SoundEffects.playErrorSound(Bukkit.getPlayer(player));
    }

    public static void sendAlertMessage(UUID player, String msg) {
        Bukkit.getPlayer(player).sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "ALERT: " + ChatColor.GRAY + msg);
        SoundEffects.playMessageSound(Bukkit.getPlayer(player));
    }

    public static void sendNoPerms(UUID player) {
        sendErrorMessage(player, "You do not have permission to run this command, contact an administrator if you feel this is incorrect!");
    }

    public static void logEvent(Player p, String event) {
        //TODO: Store logs in a log file, allow for access in GUI by owner
        if (!p.isOp()) {
            Bukkit.getLogger().info(p.getName() + " " + event);
        }
    }
}
