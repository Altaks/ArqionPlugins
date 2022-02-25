package fr.altaks.heleswitcher;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Servers {
	
	Lobby("lobby", ItemStackBuilder.buildItemStack(Material.BEDROCK, "Lobby", new ArrayList<String>(Collections.singletonList("§eCliques ici pour accéder au Lobby !")))),
	Server1("server-1", ItemStackBuilder.buildItemStack(Material.GRASS_BLOCK, "Lobby",new ArrayList<String>(Collections.singletonList("§eCliques ici pour accéder au Serveur 1!"))));
	
	private String id;
	private ItemStack symbol;
	
	private Servers(String id, ItemStack symbol) {
		this.id = id;
		this.symbol = symbol;
	}

	public String getId() {
		return id;
	}

	public ItemStack getSymbol() {
		return symbol;
	}

}
