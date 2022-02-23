package fr.matmatgamer.helebitcoins.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.matmatgamer.helebitcoins.Main;

public class CommandAbtc implements CommandExecutor {

	private Main main;
	
	public CommandAbtc(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if (args.length == 0 || args[0].equals("help")){
			
			sender.sendMessage("§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c- §4Admin - HELP §c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=\n\n\n"
					+ "§c/abtc infos <joueur> §9-> §7Montre combien le joueur <joueur> a de bitcoins\n\n"
					+ "§c/abtc give <joueur> <nombre> §9-> §7give <nombre> bitcoins au joueur <joueur>\n\n"
					+ "§c/abtc take <joueur> <nombre> §9-> §7retire <nombre> bitcoins au joueur <joueur>\n\n"
					+ "§c/abtc reset <joueur> §9-> §7remet les bitcoins au joueur <joueur> à 0\n\n\n"
					+ "§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c- §4Admin - HELP §c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=§c-§6=");
		}
		else if (args[0].equals("give")) {
			if(args.length != 1) {
				
				Player player = Bukkit.getPlayer(args[1]);
				
				if (!player.isOnline()) {
					sender.sendMessage(Main.prefix + " §4Le joueur doit être en ligne !");
					return false;
				}
				
				File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId().toString() + ".yml");
				
				if(!file.exists()) {
					sender.sendMessage(Main.prefix + " §4Ce joueur ne s'est jamais connecté !");
					return false;
				}
				
				YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
				
				if (args.length != 2) {
					float given = Float.valueOf(args[2]);
					float old = Float.valueOf(configuration.get("Bitcoins").toString());
					float now = old + given;
					
					configuration.set("Bitcoins", now);
					
					try {
						configuration.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					sender.sendMessage(Main.prefix + " §b" + given + " §6BitCoins ont été donnés à §9" + player.getName() + "§6. Il possède maintenant §b" + now + " §6BitCoins");
					player.sendMessage(Main.prefix + " §b" + given + " §6BitCoins vous ont été donnés par un §4Haut-Staff§6. Vous possédez maintenant §b" + now + " §6BitCoins");
				} else {
					sender.sendMessage(Main.prefix + " §4Veuillez spécifier un nombre de BitCoins !");
					return false;
				}
				
			} else {
				sender.sendMessage(Main.prefix + " §4Pseudo du joueur ?");
			}
		}
		else if (args[0].equals("take")) {
			if(args.length != 1) {
				Player player = main.getServer().getPlayer(args[1]);
				
				if (!player.isOnline()) {
					sender.sendMessage(Main.prefix + " §4Le joueur doit être en ligne !");
					return false;
				}
				
				File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
				
				if(!file.exists()) {
					sender.sendMessage(Main.prefix + " §4Ce joueur ne s'est jamais connecté !");
					return false;
				}
				
				YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
				
				if (args.length != 2) {
					float taken = Float.valueOf(args[2]);
					float old = Float.valueOf(configuration.get("Bitcoins").toString());
					float now = old - taken;
					
					if ((old - taken) < 0) {
						now = 0;
						taken = old;
					}
					configuration.set("Bitcoins", now);
					
					try {
						configuration.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					sender.sendMessage(Main.prefix + " §b" + taken + " §6BitCoins ont été retiré à §9" + player.getName() + "§6. Il posséde maintenant §b" + now + " §6BitCoins");
					player.sendMessage(Main.prefix + " §b" + taken + " §6BitCoins vous ont été retiré par un §4Haut-Staff§6. Vous possedez maintenant §b" + now + " §6BitCoins");
				} else {
					sender.sendMessage(Main.prefix + " §4Veuillez spécifier un nombre de BitCoins !");
					return false;
				}
				
			} else {
				sender.sendMessage(Main.prefix + " §4Pseudo du joueur ?");
			}
		}
		else if (args[0].equals("reset")) {
			if(args.length != 1) {
				Player player = main.getServer().getPlayer(args[1]);
				
				if (!player.isOnline()) {
					sender.sendMessage(Main.prefix + " §4Le joueur doit être en ligne !");
					return false;
				}
				
				File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
				
				if(!file.exists()) {
					sender.sendMessage(Main.prefix + " §4Ce joueur ne s'est jamais connecté !");
					return false;
				}
				
				YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
				
				configuration.set("Bitcoins", 0);
				
				try {
					configuration.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sender.sendMessage(Main.prefix + " §6Les BitCoins de§9 " + player.getName() + " §6ont été remis a 0");
				player.sendMessage(Main.prefix + " §6Vos BitCoins ont été remis à 0 par un §4Haut-Staff§6.");
			}
		}
		else if (args[0].equals("infos")) {
			if(args.length != 1) {
				Player player = main.getServer().getPlayer(args[1]);
				
				if (!player.isOnline()) {
					sender.sendMessage(Main.prefix + " §4Le joueur doit être en ligne !");
					return false;
				}
				
				File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
				
				if(!file.exists()) {
					sender.sendMessage(Main.prefix + " §4Ce joueur ne s'est jamais connecté !");
					return false;
				}
				
				YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
				
				float pBitcoins = Float.valueOf(configuration.get("Bitcoins").toString());
				
				sender.sendMessage(Main.prefix + " §9" + player.getName() + " §6possède §b" + pBitcoins + " §6BitCoins.");
			}
		}
		
		return false;
	}

}
