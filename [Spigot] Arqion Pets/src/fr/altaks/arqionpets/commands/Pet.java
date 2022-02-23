package fr.altaks.arqionpets.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.utils.ItemManager;

public class Pet implements CommandExecutor {

	private Main main;
	
	public Pet(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pet") && sender instanceof Player) {

			Player player = (Player) sender;

			Inventory inv = Bukkit.createInventory(null, 6 * 9, "§8Pets \u00BB");

			for(int i = 0; i < inv.getSize(); i++) inv.setItem(i,ItemManager.PrebuiltItems.inventoryFillingGlassPane);

			if(main.getHasPetEquiped().contains(player)) inv.setItem(13, new ItemManager.ItemBuilder(Material.LIME_DYE, 1, "§cPet équipé").build());

			inv.setItem(29, PluginItems.bat_pet);
			inv.setItem(30, PluginItems.silverfish_pet);
			inv.setItem(31, PluginItems.parrot_pet);
			inv.setItem(32, PluginItems.phantom_pet);
			inv.setItem(33, PluginItems.slime_pet);
			inv.setItem(39, PluginItems.pig_pet);
			inv.setItem(40, PluginItems.ender_drag_pet);
			inv.setItem(41, PluginItems.chicken_pet);
			
			player.openInventory(inv);
			return true;
			
		}
		return false;
	}

}
