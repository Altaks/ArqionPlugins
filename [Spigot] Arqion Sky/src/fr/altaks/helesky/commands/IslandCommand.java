package fr.altaks.helesky.commands;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.HeleCommand;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.islandcore.IslandTier;
import fr.altaks.helesky.utils.ItemManager;
import fr.altaks.helesky.utils.ItemManager.ItemBuilder;
import fr.altaks.helesky.utils.LoreUtil;
import fr.altaks.helesky.utils.TextComponentUtil;
import net.md_5.bungee.api.chat.TextComponent;

public class IslandCommand implements HeleCommand {
	
	private Main main;
	
	public IslandCommand(Main main) {
		this.main = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("create","upgrade","go","warp","level","top","sethome","setwarp","delwarp","rename","invite","kick","leave","setowner","delete","ban","unban","banlist","team","coop","cooplist").stream().filter(e -> e.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("island") && sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(args.length == 0) {
				
				// /is
				if(main.getIslandIDFromUUID().containsKey(player.getUniqueId()) && main.getIslandsFromId().containsKey(main.getIslandIDFromUUID().get(player.getUniqueId()))) {
					

					Island island = main.getIslandsFromId().get(main.getIslandIDFromUUID().get(player.getUniqueId()));
					Inventory inv = Bukkit.createInventory(null, 5 * 9, "�cHeleSky \u00BB");
					
					// l'ile existe et le joueur est link a une ile
					ItemStack book = new ItemManager.ItemBuilder(Material.BOOK, 1, "�cInformations").setLore(LoreUtil.getInformationBookModifiedLore(island)).build();

					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						ItemStack grass = new ItemManager.ItemBuilder(Material.GRASS_BLOCK, 1, "�3Se t�l�porter").build();
						ItemStack bed = new ItemManager.ItemBuilder(Material.RED_BED, 1, "�cD�finir le point de spawn de l��le").build();
						ItemStack goldNugget = new ItemManager.ItemBuilder(Material.GOLD_NUGGET, 1, "�cTop 5 levels").addSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
						ItemStack sign = new ItemManager.ItemBuilder(Material.OAK_SIGN, 1, "�cD�finir le point de warp de l��le").build();
						
						ItemStack head = new ItemManager.SkullBuilder(1, "�cGestion des joueurs").setOwner(player.getName()).build();

						inv.setItem(4, book);
						
						inv.setItem(20, grass);
						inv.setItem(30, bed);
						inv.setItem(22, goldNugget);
						inv.setItem(32, sign);
						inv.setItem(24, head);
						
						for (int i = 0; i < inv.getSize(); i++) {
							if(inv.getItem(i) != null) {
								if(inv.getItem(i).getType() == Material.AIR) {
									inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
								}
							} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
						}
					});

					player.openInventory(inv);
					return true;
				} else {
					sender.sendMessage(Main.PREFIX + "�6Vous pouvez vous cr�er une �le en utilisant la commande �7/is create");
					return true;
				}
				
				
			} else if(args.length >= 1) {
				
				if(args[0].equalsIgnoreCase("create")) {

					// /is create
					
					if(!main.getIslandIDFromUUID().containsKey(player.getUniqueId())) {
						 

						Inventory inv = Bukkit.createInventory(null, 9 * 3, "�cHeleSky \u00BB Cr�ation d'�le");
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							ItemStack dirt = new ItemBuilder(Material.GRASS_BLOCK, 1, "�6Th�me terrestre")
									.setLore(LoreUtil.getModifiedLore("�6Dirt, Dirt, Dirt, OhOh It�s my dirt", "�6Hummmm my big dirt !")).build();
							
							ItemStack water = new ItemBuilder(Material.WATER_BUCKET, 1, "�6Th�me aquatique")
									.setLore(LoreUtil.getModifiedLore("�9L�eau sa mouille, OMG !")).build();
							
							ItemStack fire = new ItemBuilder(Material.RED_NETHER_BRICKS, 1, "�6Th�me de feu")
									.setLore(LoreUtil.getModifiedLore("�4Ouh pinaise, le feu �a br�le !")).build();
							
							ItemStack air = new ItemBuilder(Material.FEATHER, 1, "�6Th�me a�rien")
									.setLore(LoreUtil.getModifiedLore("�dI believe I can FLYYY")).build();
							
							inv.setItem(10, dirt);
							inv.setItem(12, water);
							inv.setItem(14, fire);
							inv.setItem(16, air);
							
							for (int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) != null) {
									if(inv.getItem(i).getType() == Material.AIR) {
										inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
									}
								} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
						});
						
						player.openInventory(inv);
						return true;
					} else {
						player.sendMessage(Main.ERROR_PREFIX + "Vous faites d�j� partie d'une �le !");
						return true;
					}
				
				} else if(args[0].equalsIgnoreCase("upgrade")) {
					
					if(main.getIslandIDFromUUID().containsKey(player.getUniqueId())) {
						
						Island island = main.getIslandsFromId().get(main.getIslandIDFromUUID().get(player.getUniqueId()));
						
						if(!island.getOwnerId().equals(player.getUniqueId())) {
							player.sendMessage(Main.ERROR_PREFIX + "Vous n'�tes pas propri�taire de l'�le");
							return true;
						} 

						Inventory inv = Bukkit.createInventory(null, 9 * 4, "�6Am�lioration d'�le");
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							
							ItemBuilder paper = new ItemBuilder(Material.PAPER, 1, "�6Taille actuelle :�e " + (100+(25*island.getTier())) +" x "+(100+(25*island.getTier())));
							
							ItemBuilder gold_nugget = new ItemBuilder(Material.GOLD_NUGGET, 1, "�6Tier 1")
									.setLore(LoreUtil.getTierInformationLore(IslandTier.Tier1)).addItemFlags(ItemFlag.HIDE_ENCHANTS);
							
							ItemBuilder gold_ingot = new ItemBuilder(Material.GOLD_INGOT, 1, "�6Tier 2")
									.setLore(LoreUtil.getTierInformationLore(IslandTier.Tier2)).addItemFlags(ItemFlag.HIDE_ENCHANTS);
							
							ItemBuilder gold_block = new ItemBuilder(Material.GOLD_BLOCK, 1, "�6Tier 3")
									.setLore(LoreUtil.getTierInformationLore(IslandTier.Tier3)).addItemFlags(ItemFlag.HIDE_ENCHANTS);
							
							ItemBuilder nether_star = new ItemBuilder(Material.NETHER_STAR, 1, "�6Tier 4")
									.setLore(LoreUtil.getTierInformationLore(IslandTier.Tier4)).addItemFlags(ItemFlag.HIDE_ENCHANTS);
							
							switch (island.getTier()) {
								case 4:
								case 3:
									gold_block.addSafeEnchant(Enchantment.DURABILITY, 1);
								case 2:
									gold_ingot.addSafeEnchant(Enchantment.DURABILITY, 1);
								case 1:
									gold_nugget.addSafeEnchant(Enchantment.DURABILITY, 1);
									break;
							default:
								break;
							}
							
							inv.setItem(13, paper.build());
							
							inv.setItem(19, gold_nugget.build());
							inv.setItem(21, gold_ingot.build());
							inv.setItem(23, gold_block.build());
							inv.setItem(25, nether_star.build());
							
							for (int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) != null) {
									if(inv.getItem(i).getType() == Material.AIR) {
										inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
									}
								} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
							
						});
						player.openInventory(inv);
						
						return true;
					} else {
						player.sendMessage(Main.PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
				
				} else if(args[0].equalsIgnoreCase("go") || args[0].equalsIgnoreCase("home")) {
					if(main.hasIsland(player.getUniqueId())) {
						try {
							Island island = main.getPlayerIsland(player.getUniqueId());
							player.teleport(island.getHome());
							return true;
						} catch (NullPointerException e) {
							sender.sendMessage(Main.PREFIX + "Votre �le n'a pas �t� trouv�e, vous devez poss�der/faire partie d'une �le pour y acc�der.");
							return true;
							
						}
					} else {
						player.sendMessage(Main.ERROR_PREFIX + "�cVous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("warp")) {
					
					if(args.length > 1) {
						// joueur d�fini
						String targetname = args[1];
						OfflinePlayer target = Bukkit.getPlayer(targetname);
						if(target == null) {
							sender.sendMessage(Main.ERROR_PREFIX + "Le joueur nomm� n'est jamais venu sur le serveur ...");
							return true;
						}
						
						if(main.hasIsland(target.getUniqueId())) {
							
							Island island = main.getPlayerIsland(target.getUniqueId());
							
							if(!island.isWarpEnabled()) {
								sender.sendMessage(Main.PREFIX + "Le warp de cette �le est malheureusement d�sactiv� !");
								return true;
							}
							
							if(island.getBannedIds().contains(player.getUniqueId())) {
								sender.sendMessage(Main.PREFIX + "�cVous avez �t� banni de cette �le !");
								return true;
							}
							
							player.teleport(island.getWarp());
							return true;
						} else {
							sender.sendMessage(Main.ERROR_PREFIX + "Le joueur nomm� n'a pas d'�le associ�e !");
							return true;
						}
						
					} else {

						int page = 0;
						Inventory inv = Bukkit.createInventory(null, 6*9, "�8Warps du serveur �7("+page+"/"+ (int) this.main.getIslandsFromId().values().size() / 36+")");
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							
							ItemStack infobook = new ItemBuilder(Material.BOOK, 1, "�cInformations").setLore("�eNombre total de warps : " + this.main.getIslandsFromId().values().size()).build();
							ItemStack nextarrow = new ItemBuilder(Material.ARROW, 1, "�cPage suivante").build();

							int slot = 9;
							
							inv.setItem(4, infobook);
							
							inv.setItem(53, nextarrow);
							
							List<Island> islands = this.main.getIslandsFromId()
									.values()
									.stream()
									.filter(island -> island.isWarpEnabled())
									.collect(Collectors.toList());
							
							for(int i = page * 36; i < (page+1)*36; i++) {
								if(i >= islands.size()) break;
								ItemStack islandpaper = new ItemBuilder(Material.PAPER,1, ""+islands.get(i).getName()).build();
								inv.setItem(slot, islandpaper);
								slot++;
							}
							
							for (int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) != null) {
									if(inv.getItem(i).getType() == Material.AIR) {
										inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
									}
								} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
							
						});
						
						player.openInventory(inv);
						return true;
						
					}
				} else if(args[0].equalsIgnoreCase("level")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						sender.sendMessage(Main.PREFIX + "Actuellement, votre �le est de niveau �e" + main.getPlayerIsland(player.getUniqueId()).getLevel() + "�6 !");
						return true;
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("sethome")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(island.getOwnerId().equals(player.getUniqueId())) { // si c'est l'owner
							island.setHome(player.getLocation());
							player.sendMessage(Main.PREFIX + "Le home de votre �le � bien �t� d�plac� !");
							return true;
						} else {
							player.sendMessage(Main.PREFIX + "Vous devez �tre propri�taire de l'�le pour en d�placer le home !");
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("setwarp")) {
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(island.getOwnerId().equals(player.getUniqueId())) { // si c'est l'owner
							island.setWarp(player.getLocation());
							island.setWarpEnabled(true);
							player.sendMessage(Main.PREFIX + "Le warp de votre �le � bien �t� d�plac� !");
							return true;
						} else {
							player.sendMessage(Main.PREFIX + "Vous devez �tre propri�taire de l'�le pour en d�placer le warp !");
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("rename")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(island.getOwnerId().equals(player.getUniqueId())) { // si c'est l'owner
							island.setName(ChatColor.translateAlternateColorCodes('&', args[1]));
							player.sendMessage(Main.PREFIX + "Le nom de votre �le a bien �t� chang� en : " + island.getName());
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("setowner")) {
					
					String targetname = args[1];
					OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "Ce joueur n'est jamais venu sur le serveur !");
						return true;
					}
					
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(island.getOwnerId().equals(player.getUniqueId()) && island.getMembersId().contains(target.getUniqueId())) { // si c'est l'owner & que la cible fait partie du groupe

							island.getMembersId().add(player.getUniqueId());
							island.getMembersId().remove(target.getUniqueId());
							island.setOwnerId(target.getUniqueId());
							
							if(target.isOnline()) {
								Bukkit.getPlayer(target.getUniqueId()).sendMessage(Main.PREFIX + "Vous devenez propri�taire de l'�le : �e" + island.getName());
							}
				
							player.sendMessage(Main.PREFIX + "L'�le " + island.getName() + " appartient dor�navent � " + target.getName());
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("team")) {
					if(main.hasIsland(player.getUniqueId())) {
						
						Island island = main.getPlayerIsland(player.getUniqueId());
						
						StringJoiner joiner = new StringJoiner("�6, �e");
						String owner_pseudo = Bukkit.getOfflinePlayer(island.getOwnerId()).getName();
						
						joiner.add("�6 " + owner_pseudo);
						
						for(UUID id : island.getMembersId()) {
							
							String pseudo = Bukkit.getOfflinePlayer(id).getName();
							joiner.add(pseudo);
							
						}
						
						sender.sendMessage(Main.PREFIX + "Votre �le comporte les joueurs : " + joiner.toString());
						return true;
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("delwarp")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(island.getOwnerId().equals(player.getUniqueId())) { // si c'est l'owner
							island.setWarpEnabled(false);
							sender.sendMessage(Main.PREFIX + "Le warp de votre �le a bien �t� d�sactiv�");
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("leave")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(!island.getOwnerId().equals(player.getUniqueId())) { // si c'est l'owner
							
							island.getMembersId().remove(player.getUniqueId());
							main.getIslandIDFromUUID().remove(player.getUniqueId());
							player.teleport(Main.spawn);
							sender.sendMessage(Main.PREFIX + "Vous avez quitt� l'�le qui vous �tait associ�e !");
							return true;
						} else {
							sender.sendMessage(Main.PREFIX + "Vous �tes le propri�taire de votre �le, vous ne pouvez pas la quitter !");
							return true;
						}
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("top")) {

					Inventory inv = Bukkit.createInventory(null, 3 * 9, "�8Podium des �les \u00BB");

					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						Island[] islands = getTopIslands();
						
						ItemStack paper = new ItemBuilder(Material.PAPER,1,"Place non occup�e").setLore("�rIl n'y a pas encore assez d'�les pour ","�rque cette place du classement soit occup�e").build();
						
						switch (islands.length) {
							case 5:
								ItemStack iron_block = new ItemBuilder(Material.IRON_BLOCK, 1, islands[4].getName()).setLore( LoreUtil.getTopIslandLore(islands[4])).build();
								inv.setItem(15, iron_block);
							case 4:
								ItemStack gold_block = new ItemBuilder(Material.GOLD_BLOCK, 1, islands[3].getName()).setLore( LoreUtil.getTopIslandLore(islands[3])).build();
								inv.setItem(14, gold_block);
							case 3:
								ItemStack diamond_block = new ItemBuilder(Material.DIAMOND_BLOCK, 1, islands[2].getName()).setLore( LoreUtil.getTopIslandLore(islands[2])).build();
								inv.setItem(13, diamond_block);
							case 2:
								ItemStack emerald_block = new ItemBuilder(Material.EMERALD_BLOCK, 1, islands[1].getName()).setLore( LoreUtil.getTopIslandLore(islands[1])).build();
								inv.setItem(12, emerald_block);
							case 1:
								ItemStack nether_star = new ItemBuilder(Material.NETHER_STAR, 1, islands[0].getName()).setLore( LoreUtil.getTopIslandLore(islands[0])).build();
								inv.setItem(11, nether_star);
								break;
								
							default: return;
						}
					
						for(int i = 0; i < 5 - islands.length; i++) inv.setItem(15 - i, paper);
						
						for (int i = 0; i < inv.getSize(); i++) {
							if(inv.getItem(i) != null) {
								if(inv.getItem(i).getType() == Material.AIR) {
									inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
								}
							} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
						}
					});
					player.openInventory(inv);
					return true;
					
				} else if(args[0].equalsIgnoreCase("invite") && args.length > 1) {
					
					if(!main.getIslandIDFromUUID().containsKey(player.getUniqueId())) {
						sender.sendMessage(Main.PREFIX + "Vous n'avez pas d'�le !");
						return true;
					}
					if(!main.getPlayerIsland(player.getUniqueId()).getOwnerId().equals(player.getUniqueId())) {
						sender.sendMessage(Main.PREFIX + "Vous n'�tes pas propri�taire de l'�le !");
						return true;
					}
					
					Island island = main.getPlayerIsland(player.getUniqueId());

					String targetpseudo = args[1];
					Player target = Bukkit.getPlayer(targetpseudo);
					
					if(island.getMembersId().size() >= Main.maxPlayerPerIsland) {
						sender.sendMessage(Main.PREFIX + "�cVotre �le comprend trop de membres, vous ne pouvez pas inviter quelqu'un de suppl�mentaire !");
						return true;
					}

					if(target == null) {
						sender.sendMessage(Main.PREFIX + "�cLe joueur cibl� n'est pas pr�sent sur le serveur !");
						return true;
					}
					
					if(island.getMembersId().contains(target.getUniqueId())) {
						sender.sendMessage(Main.PREFIX + "�cCe joueur fait d�j� partie de l'�le !");
						return true;
					}
					
					TextComponentUtil mainComp = new TextComponentUtil(Main.PREFIX + "�6Vous avez �t� invit� sur l'�le " + island.getName() + "\n");
					TextComponentUtil acceptComp = new TextComponentUtil("�a�l[Accepter]").setClick_runCommand("/is joinaccept").setHover_showText("�aCliquez ici pour accepter");
					TextComponentUtil midComp = new TextComponentUtil("�r  |  �r");
					TextComponentUtil denyComp = new TextComponentUtil("�c�l[Refuser]").setClick_runCommand("/is joindeny").setHover_showText("�cCliquez ici pour accepter");;
					
					TextComponent total = mainComp.addExtra(acceptComp.build()).addExtra(midComp.build()).addExtra(denyComp.build()).build();
					
					main.getPendingInvites().put(target.getUniqueId(), island);
					target.spigot().sendMessage(total);
					return true;
					
				} else if(args[0].equalsIgnoreCase("joinaccept")) {
					
					if(main.getPendingInvites().containsKey(player.getUniqueId())) {
						
						Player owner = Bukkit.getPlayer(main.getPendingInvites().get(player.getUniqueId()).getOwnerId());
						if(owner != null) {
							owner.sendMessage(Main.PREFIX + "La requ�te envoy�e � " + player.getDisplayName() + " a �t� accept�e");
						}
						
						main.getPendingInvites().remove(player.getUniqueId());
						main.getPlayerIsland(owner.getUniqueId()).getMembersId().add(player.getUniqueId());
						main.getIslandIDFromUUID().put(player.getUniqueId(), main.getPlayerIsland(owner.getUniqueId()).getId());
						player.sendMessage(Main.PREFIX + "La requ�te a bien �t� accept�e !");
						return true;
					} else {
						player.sendMessage(Main.PREFIX + "�cVous n'avez pas de d'invitation en attente !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("joindeny")) {
					
					if(main.getPendingInvites().containsKey(player.getUniqueId())) {
						
						Player owner = Bukkit.getPlayer(main.getPendingInvites().get(player.getUniqueId()).getOwnerId());
						if(owner != null) {
							owner.sendMessage(Main.PREFIX + "La requ�te envoy�e � " + player.getDisplayName() + " a �t� refus�e");
						}
						
						main.getPendingInvites().remove(player.getUniqueId());
						player.sendMessage(Main.PREFIX + "La requ�te a bien �t� refus�e !");
						return true;
					} else {
						player.sendMessage(Main.PREFIX + "�cVous n'avez pas de d'invitation en attente !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("kick") && args.length > 1) {
					
					if(main.getPlayerIsland(player.getUniqueId()).getOwnerId().equals(player.getUniqueId())) {
						
						String targetname = args[1];
						OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
						if(target == null) {
							player.sendMessage(Main.PREFIX + "�cLe joueur cible n'est jamais venu sur le serveur");
							return true;
						}
						
						Island island = main.getPlayerIsland(player.getUniqueId());
						
						if(island.getMembersId().contains(target.getUniqueId())) {
							// member donc kick + tp spawn
							island.getMembersId().remove(target.getUniqueId());
							main.getIslandIDFromUUID().remove(target.getUniqueId());
							
							if(target.isOnline()) {							
								target.getPlayer().sendMessage(Main.PREFIX + "�6Vous avez �t� renvoy� de l'�le " + island.getName()+ " !");
								target.getPlayer().teleport(Main.spawn);
								return true;
							}
							player.sendMessage(Main.PREFIX + "Le joueur " + target.getName() + " a effectivement �t� �ject� de votre �le");
							return true;
							
						} else if(island.getCooperatorsId().contains(target.getUniqueId())) {
							// cooperator donc kick + tp a son �le s'il en a une
							
							island.getCooperatorsId().remove(target.getUniqueId());
							
							if(target.isOnline()) {							
								target.getPlayer().sendMessage(Main.PREFIX + "�6Vous avez �t� renvoy� de l'�le " + island.getName()+ " !");
								if(main.hasIsland(target.getUniqueId())) {
									target.getPlayer().teleport(main.getPlayerIsland(target.getUniqueId()).getHome());
								} else target.getPlayer().teleport(Main.spawn);
								return true;
							}
							player.sendMessage(Main.PREFIX + "Le joueur " + target.getName() + " a effectivement �t� �ject� de votre �le");
							return true;
						} else if(island.getVisitors().contains(target)) {
							if(target.isOnline()) {
								target.getPlayer().teleport(Main.spawn);
								target.getPlayer().sendMessage(Main.PREFIX + "�6Vous avez �t� renvoy� de l'�le " + island.getName()+ " !");
							}
							return true;
						} else {
							player.sendMessage(Main.PREFIX + "�cCe joueur n'a aucun lien avec votre �le");
							return true;
						}
						
					} else {
						player.sendMessage(Main.PREFIX + "�cVous n'�tes pas propri�taire de l'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("ban") && args.length > 1) {
					
					if(main.hasIsland(player.getUniqueId())) {
						
						Island island = main.getPlayerIsland(player.getUniqueId());
						if(!player.getUniqueId().equals(island.getOwnerId())) {
							player.sendMessage(Main.PREFIX + "Tu ne peux pas faire cela petit chenapan");
							return true;
						}
						
						String targetname = args[1];
						OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
						if(target == null) {
							player.sendMessage(Main.PREFIX + "�cLe joueur cible n'est jamais venu sur le serveur");
							return true;
						}
						
						if(target.getUniqueId().equals(island.getOwnerId())) {
							player.sendMessage(Main.PREFIX + "Tu ne peux pas faire cela !");
							return true;
						}
						
						island.getBannedIds().add(target.getUniqueId());
						if(island.getMembersId().contains(target.getUniqueId())) island.getMembersId().remove(target.getUniqueId());
						if(island.getCooperatorsId().contains(target.getUniqueId())) island.getCooperatorsId().remove(target.getUniqueId());
						
						if(target.isOnline()) {
							target.getPlayer().teleport(Main.spawn);
							target.getPlayer().sendMessage(Main.PREFIX + "�cVous avez �t� banni de l'�le " + island.getName() + " !");
						}
						
						player.sendMessage(Main.PREFIX + "�6Le joueur �e" + target.getName() + "�6 a bien �t� banni de votre �le !");
						return true;
					} else {
						player.sendMessage(Main.PREFIX + "�cVous n'avez pas d'�le !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("unban") && args.length > 1) {
					
					if(main.hasIsland(player.getUniqueId())) {

						Island island = main.getPlayerIsland(player.getUniqueId());
						
						String targetname = args[1];
						OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
						if(target == null) {
							player.sendMessage(Main.PREFIX + "�cLe joueur cible n'est jamais venu sur le serveur");
							return true;
						}
						
						if(island.getBannedIds().contains(target.getUniqueId())) {
							
							island.getBannedIds().remove(target.getUniqueId());
							if(target.isOnline()) {
								target.getPlayer().sendMessage(Main.PREFIX + "�cVous avez �t� d�banni de l'�le " + island.getName() + " !");
							}
							player.sendMessage(Main.PREFIX + "Le joueur " + target.getName() + " a bien �t� d�banni de l'�le !");
							return true;
							
						} else {
							player.sendMessage(Main.PREFIX + "�cLe joueur cible n'est pas banni de votre �le !");
							return true;
						}
						
					}
					
				} else if(args[0].equalsIgnoreCase("banlist")) {
					
					if(main.getPlayerIsland(player.getUniqueId()).getOwnerId().equals(player.getUniqueId())) {
					
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
							Island island = main.getPlayerIsland(player.getUniqueId());
							
							StringJoiner joiner = new StringJoiner("�6, ");
							
							for(UUID id : island.getBannedIds()) {
								
								String pseudo = Bukkit.getOfflinePlayer(id).getName();
								joiner.add("�e"+pseudo);
								
							}
							
							sender.sendMessage(Main.PREFIX + "Votre �le comporte les bannissements : " + joiner.toString());

						});
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("cooplist")) {
					
					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						Island island = main.getPlayerIsland(player.getUniqueId());
						
						StringJoiner joiner = new StringJoiner("�6, ");
						
						for(UUID id : island.getCooperatorsId()) {
							
							String pseudo = Bukkit.getOfflinePlayer(id).getName();
							joiner.add("�e"+pseudo);
							
						}
						
						sender.sendMessage(Main.PREFIX + "Cette �le comporte les coop�rateurs : " + joiner.toString());

					});
					return true;
					
				} else if(args[0].equalsIgnoreCase("coop") && args.length > 2) {
					
					if(main.hasIsland(player.getUniqueId())) {
						
						if(main.getPlayerIsland(player.getUniqueId()).getOwnerId().equals(player.getUniqueId())) {
							String operation = args[1];
							String targetname = args[2];
							
							OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
							if(target == null) {
								player.sendMessage(Main.PREFIX + "�cLe joueur cible n'est jamais venu sur le serveur");
								return true;
							}
							
							Island island = main.getPlayerIsland(player.getUniqueId());
							
							if(operation.equalsIgnoreCase("remove")) {
								island.getCooperatorsId().remove(target.getUniqueId());
								if(target.isOnline()) {
									target.getPlayer().sendMessage(Main.PREFIX + "�6Vous n'�tes plus coop�rateur de l'�le " + island.getName() + " !");
								}
								sender.sendMessage(Main.PREFIX + "�6Le joueur " + target.getName() + " a perdu son r�le de coop�rateur de votre �le");
								return true;
								
							} else if(operation.equalsIgnoreCase("add")) {
								island.getCooperatorsId().add(target.getUniqueId());
								
								if(target.isOnline()) {
									target.getPlayer().sendMessage(Main.PREFIX + "�6Vous �tes dor�navent coop�rateur de l'�le " + island.getName() + " !");
								}
								sender.sendMessage(Main.PREFIX + "�6Le joueur " + target.getName() + " est maintenant coop�rateur de votre �le");
								return true;
								
							} else return false;
							
						} else {
							player.sendMessage(Main.PREFIX + "�cVous n'�tes pas le propri�taire de l'�le");
							return true;
						}
					} else {
						player.sendMessage(Main.PREFIX + "�cVous n'avez pas d'�le !");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("deleteconfirm")) {
					
					if(main.getPendingDeletions().contains(player.getUniqueId())) {
						
						Island island = main.getPlayerIsland(player.getUniqueId());
						main.getIslandManager().deleteIsland(island);
						main.getPendingDeletions().remove(player.getUniqueId());
						
						return true;
						
					} else {
						sender.sendMessage(Main.PREFIX + "�cVous n'avez pas de demande de destruction en attente...");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("deletedeny")) {
					
					if(main.getPendingDeletions().contains(player.getUniqueId())) {
						
						main.getPendingDeletions().remove(player.getUniqueId());
						sender.sendMessage(Main.PREFIX + "�cVotre demande de destruction a bien �t� refus�e");
						return true;
						
					} else {
						sender.sendMessage(Main.PREFIX + "�cVous n'avez pas de demande de destruction en attente...");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("delete")) {
					
					if(main.hasIsland(player.getUniqueId())) {
						
						if(main.getPlayerIsland(player.getUniqueId()).getOwnerId().equals(player.getUniqueId())) {
							
							TextComponentUtil mainComp = new TextComponentUtil(Main.PREFIX + "�cVoulez vous vraiment d�truire votre �le ? �7( �7Cette �7action �7est �7irreversible �7:/ �7) \n");
							TextComponentUtil acceptComp = new TextComponentUtil("�a�l[Accepter]").setClick_runCommand("/is deleteconfirm").setHover_showText("�aCliquez ici pour accepter");
							TextComponentUtil midComp = new TextComponentUtil("�r  |  �r");
							TextComponentUtil denyComp = new TextComponentUtil("�c�l[Refuser]").setClick_runCommand("/is deletedeny").setHover_showText("�cCliquez ici pour refuser");;
							
							TextComponent total = mainComp.addExtra(acceptComp.build()).addExtra(midComp.build()).addExtra(denyComp.build()).build();
							main.getPendingDeletions().add(player.getUniqueId());
							player.spigot().sendMessage(total);
							return true;
							
						} else {
							sender.sendMessage(Main.PREFIX + "�cVous n'�tes pas propri�taire de l'�le !");
							return true;
						}
					} else {
						sender.sendMessage(Main.PREFIX + "�cVous n'avez pas d'�le !");
						return true;
					}
					
				}
			}
			
		}
		
		return false;
	}
	
	public Island[] getTopIslands() {
		
		List<Island> islandsList = main.getIslandsFromId().values().stream().collect(Collectors.toList());
		if(islandsList.size() == 1) {
			return new Island[] { islandsList.stream().collect(Collectors.toList()).get(0) };
		}

		Island[] topIslands = new Island[(islandsList.size() > 5 ? 5 : islandsList.size())];
		Island[] islandToSort = islandsList.toArray(new Island[islandsList.size()]);
		
		bubbleSort(islandToSort);
		
		for(int i = 0; i < (islandsList.size() > 5 ? 5 : islandsList.size()); i++) {
			topIslands[i] = islandToSort[i];
		}
		
		return topIslands;
	}
	
	private final void bubbleSort(Island[] a) {
	    boolean sorted = false;
	   
	    Island temp;
	    while(!sorted) {
	    
	    	sorted = true;
	        for (int i = 0; i < a.length - 1; i++) {
	        
	        	if (a[i].getStrictXpOfIsland() < a[i+1].getStrictXpOfIsland()) {
	            
	        		temp = a[i];
	                a[i] = a[i+1];
	                a[i+1] = temp;
	                sorted = false;
	                
	        	}
	        }
	    }
	    
	}

	@Override
	public String getCommand() {
		return "island";
	}

	@Override
	public List<String> aliases() {
		return Arrays.asList("is");
	}

}
