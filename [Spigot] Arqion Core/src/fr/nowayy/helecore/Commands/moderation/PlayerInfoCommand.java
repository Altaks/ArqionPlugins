package fr.nowayy.helecore.Commands.moderation;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;
import fr.nowayy.helecore.utils.TextComponentUtil;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerInfoCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("playerinfo") && args.length > 0) {
			
			OfflinePlayer target = null;
			
			try {
				target = Bukkit.getPlayer(args[0]);
			} catch (NullPointerException e) {
				try {
					target = Bukkit.getOfflinePlayer(args[0]);
				} catch (NullPointerException e2) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
			}
			
			if(target == null) {
				sender.sendMessage(Main.Error_Prefix+Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
				return true;
			}
			if(target.getPlayer().hasPermission("mod.helecore.playerinfo")) {
				if(!sender.hasPermission("mod.helecore.playerinfobypass")) {
					sender.sendMessage(Main.prefix + Messages.PlayerInfo_CannotInspect.toString());
					return true;
				}
			}
			
			TextComponent informations = new TextComponent(Main.prefix + "---------------------------------");
			
			// Main Informations
			Arrays.asList(new TextComponent[]{
				new TextComponentUtil("\n§6\u25AA "+"Pseudonyme : §e" + target.getName()).setClick_suggestCommand(target.getName()).build(),
				new TextComponentUtil("\n§6\u25AA "+"Identifiant: §e" + target.getUniqueId()).setClick_suggestCommand(target.getUniqueId().toString()).build(),
				new TextComponentUtil("\n§6\u25AA "+"Déjà venu  : §e" + (target.hasPlayedBefore() ? "oui" : "non")).build(),
				new TextComponentUtil("\n§6\u25AA "+"Date de la dernière déconnexion: §e" + Date.from(Instant.ofEpochMilli(target.getLastPlayed())).toGMTString()).setClick_suggestCommand(new String( Date.from(Instant.ofEpochMilli(target.getLastPlayed())).toGMTString())).build(),
				new TextComponentUtil("\n§6\u25AA "+"Date de la première connexion: §e" + Date.from(Instant.ofEpochMilli(target.getFirstPlayed())).toGMTString()).setClick_suggestCommand(new String( Date.from(Instant.ofEpochMilli(target.getFirstPlayed())).toGMTString())).build()
			}).forEach(part -> informations.addExtra(part));
			
			if(target.isOnline()) {
				Player locked = target.getPlayer();
				
				Arrays.asList(new TextComponent[] {
						new TextComponentUtil("\n§6\u25AA "+"Adresse IP : §e" + locked.getAddress().getHostString() + " §6| Port : §e" + locked.getAddress().getPort()).setClick_suggestCommand(locked.getAddress().toString().replace("/", "")).build(),
						new TextComponentUtil("\n§6\u25AA "+"Coordonnées : §e[" + locked.getWorld().getName() + "/" + locked.getLocation().getBlockX() + "/" + locked.getLocation().getBlockY() + "/" + locked.getLocation().getBlockZ() + "]")
									.setClick_suggestCommand("[" + locked.getWorld().getName() + "/" + locked.getLocation().getBlockX() + "/" + locked.getLocation().getBlockY() + "/" + locked.getLocation().getBlockZ() + "]")
									.build(),
						new TextComponentUtil("\n§6\u25AA "+"Nom d'affichage §e: " + ((locked.getCustomName() == null) ? "inexistant" : locked.getCustomName())).build(),
						new TextComponentUtil("\n§6\u25AA "+"Vol activé : §e" + (locked.getAllowFlight() ? "oui" : "non")).build()
				}).forEach(text -> informations.addExtra(text));
			}
			
			informations.addExtra("\n"+Main.prefix + "---------------------------------");
			sender.spigot().sendMessage(informations);
			return true;
		}
		return false;
	}
	
	public String addNewLine(String text) {
		return text + "\n";
	}

}
