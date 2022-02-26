package fr.altaks.arqionpets.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.altaks.arqionpets.Main;

public abstract class EquipablePet implements Listener {
	
	protected Main main;
	
	public EquipablePet(Main main) {
		this.main = main;
	}
	
	/**
	 * Correspond a la liste des joueurs ayant le pet activé
	 */
	protected List<Player> players_who_enabled = new ArrayList<Player>();
	
	protected HashMap<UUID, PetRarity> pets_rarity = new HashMap<>();
	
	public void enablePetForPlayer(Player player) {
		if(pets_rarity.containsKey(player.getUniqueId())) {
			// le joueur possède le pet
			
			this.players_who_enabled.add(player);
			player.sendMessage(Main.PREFIX + "§eVous venez d'équiper votre pet");
			
		}
	}
	
	public void disablePetForPlayer(Player player) {
		if(players_who_enabled.contains(player)) {
			// faire en sorte que le joueur déséquipe son pet
			this.players_who_enabled.remove(player);
			player.sendMessage(Main.PREFIX + "§eVous venez de déséquiper votre pet");
		}
	}
	
	public void loadPetList() {
		for(String uuidPath : getYml().getKeys(false)) {
			this.pets_rarity.put(UUID.fromString(uuidPath), PetRarity.fromString(getYml().getString(uuidPath)));
		}
	}
	
	public void addPetForPlayer(Player player, PetRarity rarity) {
		getYml().set(player.getUniqueId().toString(), rarity.id);
		saveYml();
	}
	
	public void removePetForPlayer(Player player) {
		getYml().set(player.getUniqueId().toString(), null);
		saveYml();
	}
	
	public File getFile() {
		return new File(main.getDataFolder() + File.separator + getPetFileName() + ".yml");
	}
	
	public FileConfiguration getYml() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void saveYml() {
		try {
			getYml().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract String getPetFileName();
	
	public abstract int getMenuSlot();
	
	/**
	 * Mettre en place le BukkitRunnable correspondant au fonctionnement du pet
	 */
	public abstract void init();
	
	public abstract String getHeadName();
	
	public abstract boolean isListener();
	
	public static enum PetRarity {
		
		COMMON("§2[Commun]"), 
		RARE("§9[Rare]"),
		EPIC("§5[Épique]"),
		LEGENDARY("§6[Légendaire]");
		
		private String id, label;
		
		private PetRarity(String label) {
			this.id = this.toString().toLowerCase();
			this.label = label;
		}
		
		public static PetRarity fromString(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.id.equals(value)) return rarity;
			return PetRarity.COMMON;
		}
		
		public static PetRarity fromLabel(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.label.equals(value)) return rarity;
			return PetRarity.COMMON;
		}
		
	}
}