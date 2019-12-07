package de.realmeze.controller;

import de.realmeze.model.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class KitController {

	private JavaPlugin plugin;
	private Kit kit;

	public KitController(Kit kit, JavaPlugin plugin) {
		this.kit = kit;
		this.plugin = plugin;
	}

	public Kit getKit() {
		return kit;
	}

	public String getName() {
		return getKit().getName();
	}

	public Inventory getInventory() {
		return getKit().getInventory();
	}

	public void edit(Player editor) {
		editor.openInventory(getInventory());
	}

	public void giveToPlayer(Player player) {
		for (ItemStack item : getInventory().getContents()) {
			if (item != null) {
				if (!tryAutoArmor(player, item)) {
					player.getInventory().addItem(item);
				}
			}
		}
	}

	/**
	 * saves a kitcontroller to its name getName().yml
	 */
	public void save() {
		File file = new File(plugin.getDataFolder(), getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("name", getName());
		config.set("content", getInventory().getContents());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param player
	 * @param itemStack
	 * @return true if auto armored player
	 */
	private boolean tryAutoArmor(Player player, ItemStack itemStack) {
		if (isBoot(itemStack)) {
			ItemStack currentBoots = player.getInventory().getBoots();
			if(currentBoots == null){
				player.getInventory().setBoots(itemStack);
				return true;
			}
			return false;
		}
		return false;
	}

	private boolean isBoot(ItemStack itemStack) {
		Material m = itemStack.getType();
		//i dont think this will work reliably, but its just autoarmor lmao TODO fix this
		if (m.toString().contains("BOOTS")) {
			return true;
		}
		return false;
	}
}
