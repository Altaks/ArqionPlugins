package fr.altaks.helesky.commands;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.HeleCommand;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.utils.LoreUtil;
import fr.altaks.helesky.utils.TextComponentUtil;

public class AdminSky implements HeleCommand {
	
	private Main main;
	
	public AdminSky(Main main) {
		this.main = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length > 1) {
				
				if(args[0].equalsIgnoreCase("setowner") && args.length == 3) {
					
					String targetname = args[1];
					String islandidstr = args[2];
					
					try {
						
						OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
						if(target == null) {
							sender.sendMessage(Main.PREFIX + "�cLe joueur cibl� n'est jamais venu sur le serveur");
							return true;
						}

						int islandid = Integer.parseInt(islandidstr);
						Island island = main.getIslandsFromId().get(islandid);
						
						if(island.getMembersId().contains(target.getUniqueId())) {
					
							if(target.isOnline()) {
								target.getPlayer().sendMessage(Main.PREFIX + "Vous avez maintenant la propri�t� de l'�le : �e" + island.getName());
							}
							OfflinePlayer lastOwner = Bukkit.getOfflinePlayer(island.getOwnerId());
							if(lastOwner.isOnline()) {
								lastOwner.getPlayer().sendMessage(Main.PREFIX + "Le joueur " + target.getName() + " est maintenant propri�taire de votre �le !");
							}
									
							island.getMembersId().add(island.getOwnerId());
							island.getMembersId().remove(target.getUniqueId());
							island.setOwnerId(target.getUniqueId());
							
							return true;
						} else {
							sender.sendMessage(Main.PREFIX + "Le futur propri�taire doit pr�alablement faire partie des membres de l'�le !");
							return true;
						}
					} catch (NumberFormatException e) {
						sender.sendMessage(Main.ERROR_PREFIX + "Le format de l'id de l'�le est incorrect !");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("infos") && args.length >= 2) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.PREFIX + "�cLe joueur cibl� n'est jamais venu sur le serveur");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						
						Island island = main.getPlayerIsland(target.getUniqueId());
						
						TextComponentUtil delimiter = new TextComponentUtil(LoreUtil.loreDelimitation);
						((Player)sender).spigot().sendMessage(delimiter.build());
						
						TextComponentUtil idcomp = new TextComponentUtil("\nId d'�le : #" + island.getId() + "\n");
						((Player)sender).spigot().sendMessage(idcomp.build());

						TextComponentUtil namecomp = new TextComponentUtil("Nom de l'�le : " + island.getName() + "\n");
						((Player)sender).spigot().sendMessage(namecomp.build());
						
						TextComponentUtil ownercomp = new TextComponentUtil("Propri�taire de l'�le : " + Bukkit.getOfflinePlayer(island.getOwnerId()).getName() + "\n");
						((Player)sender).spigot().sendMessage(ownercomp.build());
						
						StringJoiner joiner = new StringJoiner("�6, ");
						for(UUID id : island.getMembersId()) {
							joiner.add("�e" + Bukkit.getOfflinePlayer(id).getName());
						}
							
						TextComponentUtil memberscomp = new TextComponentUtil("Membres de l'�le : " + joiner.toString() + "\n");
						((Player)sender).spigot().sendMessage(memberscomp.build());

						TextComponentUtil sizecomp = new TextComponentUtil("Taille de l'�le : " + (100+(25*(island.getTier())))+"\n");
						((Player)sender).spigot().sendMessage(sizecomp.build());
						
						String coords = "("+island.getHome().getBlockX()+"/"+island.getHome().getBlockY()+"/"+island.getHome().getBlockZ()+")";
						TextComponentUtil coordscomp = new TextComponentUtil("Emplacement de l'�le : " + coords + "\n")
								.setHover_showText("�eCliquez ici pour vous vous rendre au home de l'�le")
								.setClick_runCommand("/tp " + sender.getName() + " "+island.getHome().getBlockX()+" "+island.getHome().getBlockY()+" "+island.getHome().getBlockZ());
						((Player)sender).spigot().sendMessage(coordscomp.build());

						
						TextComponentUtil warpcomp = new TextComponentUtil("Warp d'�le : " + ((island.isWarpEnabled() ? "�aOui" : "�cNon")) +"\n");
						if(island.isWarpEnabled()) {
							warpcomp.setHover_showText("�eCliquez ici pour vous vous rendre au warp de l'�le")
									.setClick_runCommand("/tp " + sender.getName() + " "+island.getWarp().getBlockX()+" "+island.getWarp().getBlockY()+" "+island.getWarp().getBlockZ());
						}
						((Player)sender).spigot().sendMessage(warpcomp.build());
						
						StringJoiner bannedjoiner = new StringJoiner("�6, ");
						for(UUID id : island.getBannedIds()) {
							bannedjoiner.add("�e" + Bukkit.getOfflinePlayer(id).getName());
						}
						TextComponentUtil bannedscomp = new TextComponentUtil("Bannis : " + bannedjoiner.toString()+"\n");
						((Player)sender).spigot().sendMessage(bannedscomp.build());
						
						((Player)sender).spigot().sendMessage(delimiter.build());
							
						return true;
					} else {
						sender.sendMessage(Main.PREFIX + "Ce joueur n'a pas d'�le");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("team") && args.length == 4) {
					
					String operation = args[1];
					String targetname = args[2];
					String islandid = args[3];
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					try {
						int id = Integer.parseInt(islandid);
						if(main.getIslandsFromId().containsKey(id)) {
							Island island = main.getIslandsFromId().get(id);
							if(operation.equalsIgnoreCase("add")) {
								if(!island.getMembersId().contains(target.getUniqueId())) {
									island.getMembersId().add(target.getUniqueId());
									sender.sendMessage(Main.PREFIX + "Joueur ajout� avec succ�s !");
									return true;
								} else {
									sender.sendMessage(Main.ERROR_PREFIX + "Ce joueur fait d�j� partie de l'�quipe de cette �le !");
									return true;
								}
							} else if(operation.equalsIgnoreCase("remove")) {
								if(island.getMembersId().contains(target.getUniqueId())) {
									
									island.getMembersId().remove(target.getUniqueId());
									main.getIslandIDFromUUID().remove(target.getUniqueId());
									
									sender.sendMessage(Main.PREFIX + "Joueur retir� avec succ�s !");
									return true;
								} else {
									sender.sendMessage(Main.ERROR_PREFIX + "Ce joueur ne fait pas partie de l'�quipe de cette �le !");
									return true;
								}
							} else return false;
						} else {
							sender.sendMessage(Main.ERROR_PREFIX + "Aucune �le poss�de cet identifiant !");
							return true;
						}
						
					} catch (NumberFormatException e) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cVous devez entrer une valeur correcte pour l'ID de l'�le");
						e.printStackTrace();
					}
					
				} else if(args[0].equalsIgnoreCase("delete")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						
						Island island = main.getPlayerIsland(target.getUniqueId());
						main.getIslandManager().deleteIsland(island);
						
						sender.sendMessage(Main.PREFIX + "�cL'�le a �t� d�truite !");
						return true;
						
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("sethome")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						
						Island island = main.getPlayerIsland(target.getUniqueId());
						Location loc = player.getLocation().add(0, 0.1, 0);
						
						if(loc.distance(island.getAnchor()) <= 100 + (25 * island.getTier())) {
							island.setHome(loc);
							sender.sendMessage(Main.PREFIX + "�6Le home de l'�le a �t� chang�");
							return true;
						} else {
							sender.sendMessage(Main.PREFIX + "�6L'emplacement ne fait pas partie de l'�le !");
							return true;
						}
						
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("setwarp")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						
						Island island = main.getPlayerIsland(target.getUniqueId());
						Location loc = player.getLocation().add(0, 0.1, 0);
						
						if(loc.distance(island.getAnchor()) <= 100 + (25 * island.getTier())) {
							island.setWarp(loc);
							island.setWarpEnabled(true);
							sender.sendMessage(Main.PREFIX + "�6Le warp de l'�le a �t� chang�");
							return true;
						} else {
							sender.sendMessage(Main.PREFIX + "�6L'emplacement ne fait pas partie de l'�le !");
							return true;
						}
						
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("delwarp")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						
						Island island = main.getPlayerIsland(target.getUniqueId());
						island.setWarpEnabled(false);
						sender.sendMessage(Main.PREFIX + "�cLe warp de cette �le a �t� d�sactiv� !");
						return true;
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("tp")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						Island island = main.getPlayerIsland(target.getUniqueId());

						player.teleport(island.getHome());
						sender.sendMessage(Main.PREFIX + "Vous voila sur le home de " + island.getName());
						return true;
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("level")) {
					
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
					if(target == null) {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'est jamais venu sur le serveur ! ");
						return true;
					}
					
					if(main.hasIsland(target.getUniqueId())) {
						Island island = main.getPlayerIsland(target.getUniqueId());

						String operation = args[1];
						switch(operation.toLowerCase()) {
						
							case "set":
								if(args.length >= 4) {
									try {
										island.setLevel(Integer.parseInt(args[3]));
										sender.sendMessage(Main.PREFIX + "�cLe niveau de l'�le a bien �t� chang� !");
									} catch (NumberFormatException e) {
										sender.sendMessage(Main.PREFIX + "�cVous devez entrer une valeur correcte !");
										return true;
									}
								} else {
									sender.sendMessage(Main.ERROR_PREFIX + "�cVous n'avez pas sp�cifi� de valeur !");
									return true;
								}
								
								break;
							case "add":
								if(args.length > 3) {
									try {
										island.setLevel(island.getLevel() + Integer.parseInt(args[3]));
										sender.sendMessage(Main.PREFIX + "�cLe niveau de l'�le a bien �t� chang� !");
									} catch (NumberFormatException e) {
										sender.sendMessage(Main.PREFIX + "�cVous devez entrer une valeur correcte !");
										return true;
									}
								} else {
									sender.sendMessage(Main.ERROR_PREFIX + "�cVous n'avez pas sp�cifi� de valeur !");
									return true;
								}
								
								break;
							case "remove":
								if(args.length > 3) {
									try {
										island.setLevel(island.getLevel() - Integer.parseInt(args[3]));
										sender.sendMessage(Main.PREFIX + "�cLe niveau de l'�le a bien �t� chang� !");
									} catch (NumberFormatException e) {
										sender.sendMessage(Main.PREFIX + "�cVous devez entrer une valeur correcte !");
										return true;
									}
								} else {
									sender.sendMessage(Main.ERROR_PREFIX + "�cVous n'avez pas sp�cifi� de valeur !");
									return true;
								}
								
								break;
							case "clear":
								island.setLevel(0l);
								island.setXp(0l);
								break;
							
							default: return false;
						}
						sender.sendMessage(Main.PREFIX + "�cL'�le � maintenant " + island.getLevel() + " niveaux");						
						return true;
					} else {
						sender.sendMessage(Main.ERROR_PREFIX + "�cCe joueur n'a pas d'�le ! ");
						return true;
					}
					
				} 
				
			}
			
		}
		
		
		
		return false;
	}

	@Override
	public String getCommand() {
		return "adminsky";
	}

	@Override
	public List<String> aliases() {
		return Arrays.asList();
	}

}