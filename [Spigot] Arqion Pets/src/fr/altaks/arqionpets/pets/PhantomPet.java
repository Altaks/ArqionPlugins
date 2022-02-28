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

public class PhantomPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	public PhantomPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		File file = new File(main.getDataFolder() + File.separator + "phantom_pet_owners.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getPetFileName() {
		return "phantom_pet_owners";
	}

	@Override
	public void init() {

        new BukkitRunnable(){

            @Override
            public void run(){

                for(Player player : this.players_who_enabled){
                    if(this.pets_rarity.get(player.getUniqueId()) != PetRarity.LEGENDARY) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability(elytras.getDurability() + 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));
                    }

                }

            }


        }.runTaskTimer(main, 0, 2 * 20l); // 1dura / 2s -> legendary 

        new BukkitRunnable(){

            @Override
            public void run(){

                for(Player player : this.players_who_enabled){
                    if(this.pets_rarity.get(player.getUniqueId()) != PetRarity.EPIC) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability(elytras.getDurability() + 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));

                    }

                }

            }


        }.runTaskTimer(main, 0, 4 * 20l); // 1dura / 2s -> legendary

        new BukkitRunnable(){

            @Override
            public void run(){

                for(Player player : this.players_who_enabled){
                    
                    if(this.pets_rarity.get(player.getUniqueId()) != PetRarity.RARE) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability(elytras.getDurability() + 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));

                    }

                }

            }


        }.runTaskTimer(main, 0, 10 * 20l); // 1dura / 2s -> legendary 

    }

	@Override
	public String getHeadName() {
		return PluginItems.chicken_pet.getItemMeta().getDisplayName();
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