package fr.altaks.arqionpets.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class PigPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	public PigPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		File file = new File(main.getDataFolder() + File.separator + "pig_pet_owners.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getPetFileName() {
		return "pig_pet_owners";
	}

	@Override
	public void init() {};

    private List<Material> food = Arrays.asList(Material.PORKSHOP, ... // TODO)

    @EventHandler
    public void onPlayerEatEvent(PlayerItemConsumeEvent event){

        if(event.getItem().getType().isEdible()){

            // augmenter la saturation 

            PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());

            switch(rarity){
                case EPIC:
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 5);
                    break;
                case RARE:
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 3);
                    break;
                case COMMON:
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 5);
                    break;
            }

        }

    }

	@Override
	public String getHeadName() {
		return PluginItems.pig_pet.getItemMeta().getDisplayName();
	}

	@Override
	public boolean isListener() {
		return true;
	}
	
	@Override
	public void enablePetForPlayer(Player player) {
		if(pets_rarity.containsKey(player.getUniqueId()) || Main.debugMode) {
			// le joueur possède le pet
			
			players_who_enabled.add(player);
			player.sendMessage(Main.PREFIX + "§eVous venez d'équiper votre perroquet");
			
		}
	}
	
	@Override
	public void disablePetForPlayer(Player player) {
		if(players_who_enabled.contains(player) || Main.debugMode) {
			// faire en sorte que le joueur déséquipe son pet
			players_who_enabled.remove(player);
			player.sendMessage(Main.PREFIX + "§eVous venez déséquiper votre perroquet");
		}
	}
	
	public boolean playerHasPet(UUID id) {
		return this.pets_rarity.keySet().contains(id);
	}
	
	public void loadPetList() {
		for(String uuidPath : getYml().getKeys(false)) {
			this.pets_rarity.put(UUID.fromString(uuidPath), PetRarity.fromString(getYml().getString(uuidPath)));
		}
	}
	
	public void addPetForPlayer(Player player, PetRarity rarity) {
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet cochon !");
		getYml().set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez de perdre votre cochon !");
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

}