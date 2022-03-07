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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class SlimePet implements EquipablePet {
	
	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	private File file;
	private FileConfiguration yml;
	
	public SlimePet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		file = new File(main.getDataFolder() + File.separator + "slime_pet_owners.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		yml = YamlConfiguration.loadConfiguration(file);
		
		loadPetList();
		
	}
	
	public String getPetFileName() {
		return "slime_pet_owners";
	}

	@Override
	public void init() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(Player player : players_who_enabled) {
					
					PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());
					
					switch (rarity) {
						case LEGENDARY:
						case EPIC:
							if(!player.hasPotionEffect(PotionEffectType.JUMP)) 	player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1_000_000, 2));
							return;
						case RARE:
						case COMMON:
							if(!player.hasPotionEffect(PotionEffectType.JUMP)) 	player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1_000_000, 1));
							return;
					default:
						return;
					}
					
				}
				
			}
		}.runTaskTimer(main, 0, 20l);
	}

	@Override
	public String getHeadName() {
		return PluginItems.slime_pet.getItemMeta().getDisplayName();
	}

	@Override
	public boolean isListener() {
		return false;
	}
	
	@Override
	public void enablePetForPlayer(Player player) {
		if(pets_rarity.containsKey(player.getUniqueId()) || Main.debugMode) {
			// le joueur possède le pet
			
			players_who_enabled.add(player);
			player.sendMessage(Main.PREFIX + "§eVous venez d'équiper votre slime");
			
		}
	}
	
	@Override
	public void disablePetForPlayer(Player player) {
		if(players_who_enabled.contains(player) || Main.debugMode) {
			// faire en sorte que le joueur déséquipe son pet
			players_who_enabled.remove(player);
			player.sendMessage(Main.PREFIX + "§eVous venez de déséquiper votre pet");
			
			if(player.hasPotionEffect(PotionEffectType.JUMP)) 	player.removePotionEffect(PotionEffectType.JUMP);
		}
	}
	
	public boolean playerHasPet(UUID id) {
		return this.pets_rarity.keySet().contains(id);
	}
	
	public void loadPetList() {
		for(String uuidPath : yml.getKeys(false)) {
			this.pets_rarity.put(UUID.fromString(uuidPath), PetRarity.fromString(yml.getString(uuidPath)));
		}
	}
	
	@Override
	public void addPetForPlayer(Player player, PetRarity rarity) {
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet slime !");
		yml.set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	@Override
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez de perdre le pet slime !");
		yml.set(player.getUniqueId().toString(), null);
		saveYml();
	}
	
	public void saveYml() {
		try {
			yml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
