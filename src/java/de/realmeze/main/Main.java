package de.realmeze.main;

import de.realmeze.command.KitCommand;
import de.realmeze.command.KitMgrCommand;
import de.realmeze.service.KitRegister;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin  {

	private KitRegister kitRegister;

	@Override
	public void onEnable() {
		this.kitRegister = new KitRegister(this);
		this.getCommand("Kit").setExecutor(new KitCommand(getRegister()));
		this.getCommand("KitMgr").setExecutor(new KitMgrCommand(getRegister()));
	}

	@Override
	public void onDisable() {
		getRegister().saveKits();
	}

	private KitRegister getRegister(){
		return kitRegister;
	}

}
