package fr.altaks.arqionpets.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.altaks.arqionpets.Main;

public class RecipeGive implements Listener {
	
	private Main main;
	
	public RecipeGive(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {		
		event.getPlayer().undiscoverRecipes(this.main.getRecipeskeys());
		event.getPlayer().discoverRecipes(this.main.getRecipeskeys());
	}

}
