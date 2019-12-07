package de.realmeze.model;


import org.bukkit.inventory.Inventory;

public class Kit {
	private String name;
	private Inventory inventory;

	public Kit(String name, Inventory inventory) {
		this.name = name;
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public Inventory getInventory() {
		return inventory;
	}

}
