package me.bman7842.socialcitizens.commands;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import me.bman7842.socialcitizens.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by brand_000 on 6/5/2015.
 */
public class Trade implements CommandExecutor, Listener {

    private HashMap<UUID, UUID> playersTrading = new HashMap<UUID, UUID>();
    private HashMap<UUID, Integer> countDownPlayers = new HashMap<UUID, Integer>();

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        if (playersTrading.containsValue(p.getUniqueId()))
        {
            UUID keyPlayer = getKeyUserFromValueUser(p.getUniqueId());
            playersTrading.remove(keyPlayer);
            Messages.sendAlertMessage(keyPlayer, "The user " + p.getName() + " (you sent a trade request to) has left the game, trade cancelled!");
            return;
        }
        if (playersTrading.containsKey(p.getUniqueId()))
        {
            playersTrading.remove(p.getUniqueId());
            return;
        }
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event)
    {
        //TODO: When user closes inventory is cancels trade.
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("trade"))
        {
            if (args.length == 0)
            {
                if (playersTrading.containsValue(p.getUniqueId()))
                {
                    Messages.sendErrorMessage(p.getUniqueId(), ("Invalid usage, type /trade (accept or decline) to accept or decline the trade from " + Bukkit.getPlayer(getKeyUserFromValueUser(p.getUniqueId())).getName()));
                } else {
                    Messages.sendErrorMessage(p.getUniqueId(), "Invalid usage, type /trade (player) to trade with a player");
                }
                return false;
            }

            if (!p.hasPermission("sc.trade"))
            {
                Messages.sendNoPerms(p.getUniqueId());
                return false;
            }

            String subCommand = args[0];
            Player tradingWith;
            try {
                tradingWith = Bukkit.getPlayer(subCommand);
            } catch (Exception e) {
                if (subCommand == "accept")
                {
                    if (playersTrading.containsValue(p.getUniqueId()))
                    {
                        Player tradewith = Bukkit.getPlayer(getKeyUserFromValueUser(p.getUniqueId()));
                        playersTrading.remove(tradewith.getUniqueId());
                        Messages.sendAlertMessage(tradewith.getUniqueId(), (p.getName() + " has declined your trade request!"));
                        Messages.sendAlertMessage(p.getUniqueId(), "You have successfully declined " + tradewith.getName() + "'s trade request!");
                        return false;
                    } else {
                        Messages.sendErrorMessage(p.getUniqueId(), "No one has invited you to a trade!");
                        return false;
                    }
                } else if (subCommand == "decline" || subCommand == "deny") {
                    if (playersTrading.containsValue(p.getUniqueId()))
                    {
                        openTradeGUI(Bukkit.getPlayer(getKeyUserFromValueUser(p.getUniqueId())), p);
                        return true;
                    } else {
                        Messages.sendErrorMessage(p.getUniqueId(), "No one has invited you to a trade!");
                        return false;
                    }
                } else {
                    Messages.sendErrorMessage(p.getUniqueId(), "Invalid argument, it must be a valid player or true/false");
                    return false;
                }
            }

            playersTrading.put(p.getUniqueId(), tradingWith.getUniqueId());
            Messages.sendAlertMessage(p.getUniqueId(), "You successfully sent a trade request to " + tradingWith.getName() + ", he has 60 seconds to accept or deny!");
            Messages.sendAlertMessage(tradingWith.getUniqueId(), (p.getName() + " has invited you to a trade, you have 60 seconds to reply. Type /trade accept or /trade decline"));
            //TODO: Add countdown with repeated task, make sure to have other areas remove from countdown at proper time.
        }

        return false;
    }

    public void openTradeGUI(Player playerOne, Player playerTwo)
    {

    }

    public UUID getKeyUserFromValueUser(UUID valueUser)
    {
        for (UUID player : playersTrading.keySet())
        {
            if (playersTrading.get(player) == valueUser)
            {
                return player;
            }
        }
        return null;
    }
}
