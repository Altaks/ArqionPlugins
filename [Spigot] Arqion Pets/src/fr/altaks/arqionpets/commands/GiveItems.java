package fr.altaks.arqionpets.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.utils.ItemManager;

public class GiveItems implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length <= 1) return Arrays.asList("pet_infuser", "dummy_head", "bat_pet", "silverfish_pet", "parrot_pet", "slime_pet",
				"enderdrag_pet", "pig_pet", "chicken_pet", "ores", "cables", "processor", "witherconv", "pet_core", "alim", "pet_dust").stream().filter(e -> e.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("giveitem")) {

			ItemStack item;
			switch (args[0]) {
				case "pet_infuser":
					item = PluginItems.pet_infuser;
					break;
				case "bat_pet":
					item = PluginItems.bat_pet;
					break;
				case "silverfish_pet":
					item = PluginItems.silverfish_pet;
					break;
				case "parrot_pet":
					item = PluginItems.parrot_pet;
					break;
				case "pet_core":
					item = PluginItems.pet_core;
					break;
				case "slime_pet":
					item = PluginItems.slime_pet;
					break;
				case "enderdrag_pet":
					item = PluginItems.ender_drag_pet;
					break;
				case "pig_pet":
					item = PluginItems.pig_pet;
					break;
				case "chicken_pet":
					item = PluginItems.chicken_pet;
					break;
				case "dummy_head":
					item = new ItemManager.SkullBuilder(1, "�cDummy Head").setOwner((OfflinePlayer) sender).build();
					break;
				case "ores":
					for(int i = 0; i < 16; i++) {
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.actinium_block);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.actinium_nugget);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.actinium_ingot);
						
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.copper_block);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.copper_nugget);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.copper_ingot);

						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.platinium_block);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.platinium_nugget);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.platinium_ingot);
						
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.cobalt_block);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.cobalt_nugget);
						((Player) sender).getInventory().addItem(PluginItems.CrosspluginItems.cobalt_ingot);
					}
					return true;
				case "cables":
					item = PluginItems.cable;
					break;
				case "processor":
					item = PluginItems.processor;
					break;
				case "witherconv":
					item = PluginItems.wither_pet_converter;
					break;
				case "alim":
					item = PluginItems.alimentation;
					break;
				case "pet_dust":
					item = new ItemManager.ItemBuilder(PluginItems.pet_dust.clone()).setAmount(64).build();
					break;
			default:
				sender.sendMessage("D�sol� mais votre commande est erron�e");
				return false;
			}

			((Player) sender).getInventory().addItem(item);
			return true;
		}
		return false;
	}

}
