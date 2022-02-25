package fr.altaks.helesky.listener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.CustomItems;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.levelup.LevelingItems;
import fr.altaks.helesky.core.levelup.LevelingWither;
import fr.altaks.helesky.utils.LoreUtil;

public class WitherInteractListener implements Listener {
	
	private Main main;
	
	public WitherInteractListener(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onWitherSummon(PlayerInteractEvent event) {
	
		// check le type de l'oeuf
		// si wither alors check si le player a une ile
		// si le player a une ile, check si l'ile a d�j� un wither
		// sinon ajouter le wither dans la hashmap
		
		if(event.hasItem() && event.hasBlock()) {
			ItemStack item = event.getItem();
			if(item.getType() == Material.WITHER_SKELETON_SPAWN_EGG) {
					if(!item.getItemMeta().getDisplayName().equals(CustomItems.witherSpawnEgg.getItemMeta().getDisplayName())) {
						return;
					}
					
					// check si joueur a une �le
					
					Player player = event.getPlayer();
					if(main.hasIsland(player.getUniqueId())) {
						
						event.setCancelled(true);
						Island island = main.getPlayerIsland(player.getUniqueId());
						
						if(main.hasWither(island.getId())) {
							player.sendMessage(Main.PREFIX + "Votre île possède déjà un wither !");
							event.setCancelled(true);
							return;
						}
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
							
							Location location = event.getClickedBlock().getLocation();
							Wither wither = (Wither) location.getWorld().spawnEntity(location, EntityType.WITHER);
							
							if(wither == null) {
								player.sendMessage(Main.ERROR_PREFIX + "Une erreur est survenue lors de l'apparition de votre Wither, veuillez en informer le staff");
								event.setCancelled(true);
							}
							
							// check distance entre �le et wither
							
							if((wither.getLocation().distance(island.getAnchor()) > (100 + (25 * island.getTier()) / 2))) {
								player.sendMessage(Main.PREFIX + "Vous ne pouvez pas poser de wither en dehors de votre île !");
								event.setCancelled(true);
								return;
							}

							// retirer tous les trucs chiants : bossbar, ia, bruit, d�placement, changer l'age etc
							wither.getBossBar().removeAll();
							wither.setAI(false);
							wither.setPersistent(true);
							wither.setSilent(true);
							wither.setRemoveWhenFarAway(false);
							
							// TODO : faire le wither petit 
							// modif par nms
							
							// recup la dura du wither
							int durability = 100;
							ItemMeta meta = item.getItemMeta();
							String line = meta.getLore().get(2);
							if(!line.startsWith("§6Durabilité : ")) System.out.println("Oeuf de wither invalide");
							line = line.replace("§6Durabilité : §a", "");
							
							durability = line.replace(".", "").replace("§7", "").replace("§a", "").length() * 2;
							
							// ajouter wither a la hashmap
							LevelingWither witherObj = new LevelingWither(wither.getUniqueId(), durability);
							main.getIslandIdFromWither().put(witherObj, island.getId());
							
						}, 20 * 2 /* 2 secondes */);
						player.getInventory().setItemInMainHand(null);
					} else {
						player.sendMessage(Main.PREFIX + "§cVous n'avez pas d'île, vous ne pouvez pas faire apparaître de wither !");
						event.setCancelled(true);
						return;
					}
			}
		}
	
	}
	
	@EventHandler
	public void onWitherDamageToRemove(EntityDamageByEntityEvent event) {
		
		Entity damager = event.getDamager();
		Entity victim = event.getEntity();
		
		if(damager instanceof Player && victim instanceof Wither) {
			
			LevelingWither levelingWither = null;
			for(Entry<LevelingWither, Integer> entry : main.getIslandIdFromWither().entrySet()) {
				if(entry.getKey().getWitherId().equals(victim.getUniqueId())) {
					levelingWither = entry.getKey();
					break;
				}
			}
			if(levelingWither == null) return;
			
			Player player = (Player) damager;
			if(player.getGameMode() != GameMode.SURVIVAL) return;
			if(main.hasIsland(player.getUniqueId())) {
				
				Island island = main.getPlayerIsland(player.getUniqueId());
				if(island.getOwnerId().equals(player.getUniqueId())) {
					event.setCancelled(true);
					victim.remove();
					
					ItemStack item = CustomItems.witherSpawnEgg;
					ItemMeta meta = item.getItemMeta();					
					meta.setLore(Arrays.asList(LoreUtil.getLevelingWither(levelingWither.getDurability())));
					item.setItemMeta(meta);
					
					player.getInventory().addItem(item);
					
					File file = new File(main.getIslandDirectory() + File.separator + main.getWitherIsland(levelingWither).getId() + ".yml");
					try {
						// �criture du data du wither
						FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
						yml.set("witherData", null);
						
						// sauvegarde
						yml.save(file);
						
					} catch (IOException e) {
						Main.LOGGER.warning(Main.ERROR_PREFIX + "§cUne erreur est survenue lors de la suppresion du wither de l'île §6#" + main.getWitherIsland(levelingWither).getId());
						e.printStackTrace();
					}
					
					main.getIslandIdFromWither().remove(levelingWither);
					
				} else {
					player.sendMessage(Main.PREFIX + "§cVous ne pouvez pas retirer le wither d'amélioration si vous n'êtes pas le propriétaire de l'île !");
					event.setCancelled(true);
				}
				
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onWitherGiveOre(PlayerInteractAtEntityEvent event) {
		
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if(entity instanceof Wither) {
			
			// check si l'item est une leveling item
			if(player.getInventory().getItemInMainHand() != null) {
				
				LevelingItems item = LevelingItems.matchesItem(player.getItemInHand());
				int amount = player.getItemInHand().getAmount();
				if(item == null) return;
				
				Wither wither = (Wither) entity;
				// obtenir le leveling wither peu importe l'ile
				LevelingWither levelingWither = null;
				for(Entry<LevelingWither, Integer> entry : main.getIslandIdFromWither().entrySet()) {
					if(entry.getKey().getWitherId().equals(wither.getUniqueId())) {
						levelingWither = entry.getKey();
						break;
					}
				}
				if(levelingWither == null) return;
				// r�cup l'ile et check si l'add xp va passer un level
				
				Island island = main.getWitherIsland(levelingWither);

				// ajouter l'xp a l'�le
				if(island.itemWillMakeLevelUp(item, amount)) {
					main.getIslandIdFromWither().remove(levelingWither);
					levelingWither.setDurability(levelingWither.getDurability() - 1);
					if(levelingWither.getDurability() == 0 || levelingWither.getDurability() < 1) {
						// TODO : faire mourir le wither avec animation
						Bukkit.getEntity(levelingWither.getWitherId()).remove();
						main.getIslandIdFromWither().remove(levelingWither);
						player.sendMessage(Main.PREFIX + "Votre wither a disparu suite a un usage intensif (100 level-ups)");
					}
					main.getIslandIdFromWither().put(levelingWither, island.getId());
				}
				island.addXp(item.getXp() * amount);
				
				player.getInventory().setItemInMainHand(null);
			}
		}
		
	}

}
