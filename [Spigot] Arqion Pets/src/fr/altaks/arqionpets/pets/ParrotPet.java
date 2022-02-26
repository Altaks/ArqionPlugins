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

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;

public class ParrotPet implements EquipablePet {

	private List<Player> players_who_enabled = new ArrayList<Player>();
	private HashMap<UUID, PetRarity> pets_rarity = new HashMap<UUID, EquipablePet.PetRarity>();
	
	private Main main;
	
	public ParrotPet(Main main) {
		this.main = main;
		
		// check if file exist if not, create
		File file = new File(main.getDataFolder() + File.separator + "parrot_pet_owners.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getPetFileName() {
		return "parrot_pet_owners";
	}

	@Override
	public void init() {};
	
	@EventHandler
	public void onPlayerDrinkPotionEvent(PlayerItemConsumeEvent event) {
		
		// get potion type
		
		if(event.getItem().getType() != Material.POTION) return;
		
		Player player = event.getPlayer();
		
		if(players_who_enabled.contains(player)) {
			int multiplier = 1;
			
			PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(player.getUniqueId());
			
			switch (rarity) {
				
				case LEGENDARY:
					multiplier = 10;
					break;
				case EPIC:
					multiplier = 5;
					break;
				case RARE:
					multiplier = 3;
					break;
				case COMMON:
					multiplier = 2;
					break;
				default:
					break;
			}
			
			for(PotionEffect potEffect : ((PotionMeta)event.getItem().getItemMeta()).getCustomEffects()) {
				player.removePotionEffect(potEffect.getType()); // remove effect
				player.addPotionEffect(new PotionEffect(potEffect.getType(), potEffect.getDuration() * multiplier, potEffect.getAmplifier())); // add new effect w/ coeffs in funct° of rarity
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerGetsAttacked(EntityDamageByEntityEvent event) {
		
		if(!(event.getDamager() instanceof LivingEntity) || !(event.getEntity() instanceof Player)) return;

		Player victim = (Player) event.getEntity();
		LivingEntity attacker = (LivingEntity) event.getDamager();
		
		if(this.players_who_enabled.contains(victim)) {
			
			PetRarity rarity = Main.debugMode ? PetRarity.LEGENDARY : pets_rarity.get(victim.getUniqueId());

			switch (rarity) {
				case LEGENDARY:
					attacker.damage(2.5);
					attacker.sendMessage("[Perroquet] \u00BB Raaaaouuul pas c-conteeeent !");
					victim.sendMessage(Main.PREFIX + "§eVotre perroquet vous a vengé §7(2.5 \u2665)");
					break;
				case EPIC:
					attacker.damage(1.75);
					attacker.sendMessage("[Perroquet] \u00BB Poc poc perrroqueeeet !");
					victim.sendMessage(Main.PREFIX + "§eVotre perroquet vous a vengé §7(1.75 \u2665)");
					break;
				case RARE:
					attacker.damage(1);
					attacker.sendMessage("[Vous] \u00BB Ouch ma tête, saleté de perroquet !");
					victim.sendMessage(Main.PREFIX + "§eVotre perroquet vous a vengé §7(1 \u2665)");
					break;

			default:
				break;
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerHoldCookieEvent(PlayerItemHeldEvent event) {
		
		if(this.players_who_enabled.contains(event.getPlayer())) {
			
			// on recup l'item type
			if(event.getPlayer().getInventory().getItem(event.getNewSlot()) == null) return;
			if(event.getPlayer().getInventory().getItem(event.getNewSlot()).getType() == Material.COOKIE) {
				
				// le joueur perd son pet
				
				if(!Main.debugMode) {
					event.getPlayer().sendMessage("[Perroquet] \u00BB Tu ne m'aurrra pas !");
					removePetForPlayer(event.getPlayer());
				} else event.getPlayer().sendMessage(Main.PREFIX + "§c [DEBUG] \u00BB Vous venez de \"perdre\" votre perroquet");
			
			}
			
		}
		
	}

	@Override
	public String getHeadName() {
		return PluginItems.parrot_pet.getItemMeta().getDisplayName();
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
		player.sendMessage(Main.PREFIX + "§cVous venez d'obtenir le pet perroquet !");
		getYml().set(player.getUniqueId().toString(), rarity.getId());
		saveYml();
	}
	
	public void removePetForPlayer(Player player) {
		player.sendMessage(Main.PREFIX + "§cVous venez de perdre vôtre pet perroquet !");
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
