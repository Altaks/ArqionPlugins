package fr.altaks.arqionpets.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class BatPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	private File file;
	private FileConfiguration yml;
	
	public BatPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		file = new File(main.getDataFolder() + File.separator + "bat_pet_owners.yml");
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
		return "bat_pet_owners";
	}

	@Override
	public void init() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				// si on est la nuit: 
				long overworldTime = Bukkit.getWorld("world").getTime();
				if(12541 < overworldTime && overworldTime < 22925 /* aube */) {

					// calcul de nb de secondes avant le jour
					int secondsUntilDay = (int)((22925 - overworldTime) / 20);
					
					for(Player player : players_who_enabled) {
						
						PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());
						
						if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1_000_000, 255));
						
						
						switch (rarity) {
						
							case LEGENDARY:
								
								if(!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) 	player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 2));
								if(!player.hasPotionEffect(PotionEffectType.SPEED)) 			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 1));
								if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) 		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 1));

								break;
								
							case EPIC:
								
								if(!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) 	player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 2));
								if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) 		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 1));

								break;
								
							case RARE:

								if(!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) 	player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, secondsUntilDay, 1));
								break;
						default:
							break;
						}
						
					}
				}
				
			}
		}.runTaskTimer(main, 0, 20l);
	}

	@Override
	public String getHeadName() {
		return PluginItems.bat_pet.getItemMeta().getDisplayName();
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
			player.sendMessage(Main.PREFIX + "§eVous venez d'équiper votre chauve-souris");
			
		}
	}
	
	@Override
	public void disablePetForPlayer(Player player) {
		if(players_who_enabled.contains(player) || Main.debugMode) {
			// faire en sorte que le joueur déséquipe son pet
			players_who_enabled.remove(player);
			player.sendMessage(Main.PREFIX + "§eVous venez déséquiper votre pet");
			
			PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());
			
			switch (rarity) {
				case LEGENDARY:
					if(player.hasPotionEffect(PotionEffectType.SPEED)) 				player.removePotionEffect(PotionEffectType.SPEED);
				case EPIC:
					if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) 		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				case RARE:
					if(player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) 	player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				case COMMON:
					if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			default:
				return;
			}
		}
	}
	
	@Override
	public boolean playerHasPet(UUID id) {
		return this.pets_rarity.containsKey(id);
	}
	
	public void loadPetList() {
		for(String uuidPath : yml.getKeys(false)) {
			this.pets_rarity.put(UUID.fromString(uuidPath), PetRarity.fromString(yml.getString(uuidPath)));
		}
	}
	
	@Override
	public void addPetForPlayer(Player player, PetRarity rarity) {
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet chauve-souris !");
		yml.set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	@Override
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet chauve-souris !");
		yml.set(player.getUniqueId().toString(), null);
		saveYml();
	}
	
	public void saveYml() {
		if(Main.debugMode) System.out.println(file.getAbsolutePath() + "saved | " + (yml != null));
		try {
			yml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PetRarity getRarityFromPlayerPet(Player player) {
		return this.pets_rarity.get(player.getUniqueId());
	}

}
