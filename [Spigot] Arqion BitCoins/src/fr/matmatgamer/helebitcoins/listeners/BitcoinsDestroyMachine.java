package fr.matmatgamer.helebitcoins.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AdminWrench;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;
import fr.matmatgamer.helebitcoins.utils.items.Wires;
import fr.matmatgamer.helebitcoins.utils.items.Wrench;

public class BitcoinsDestroyMachine implements Listener {

	
	private Main main;

	public BitcoinsDestroyMachine(Main main) {
		this.main = main;
	}
	
	
	Serveurs Serveurs = new Serveurs();
	AllimSolaire AllimSolaire = new AllimSolaire();
	PC PC = new PC();
	AllimEssence AllimEssence = new AllimEssence();
	Batterie Batterie = new Batterie();
	Wrench Wrench = new Wrench();
	AdminWrench AdminWrench = new AdminWrench();
	Wires Wires = new Wires();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onBlockDamage(BlockDamageEvent e) {


		Player player = e.getPlayer();
		Location loc = e.getBlock().getLocation();
		Material block = e.getBlock().getType();
		
		if(block.equals(Serveurs.Matos())) {
			File file = new File(main.getDataFolder(), "Id/serveurs.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			
			
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							if(player.getItemInHand().getType().equals(Wrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(Wrench.getName())) {
								e.getBlock().setType(Material.AIR);
								loc.getWorld().dropItem(loc, Serveurs.getItem());
								for (String str : new String[] {".co", ".owner"}) {
									configuration.set(i + str, null);
								}
								configuration.set(i + "", "BREAK");
								
								try {
									configuration.save(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
								for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
									if(configuration2.isSet("Serveurs." + j + ".id") && configuration2.get("Serveurs." + j + ".id").equals(i)) {
										String key = "Serveurs." + j;
										
										configuration2.set(key + ".placed", "False");
										configuration2.set(key + ".co", null);
										configuration2.set(key + ".energie", 0);
										configuration2.set(key + ".batterie", "False");
										configuration2.set(key + ".id", null);
										
										try {
											configuration2.save(file2);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										break;
									}
								}
								
							} else {
								player.sendMessage("Prends une wrench stp !");
							}
						} else {
							player.sendMessage("Ce n'est pas à toi !");
						}
						break;
					}
				}
				}
			
		}
		
		
		
		if(block.equals(Batterie.Matos())) {
			File file = new File(main.getDataFolder(), "Id/batteries.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			
			
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							if(player.getItemInHand().getType().equals(Wrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(Wrench.getName())) {
								e.getBlock().setType(Material.AIR);
								loc.getWorld().dropItem(loc, Batterie.getItem());
								
								for (String str : new String[] {".co", ".owner"}) {
									configuration.set(i + str, null);
								}
								configuration.set(i + "", "BREAK");
								
								
								
								for (int j = 0; j < Batterie.LimitePerPlayer(); j++) {
									if(configuration2.isSet("Batterie." + j + ".id") && configuration2.get("Batterie." + j + ".id").equals(i)) {
										configuration2.set("Batterie." + j + ".placed", "False");
										configuration2.set("Batterie." + j + ".co", null);
										configuration2.set("Batterie." + j + ".energie", 0);
										configuration2.set("Batterie." + j + ".batterie", "False");
										configuration2.set("Batterie." + j + ".id", null);
										
										try {
											configuration.save(file);
											configuration2.save(file2);
										} catch (IOException e1) {
											e1.printStackTrace();
										}

										break;
									}
								}
								
							} else {
								player.sendMessage("prend une wrench stp !");
							}
						} else {
							player.sendMessage("Ce n'est pas a toi !");
						}
						break;
					}
				}
			}
			
		}
		if(block.equals(AllimSolaire.Matos())) {
			File file = new File(main.getDataFolder(), "Id/allimSolaire.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							if(player.getItemInHand().getType().equals(Wrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(Wrench.getName())) {
								e.getBlock().setType(Material.AIR);
								loc.getWorld().dropItem(loc, AllimSolaire.getItem());
								
								configuration.set(i + ".co", null);
								configuration.set(i + ".owner", null);
								configuration.set(i + "", "BREAK");
								
								
								for (int j = 0; j < AllimSolaire.LimitePerPlayer(); j++) {
									if(configuration2.isSet("AllimSolaire." + j + ".id") && configuration2.get("AllimSolaire." + j + ".id").equals(i)) {
										configuration2.set("AllimSolaire." + j + ".placed", "False");
										configuration2.set("AllimSolaire." + j + ".co", null);
										configuration2.set("AllimSolaire." + j + ".energie", 0);
										configuration2.set("AllimSolaire." + j + ".batterie", "False");
										configuration2.set("AllimSolaire." + j + ".id", null);
										
										try {
											configuration.save(file);
											configuration2.save(file2);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										break;
									}
								}
								
							} else {
								player.sendMessage("Prends une wrench stp !");
							}
						} else {
							player.sendMessage("Ce n'est pas à toi !");
						}
						break;
					}
				}
			}
			
		}
		if(block.equals(AllimEssence.Matos())) {
			File file = new File(main.getDataFolder(), "Id/allimEssence.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							if(player.getItemInHand().getType().equals(Wrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(Wrench.getName())) {
								e.getBlock().setType(Material.AIR);
								loc.getWorld().dropItem(loc, AllimEssence.getItem());
								
								configuration.set(i + ".co", null);
								configuration.set(i + ".owner", null);
								configuration.set(i + "", "BREAK");
								
								try {
									configuration.save(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
								for (int j = 0; j < AllimEssence.LimitePerPlayer(); j++) {
									if(configuration2.isSet("AllimEssence." + j + ".id") && configuration2.get("AllimEssence." + j + ".id").equals(i)) {
										configuration2.set("AllimEssence." + j + ".placed", "False");
										configuration2.set("AllimEssence." + j + ".co", null);
										configuration2.set("AllimEssence." + j + ".energie", 0);
										configuration2.set("AllimEssence." + j + ".batterie", "False");
										configuration2.set("AllimEssence." + j + ".id", null);
										
										try {
											configuration2.save(file2);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										break;
									}
								}
								
							} else {
								player.sendMessage("Prends une wrench stp !");
							}
						} else {
							player.sendMessage("Ce n'est pas à toi !");
						}
						break;
					}
				}
			}
			
		}
		if(block.equals(PC.Matos())) {
			File file = new File(main.getDataFolder(), "Id/pc.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							if(player.getItemInHand().getType().equals(Wrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(Wrench.getName())) {
								e.getBlock().setType(Material.AIR);
								loc.getWorld().dropItem(loc, PC.getItem());
								
								configuration.set(i + ".co", null);
								configuration.set(i + ".owner", null);
								configuration.set(i + "", "BREAK");
								
								try {
									configuration.save(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
								configuration2.set("PC.placed", "False");
								configuration2.set("PC.co", null);
								configuration2.set("PC.energie", 0);
								configuration2.set("PC.batterie", "False");
								configuration2.set("PC.id", null);
										
								try {
									configuration2.save(file2);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								break;
								
							} else {
								player.sendMessage("prend une wrench stp !");
							}
						} else {
							player.sendMessage("Ce n'est pas a toi !");
						}
						break;
					}
				}
			}
			
		}
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onDestroy(BlockBreakEvent e) {


		Player player = e.getPlayer();
		Location loc = e.getBlock().getLocation();
		Material block = e.getBlock().getType();
		
		
		if(block.equals(Serveurs.Matos())) {
			final File file = new File(main.getDataFolder(), "Id/serveurs.yml");
			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
			final int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						e.setCancelled(true);
						if(player.getItemInHand().getType().equals(AdminWrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(AdminWrench.getName())) {
							e.getBlock().setType(Material.AIR);
							loc.getWorld().dropItem(loc, Serveurs.getItem());
							
							final File file2 = new File(main.getDataFolder(), "Players/" + configuration.get(i + ".owner") + ".yml");
							final YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
							
							configuration.set(i + ".co", null);
							configuration.set(i + ".owner", null);
							configuration.set(i + "", "BREAK");
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
								if(configuration2.isSet("Serveurs." + j + ".id") && configuration2.get("Serveurs." + j + ".id").equals(i)) {
									configuration2.set("Serveurs." + j + ".placed", "False");
									configuration2.set("Serveurs." + j + ".co", null);
									configuration2.set("Serveurs." + j + ".energie", 0);
									configuration2.set("Serveurs." + j + ".batterie", "False");
									configuration2.set("Serveurs." + j + ".id", null);
									
									try {
										configuration2.save(file2);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									break;
								}
							}
							break;
						}
					}
				}
			}
			
		}
		
		if(block.equals(PC.Matos())) {
			final File file = new File(main.getDataFolder(), "Id/pc.yml");
			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
			final int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						e.setCancelled(true);
						if(player.getItemInHand().getType().equals(AdminWrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(AdminWrench.getName())) {
							e.getBlock().setType(Material.AIR);
							loc.getWorld().dropItem(loc, PC.getItem());
							
							final File file2 = new File(main.getDataFolder(), "Players/" + configuration.get(i + ".owner") + ".yml");
							final YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
							
							configuration.set(i + ".co", null);
							configuration.set(i + ".owner", null);
							configuration.set(i + "", "BREAK");
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							configuration2.set("PC.placed", "False");
							configuration2.set("PC.co", null);
							configuration2.set("PC.energie", 0);
							configuration2.set("PC.batterie", "False");
							configuration2.set("PC.id", null);
									
							try {
								configuration2.save(file2);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							break;
						}
					}
				}
			}
			
		}
		
		if(block.equals(AllimEssence.Matos())) {
			final File file = new File(main.getDataFolder(), "Id/allimEssence.yml");
			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
			final int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						e.setCancelled(true);
						if(player.getItemInHand().getType().equals(AdminWrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(AdminWrench.getName())) {
							e.getBlock().setType(Material.AIR);
							loc.getWorld().dropItem(loc, AllimEssence.getItem());
							
							final File file2 = new File(main.getDataFolder(), "Players/" + configuration.get(i + ".owner") + ".yml");
							final YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
							
							configuration.set(i + ".co", null);
							configuration.set(i + ".owner", null);
							configuration.set(i + "", "BREAK");
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
								if(configuration2.isSet("AllimEssence." + j + ".id") && configuration2.get("AllimEssence." + j + ".id").equals(i)) {
									configuration2.set("AllimEssence." + j + ".placed", "False");
									configuration2.set("AllimEssence." + j + ".co", null);
									configuration2.set("AllimEssence." + j + ".energie", 0);
									configuration2.set("AllimEssence." + j + ".batterie", "False");
									configuration2.set("AllimEssence." + j + ".id", null);
									
									try {
										configuration2.save(file2);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									break;
								}
							}
							break;
						}
					}
				}
			}
			
		}
		
		if(block.equals(AllimSolaire.Matos())) {
			final File file = new File(main.getDataFolder(), "Id/allimSolaire.yml");
			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
			final int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						e.setCancelled(true);
						if(player.getItemInHand().getType().equals(AdminWrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(AdminWrench.getName())) {
							e.getBlock().setType(Material.AIR);
							loc.getWorld().dropItem(loc, AllimSolaire.getItem());
							
							final File file2 = new File(main.getDataFolder(), "Players/" + configuration.get(i + ".owner") + ".yml");
							final YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
							
							configuration.set(i + ".co", null);
							configuration.set(i + ".owner", null);
							configuration.set(i + "", "BREAK");
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
								if(configuration2.get("AllimSolaire." + j + ".id").equals(i)) {
									configuration2.set("AllimSolaire." + j + ".placed", "False");
									configuration2.set("AllimSolaire." + j + ".co", null);
									configuration2.set("AllimSolaire." + j + ".energie", 0);
									configuration2.set("AllimSolaire." + j + ".batterie", "False");
									configuration2.set("AllimSolaire." + j + ".id", null);
									
									try {
										configuration2.save(file2);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									break;
								}
							}
							break;
						}
					}
				}
			}
			
		}
		
		if(block.equals(Batterie.Matos())) {
			final File file = new File(main.getDataFolder(), "Id/batteries.yml");
			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
			final int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						e.setCancelled(true);
						if(player.getItemInHand().getType().equals(AdminWrench.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(AdminWrench.getName())) {
							e.getBlock().setType(Material.AIR);
							loc.getWorld().dropItem(loc, Batterie.getItem());
							
							final File file2 = new File(main.getDataFolder(), "Players/" + configuration.get(i + ".owner") + ".yml");
							final YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
							
							configuration.set(i + ".co", null);
							configuration.set(i + ".owner", null);
							configuration.set(i + "", "BREAK");
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
								String key = "Batterie" + j;
								
								if(configuration2.get(key + ".id").equals(i)) {
									configuration2.set(key + ".placed", "False");
									configuration2.set(key + ".co", null);
									configuration2.set(key + ".energie", 0);
									configuration2.set(key + ".batterie", "False");
									configuration2.set(key + ".id", null);
									
									try {
										configuration2.save(file2);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									break;
								}
							}
							break;
						}
					}
				}
			}
			

	}
	}
}
