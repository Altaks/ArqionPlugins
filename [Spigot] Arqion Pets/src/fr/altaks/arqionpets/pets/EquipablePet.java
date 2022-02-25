package fr.altaks.arqionpets.pets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import fr.altaks.arqionpets.Main;

public abstract class EquipablePet implements Listener {
	
	protected List<Player> player_has_equiped = new ArrayList<Player>();
	protected HashMap<UUID, PetRarity> pet_rarity_by_player = new HashMap<UUID, PetRarity>();
	
	protected String petname;
	protected ItemStack associated_skull;
	
	protected File file;
	protected FileConfiguration yml;
	
	private Main main;
	
	public EquipablePet(Main main, String petname, ItemStack associated_skull) {
		this.petname = petname;
		this.associated_skull = associated_skull;
		this.main = main;
		
		this.file = new File(main.getDataFolder() + File.separator + petname + ".yml");
		this.yml = YamlConfiguration.loadConfiguration(file);
	}
	
	public void loadPlayerInfos() {
		for(String uuid : yml.getKeys(false)) {
			PetRarity rarity = PetRarity.COMMUN;
			for(PetRarity r : PetRarity.values()) {
				if(yml.getString(uuid).equals(r.toString())) {
					rarity = r;
					break;
				}
			}
			this.pet_rarity_by_player.put(UUID.fromString(uuid), rarity);
			
		}
	}
	
	public void savePetFile() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void playerGetPet(Player player, PetRarity rarity) {
		
	}
	
	public void playerLoosePet(Player player) {
		
	}
	
	public void equip(Player player) {
		this.player_has_equiped.add(player);
	}
	
	abstract void init();
	
	public void desequip(Player player) {
		this.player_has_equiped.remove(player);
	}
	
	public String getPetName() {
		return this.petname;
	}
	
	public String getSkull() {
		return this.getSkull();
	}

	
	public static enum PetRarity {
		
		COMMUN("§2[Commun]"), 
		RARE("§9[Rare]"), 
		EPIC("§5[Épique]"), 
		LEGENDARY("§6[Légendaire]");
		
		private String label;
		
		private PetRarity(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
		
	}
	
	
	
}