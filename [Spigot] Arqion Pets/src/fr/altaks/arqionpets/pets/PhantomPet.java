package fr.altaks.arqionpets.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.events.PlayerKickDelayingEvent;
import fr.altaks.arqionpets.events.PlayerKickUndelayingEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PhantomPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	private File file;
	private FileConfiguration yml;
	
	public PhantomPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		file = new File(main.getDataFolder() + File.separator + "phantom_pet_owners.yml");
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
		return "phantom_pet_owners";
	}

	@Override
	public void init() {

        new BukkitRunnable(){

            @SuppressWarnings("deprecation")
			@Override
            public void run(){

                for(Player player : players_who_enabled){
                    if(pets_rarity.get(player.getUniqueId()) != PetRarity.LEGENDARY) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability((short) (elytras.getDurability() + 1));
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));
                    }

                }

            }


        }.runTaskTimer(main, 0, 2 * 20l); // 1dura / 2s -> legendary 

        new BukkitRunnable(){

            @SuppressWarnings("deprecation")
			@Override
            public void run(){

                for(Player player : players_who_enabled){
                    if(pets_rarity.get(player.getUniqueId()) != PetRarity.EPIC) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability((short) (elytras.getDurability() + 1));
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));

                    }

                }

            }


        }.runTaskTimer(main, 0, 4 * 20l); // 1dura / 2s -> legendary

        new BukkitRunnable(){

            @SuppressWarnings("deprecation")
			@Override
            public void run(){

                for(Player player : players_who_enabled){
                    
                    if(pets_rarity.get(player.getUniqueId()) != PetRarity.RARE) continue;
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA){

                        ItemStack elytras = player.getInventory().getChestplate();
                        boolean isMaxDura = elytras.getDurability() == Material.ELYTRA.getMaxDurability();
                        if(!isMaxDura) elytras.setDurability((short) (elytras.getDurability() + 1));
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.PREFIX + "§7[Phantom] Tes élytras sont mieux comme ça !"));

                    }

                }

            }


        }.runTaskTimer(main, 0, 10 * 20l); // 1dura / 2s -> legendary 

    }

	@Override
	public String getHeadName() {
		return PluginItems.phantom_pet.getItemMeta().getDisplayName();
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
			
			if(pets_rarity.get(player.getUniqueId()) != PetRarity.COMMON) {
				
				Bukkit.getPluginManager().callEvent(new PlayerKickDelayingEvent(player, pets_rarity.get(player.getUniqueId()).getAntiAfkDelay()));
				
			}
			
			player.sendMessage(Main.PREFIX + "§eVous venez d'équiper votre phantom");
			
		}
	}
	
	@Override
	public void disablePetForPlayer(Player player) {
		if(players_who_enabled.contains(player) || Main.debugMode) {
			// faire en sorte que le joueur déséquipe son pet
			players_who_enabled.remove(player);
			
			if(pets_rarity.get(player.getUniqueId()) != PetRarity.COMMON) {
				
				Bukkit.getPluginManager().callEvent(new PlayerKickUndelayingEvent(player));
				
			}
			
			player.sendMessage(Main.PREFIX + "§eVous venez déséquiper votre pet");
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
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet phantom !");
		yml.set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	@Override
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez de perdre votre phantom !");
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
	
	@Override
	public PetRarity getRarityFromPlayerPet(Player player) {
		return this.pets_rarity.get(player.getUniqueId());
	}

}