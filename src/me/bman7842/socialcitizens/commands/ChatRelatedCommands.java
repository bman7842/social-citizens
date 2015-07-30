package me.bman7842.socialcitizens.commands;

import me.bman7842.socialcitizens.Main;
import me.bman7842.socialcitizens.data.StoredData;
import me.bman7842.socialcitizens.utils.Messages;
import me.bman7842.socialcitizens.utils.SoundEffects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatRelatedCommands implements CommandExecutor{

	Main main;
	
	public ChatRelatedCommands(Main main)
	{
		this.main = main;
	}
	//Done
	
	/*
	 * TODO: Work on adding better options for /send currently when the user does not type in a name it does nothing!
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to perform this command!");
			return false;
		}

		Player p = (Player)sender;
		
		if (cmd.getName().equalsIgnoreCase("news")) {
        	if (args.length >= 1) {
				if (args[0] == "set") {
					if (!p.hasPermission("sc.news.set")) {
						Messages.sendNoPerms(p.getUniqueId());
						return false;
					}

					if (StoredData.isNewsEnabled() == false) {
						Messages.sendErrorMessage(p.getUniqueId(), "The news is disabled, enable it in the config file!");
						return false;
					}

					String news = "";
					for(int i = 1; i < args.length; i++) {
						news = news + " " + args[i];
					}
					StoredData.setNews(news);
					Messages.sendAlertMessage(p.getUniqueId(), "You have successfully set the news to " + ChatColor.
							translateAlternateColorCodes('&', news));
					Messages.logEvent(p, "has changed the news");
					return true;
				} else {
					Messages.sendErrorMessage(p.getUniqueId(), "Invalid usage, " + args[0] + " is not a valid sub command of news!");
					Messages.sendAlertMessage(p.getUniqueId(), "Try /news set (message) to set the news or /news to view the news!");
					return false;
				}
			} else {
				sendNews(p.getUniqueId());
			}
			return true;
        }
		
		if (cmd.getName().equalsIgnoreCase("shout")) {
        	if(!p.hasPermission("sc.shout")) {
				Messages.sendNoPerms(p.getUniqueId());
				return false;
			}
			if (args.length == 0) {
				Messages.sendErrorMessage(p.getUniqueId(), "Invalid usage, try /shout (message)");
			} else {
				String msg = (ChatColor.GREEN + "" + ChatColor.BOLD + "(" + p.getDisplayName() + " shouts): " + ChatColor.GRAY);
				for(int i = 0; i < args.length; i++) {
					msg = msg + " " + args[i];
				}
				for (Player player : Bukkit.getOnlinePlayers()) {
					SoundEffects.playMessageSound(player);
				}
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
				Messages.logEvent(p, " has shouted a message");
			}

		}
		
		if (commandLabel.equalsIgnoreCase("cc")) {
			if (sender.hasPermission("sc.cc")) {
				Bukkit.broadcastMessage(ChatColor.WHITE + 
				          "a                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                " + 
				          ChatColor.AQUA + "  The chat was cleared by " + 
				          ChatColor.RED + p.getName());
				for (Player players : Bukkit.getOnlinePlayers()) {
					Location locations = players.getLocation();
			        players.getLocation().getWorld()
			            .playSound(locations, Sound.ANVIL_BREAK, 1.0F, 1.0F);
			    }
				Messages.logEvent(p, " cleared the chat");
			}
		}
		
		return false;
    }

	public void sendNews(UUID player) {
		Player p = Bukkit.getPlayer(player);
		p.sendMessage
				(ChatColor.BLUE + "" + ChatColor.BOLD + "================" + ChatColor.WHITE + "" + ChatColor.BOLD + "NEWS" + ChatColor
							.BLUE + "" + ChatColor.BOLD + "================");
		p.sendMessage(StoredData.getNews());
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', StoredData.getNews()));
		p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "====================================");
		SoundEffects.playMessageSound(p);
	}
}
