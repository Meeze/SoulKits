package de.realmeze.command;

import de.realmeze.controller.KitController;
import de.realmeze.model.Kit;
import de.realmeze.service.KitRegister;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitMgrCommand implements CommandExecutor {

	private KitRegister kitRegister;

	public KitMgrCommand(KitRegister kitRegister) {
		this.kitRegister = kitRegister;
	}

	public KitRegister getRegister() {
		return kitRegister;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}

		if(command.getName().equalsIgnoreCase("KitMgr")){
			Player player = (Player) sender;
			switch (args.length){
				case 0:
					player.sendMessage("ladida");
					return false;
				case 2:
					String subCommand = args[0];
					String kitName = args[1];
 					if(subCommand.equalsIgnoreCase("create")){
 						Inventory inventoryToSave = Bukkit.createInventory(null, 6*9);
 						inventoryToSave.setContents(player.getInventory().getContents());
 						inventoryToSave.addItem(player.getInventory().getArmorContents());
 						Kit kit = new Kit(kitName, inventoryToSave);
 						KitController kitController = getRegister().createKit(kit);
 						return false;
					}

 					else if(subCommand.equalsIgnoreCase("edit")){

 						KitController kit = getRegister().findByName(kitName);
 						if(kit == null){
 							player.sendMessage("Kit existiert nicht");
 							return false;
						}
 						kit.edit(player);
					}

			}
			return true;
		}
		return false;
	}



}
