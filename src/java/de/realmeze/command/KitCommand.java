package de.realmeze.command;

import de.realmeze.controller.KitController;
import de.realmeze.service.KitRegister;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

	private KitRegister kitRegister;

	public KitCommand(KitRegister kitRegister){
		this.kitRegister = kitRegister;
	}

	public KitRegister getRegister() {
		return kitRegister;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(!(sender instanceof Player)){
			return false;
		}

		if(command.getName().equalsIgnoreCase("Kit")){
			Player player = (Player) sender;
			switch (args.length){
				case 0:
					kitRegister.showKits(player);
					return false;
				case 1:
					String kitName = args[0];
					KitController kitController = getRegister().findByName(kitName);
					if(kitController != null){
						kitController.giveToPlayer(player);
					}
			}
			return true;
		}
		return false;
	}


}
