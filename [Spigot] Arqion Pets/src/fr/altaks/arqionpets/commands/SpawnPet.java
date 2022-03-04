package fr.altaks.arqionpets.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class SpawnPet implements TabExecutor {
	
	private Main main;
	
	public SpawnPet(Main main) {
		this.main = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return Arrays.asList("enderdrag");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnpet") && sender instanceof Player && args.length > 0) {
			
			String petname = args[0];
			
			Player player = (Player)sender;
			ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(1.5d,-0.4d,1.5d), EntityType.ARMOR_STAND);
			
			armorstand.setGravity(false);
			armorstand.setCustomName("�5\u00BB Enderdragon \u00AB");
			armorstand.setCustomNameVisible(true);
			armorstand.setBasePlate(false);
			armorstand.setInvulnerable(true);
			armorstand.setCollidable(false);
			armorstand.setVisible(false);
			
			switch (petname) {
				case "enderdrag":
					armorstand.setHelmet(PluginItems.ender_drag_pet);
					break;
			default:
				armorstand.remove();
				return false;
			}
			
			main.getPetsArmorstand().addLast(new PetPlayerCouple(player, armorstand));
			main.getHasPetEquiped().add(player);
			player.sendMessage(Main.PREFIX + "§6Pet spawn§r : §ee" + petname);
			return true;
		}
		return false;
	}
	
	public static class PetPlayerCouple {
		
		private Player player;
		private ArmorStand stand;
		
		public PetPlayerCouple(Player player, ArmorStand stand) {
			this.player = player;
			this.stand = stand;
		}

		public Player getPlayer() {
			return player;
		}

		public ArmorStand getStand() {
			return stand;
		}
		
	}

}