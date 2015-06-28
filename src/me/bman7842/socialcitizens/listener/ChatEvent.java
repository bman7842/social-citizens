package me.bman7842.socialcitizens.listener;

import me.bman7842.socialcitizens.utils.ChatAbuseCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * Created by brand_000 on 6/6/2015.
 */
public class ChatEvent implements Listener {

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event)
    {
        event.setMessage(ChatAbuseCheck.removeProfanity(event.getMessage()));
        if (ChatAbuseCheck.containsProfanity(event.getMessage()))
        {
            swearMessageError(event.getPlayer().getUniqueId());
        }
    }

    public void swearMessageError(UUID player)
    {
        Bukkit.getPlayer(player).sendMessage(ChatColor.YELLOW + "===============" + ChatColor.RED + "" + ChatColor.BOLD + "WARNING" + net.md_5.bungee.api.ChatColor.YELLOW + "===============");
        Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "Please don't use rude words on the server!");
        Bukkit.getPlayer(player).sendMessage(ChatColor.YELLOW + "=====================================");
    }
}