package me.bman7842.socialcitizens.commands;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import me.bman7842.socialcitizens.Main;
import me.bman7842.socialcitizens.data.StoredData;
import me.bman7842.socialcitizens.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by brand_000 on 7/30/2015.
 */
public class pokeCommands implements CommandExecutor {

    Main main;

    HashMap<UUID, Integer> pokeDelay = new HashMap<UUID, Integer>();
    HashMap<UUID, UUID> pokeBack = new HashMap<UUID, UUID>(); // one who can poke back, one who poked

    public pokeCommands(Main main) {
        this.main = main;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                for (UUID player : pokeDelay.keySet()) {
                    if (pokeDelay.get(player) == 0) {
                        pokeDelay.remove(player);
                    } else {
                        pokeDelay.put(player, (pokeDelay.get(player)-1));
                    }
                }
            }
        },20L,20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a command for players!");
            return false;
        }

        Player p  = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("poke")) {
            if (!p.hasPermission("sc.poke")) {
                Messages.sendNoPerms(p.getUniqueId());
                return false;
            }

            if (args.length == 0) {
                Messages.sendErrorMessage(p.getUniqueId(), "Invalid usage, /poke [name]");
                return false;
            }

            if (pokeDelay.containsKey(p.getUniqueId())) {
                Messages.sendErrorMessage(p.getUniqueId(), "You must wait " + pokeDelay.get(p.getUniqueId()) + " second(s) before you can poke someone.");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                Messages.sendErrorMessage(p.getUniqueId(), "There is no player online with this name!");
                return false;
            }

            Player targetp = Bukkit.getPlayer(args[0]);

            if (targetp.getUniqueId().equals(p.getUniqueId())) {
                Messages.sendErrorMessage(p.getUniqueId(), "You can't poke yourself, that's just weird...");
                return false;
            }

            if (pokeBack.containsKey(p.getUniqueId())) {
                if (pokeBack.get(p.getUniqueId()).equals(targetp.getUniqueId())) {
                    Messages.sendAlertMessage(targetp.getUniqueId(), p.getName() + " has poked you back!");
                    Messages.sendAlertMessage(p.getUniqueId(), "You poked " + targetp.getName() + " back!");

                    pokeDelay.put(p.getUniqueId(), StoredData.getPokeDelayTime());
                    pokeBack.remove(p.getUniqueId());
                }
            } else {
                Messages.sendAlertMessage(targetp.getUniqueId(), p.getName() + " has poked you! Type /poke " + p.getName() + " to poke them back!");
                Messages.sendAlertMessage(p.getUniqueId(), "You have poked " + targetp.getName());

                pokeDelay.put(p.getUniqueId(), StoredData.getPokeDelayTime());
                pokeBack.put(targetp.getUniqueId(), p.getUniqueId());
            }
            return true;
        }

        return false;
    }

}
