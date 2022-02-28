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
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class ChickenPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	public ChickenPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		File file = new File(main.getDataFolder() + File.separator + "chicken_pet_owners.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getPetFileName() {
		return "chicken_pet_owners";
	}

	@Override
	public void init() {};

    @EventHandler
    public void onPlayerTakeFallDamage(EntityDamageEvent event){

        // l'entité est un joueur et il s'agit de dégâts de chute
        if(event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL){

            Player player = (Player) event.getEntity();
            if(this.players_who_enabled.contains(player)){

                // le joueur a activé son pet
                PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());
                
                switch(rarity){
                    case LEGENDARY:
                        event.setCancelled(true);
                        return;
                    case EPIC:
                        event.setDamage(event.getDamage() * 0.2);
                        return;
                    case RARE:
                        event.setDamage(event.getDamage() * 0.4);
                        return;
                    case COMMON:
                        event.setDamage(event.getDamage() * 0.7);
                        return;
                }

            }

        }


    }
   

	@Override
	public String getHeadName() {
		return PluginItems.chicken_pet.getItemMeta().getDisplayName();
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
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet poulet !");
		getYml().set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez de perdre votre poulet !");
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