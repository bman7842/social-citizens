package me.bman7842.socialcitizens.commands;

import java.util.ArrayList;
import java.util.HashMap;

import me.bman7842.socialcitizens.Main;
import me.bman7842.socialcitizens.utils.Messages;
import me.bman7842.socialcitizens.utils.SoundEffects;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GameChangingCommands implements Listener, CommandExecutor{

	/*
	ATTENTION, IF YOU ARE READING THIS I (BMAN7842) HAVE MADE THIS ALERT TO INFORM YOU THAT THE FOLLOWING CODE IN THIS CLASS IS OUTDATED
	AND WILL BE REMOVED IN THE NEXT VERSION OF SOCIALCITIZENS, I HIGHLY SUGGEST IGNORING WHAT YOU FIND IN HERE, IT IS VERY INACCURATE
	IF YOU WANT A BETTER IDEA OF WHAT THE NEW TRADING SYSTEM WILL BE LIKE, PLEASE REFER TO THE Trade CLASS FOUND IN THE commands PACKAGE

	                                            *this class is still being used by*
	                                          *the plugin, it will soon be replaced*
	                                                  *with the trade class*
	 */









	private Main main; // Empty variable defining main
	
	ArrayList<Player> playerstrading = new ArrayList<Player>();
	ArrayList<Player> tradetoggleoff = new ArrayList<Player>();
	HashMap<Player, Player> playerstradingwith = new HashMap<>();
	
	public GameChangingCommands(Main main) { //Constructor named after the name of the class. A consturctor executes immediatly after the object is created
	// Constructor is called when the object is created therefore in Main when it says new TradingBackEnd(this) the constructor runs!
		this.main = main; // Sets the main variable defined in this class to whatever the argument was in the class creating the object, in this case it was the class or this used in Main
	}

	/*
	 * TODO: Fix the issue allowing users to close out of their inventory and drop the item!
	 */
	
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent event)
	{
		Player p = (Player) event.getWhoClicked();
		if (playerstrading.contains(p))
		{
			if (event.isLeftClick())
			{
				if (event.getSlotType() != SlotType.OUTSIDE)
				{
					if (event.getCurrentItem().getType() != Material.AIR)
					{
						event.setCancelled(true); // Stops the event from happening minecraftwise, the code inside the event still runs but the item they clicked will be placed back into the inventory!
						Player playertradingwith = playerstradingwith.get(p);
						ItemStack Item = event.getCurrentItem();
						sendItem(Item, p, playertradingwith);
						//event.setCursor(null);
						event.setCurrentItem(null);
					} else {
						Messages.sendErrorMessage(p.getUniqueId(), "This is an invalid item, please try again!");
					}
				}
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
            Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("trade")) 
            {
            	if(p.hasPermission("sc.trade"))
            	{
            		if(playerstrading.contains(p)) 
            		{
            			playerstrading.remove(p);
						Messages.sendAlertMessage(p.getUniqueId(), "Your trade with " + playerstradingwith.get(p).getName() + " has ended!");
            			playerstradingwith.remove(p);

						SoundEffects.playTradeCloseSound(p);
            		} else {
            			if (args.length == 0) {
							Messages.sendErrorMessage(p.getUniqueId(), "Incorrect format, please try /trade (username)");
            			} else if (args.length == 1) {
            				if (args[0].equalsIgnoreCase("off"))
            				{
            					if (tradetoggleoff.contains(p))
            					{
									Messages.sendErrorMessage(p.getUniqueId(), "You already have trade mode off!");
            					} else {
            						tradetoggleoff.add(p);
            						Messages.sendAlertMessage(p.getUniqueId(), "Trade mode turned off");
            					}
            				} else if (args[0].equalsIgnoreCase("on"))
            				{
            					if (tradetoggleoff.contains(p))
            					{
            						tradetoggleoff.remove(p);
            						Messages.sendAlertMessage(p.getUniqueId(), "You have turned trade mode on");
            					} else {
									Messages.sendErrorMessage(p.getUniqueId(), "You already have trade mode on");
            					}
            				} else {
            					if (!tradetoggleoff.contains(p))
            					{
            						Player targetp = Bukkit.getPlayerExact(args[0]);
            						if (args[0].equalsIgnoreCase(p.getName())) 
            						{
            							Messages.sendErrorMessage(p.getUniqueId(), "You can't trade with yourself, lonely!");
            						} else {
            							if (targetp != null && (!tradetoggleoff.contains(targetp))) {
            								Messages.sendAlertMessage(p.getUniqueId(), "You can now trade items with " + args[0] + ", to stop the trade just type /trade again!");
            								playerstrading.add(p);
            								playerstradingwith.put(p, targetp);
            							} else {
											Messages.sendErrorMessage(p.getUniqueId(), "The user you requested is not online or has trade disabled!");
            							}
            						}
            					} else {
									Messages.sendErrorMessage(p.getUniqueId(), "You cannot trade with others when your trade mode is off!");
            					}
            				}
            			}
            		}
            	}
            }
        return true;
    }

	
	public void sendItem(ItemStack item, Player pwhosent, Player precieveingitem) {
		PlayerInventory pi = precieveingitem.getInventory();
		pi.addItem(item);
		Messages.sendAlertMessage(precieveingitem.getUniqueId(), "You recieved an item from " + pwhosent.getName());
		Messages.sendAlertMessage(pwhosent.getUniqueId(), "Your item was successfully sent to " + precieveingitem.getName());
	}
}