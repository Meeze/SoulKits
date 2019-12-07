package de.realmeze.service;

import de.realmeze.controller.KitController;
import de.realmeze.model.Kit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KitRegister {

	private JavaPlugin plugin;
	private ArrayList<KitController> kits;

	//auto init kitlist from config
	public KitRegister(JavaPlugin plugin) {
		this.plugin = plugin;
		this.kits = new ArrayList<KitController>();
		initKits();

	}

	//initalizes the kits.yml file containing all kitnames
	private void initKits(){
		File file = new File(plugin.getDataFolder(), "kits.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(config.getList("kits") != null){
			ArrayList<String> configKits = (ArrayList<String>) config.getList("kits");
			for (String configKit: configKits) {
				loadFromConfig(configKit);
			}
		}
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public ArrayList<KitController> getKits() {
		return kits;
	}

	//builds clickable message for all kits
	public void showKits(Player player){
		ComponentBuilder cb = new ComponentBuilder("Kits -> ");
		for (KitController kit: getKits()) {
			String name = kit.getName();
			cb.append(name + ", ").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + name));
		}
		player.spigot().sendMessage(cb.create());
	}

	/**
	 *
	 * @param name kitname
	 * @return null | KitController
	 */
	public KitController findByName(String name){
		for (KitController kit: getKits()) {
			if(kit.getName().equals(name)){
				return kit;
			}
		}
		return null;
	}

	/**
	 *
	 * @param kit kit model
	 * @return created KitController
	 */
	public KitController createKit(Kit kit){
		KitController kitController = new KitController(kit, getPlugin());;
		getKits().add(kitController);
		registerInConfig(kitController);
		kitController.save();
		return kitController;
	}

	/**
	 * registers in kits.yml
	 * @param kitController to register
	 */
	private void registerInConfig(KitController kitController){
		File file = new File(plugin.getDataFolder(), "kits.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ArrayList<String> registeredKits = new ArrayList<>();
		if(config.getList("kits") != null){
			registeredKits = (ArrayList<String>) config.getList("kits");
		}
		registeredKits.add(kitController.getName());
		config.set("kits", registeredKits);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * checks if kitname exists in config and loads it
	 * @param kitToLoad
	 */
	private void loadFromConfig(String kitToLoad) {
		File file = new File(plugin.getDataFolder(), kitToLoad + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(config.get("content") != null && config.getString("name") != null){
			String kitName = config.getString("name");
			Inventory inventory = Bukkit.createInventory(null, 6*9, kitName);
			for (Object potentialItemStack: config.getList("content")) {
				if((ItemStack) potentialItemStack != null){
					inventory.addItem((ItemStack) potentialItemStack);
				}
			}
			Kit kit = new Kit(kitName, inventory);
			KitController kitController = new KitController(kit, plugin);
			getKits().add(kitController);
		}
	}

	public void saveKits() {
		for (KitController kitController : getKits()) {
			kitController.save();
		}
	}
}
