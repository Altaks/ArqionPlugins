package fr.altaks.arqionpets.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.pets.EquipablePet;
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

			inv.setItem(29, setLoreToPetAccordingToOwning(PluginItems.bat_pet, player));
			inv.setItem(30, setLoreToPetAccordingToOwning(PluginItems.silverfish_pet, player));
			inv.setItem(31, setLoreToPetAccordingToOwning(PluginItems.parrot_pet, player));
			inv.setItem(32, setLoreToPetAccordingToOwning(PluginItems.phantom_pet, player));
			inv.setItem(33, setLoreToPetAccordingToOwning(PluginItems.slime_pet, player));
			inv.setItem(39, setLoreToPetAccordingToOwning(PluginItems.pig_pet, player));
			inv.setItem(40, setLoreToPetAccordingToOwning(PluginItems.ender_drag_pet, player));
			inv.setItem(41, setLoreToPetAccordingToOwning(PluginItems.chicken_pet, player));
			
			player.openInventory(inv);
			return true;
			
		}
		return false;
	}

	public ItemStack setLoreToPetAccordingToOwning(ItemStack item, Player player){
		EquipablePet associatedPet = main.getPets_from_name().get(item.getItemMeta().getDisplayName());
		return new ItemManager.ItemBuilder(item.clone())
				.setLore( 
					associatedPet.playerHasPet( player.getUniqueId()) ? "§a \u2713 Possédé" : "§c\u2716 Non possédé"
				)
				.build();
	}

}
