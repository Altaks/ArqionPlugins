package fr.altaks.helesky.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.HeleCommand;
import fr.altaks.helesky.core.islandcore.Island;

public class DebugIsland implements HeleCommand {

	private Main main;
	
	public DebugIsland(Main main) {
		this.main = main;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && sender.isOp() && cmd.getName().equalsIgnoreCase("debugisland")) {
			Player player = (Player) sender;
			if(main.hasIsland(player.getUniqueId())) {
				Island island = main.getPlayerIsland(player.getUniqueId());
				
				player.sendMessage("Index d'île :" + island.getId());
				player.sendMessage("Nom d'île :" + island.getName());
				player.sendMessage("Niveau : " + island.getLevel() + " niveau(x) et " + island.getXp() + " xp");
				player.sendMessage("Tier : " +island.getTier());
				player.sendMessage("Propriétaire " + Bukkit.getOfflinePlayer(island.getOwnerId()).getName());
				
				StringJoiner members = new StringJoiner(",");
				for(UUID id : island.getMembersId()) {
					members.add(Bukkit.getOfflinePlayer(id).getName());
				}
				player.sendMessage("Membres : " + members.toString());
				
				StringJoiner banneds = new StringJoiner(",");
				for(UUID id : island.getBannedIds()) {
					banneds.add(Bukkit.getOfflinePlayer(id).getName());
				}
				player.sendMessage("Bannis : " + banneds.toString());
				
				player.sendMessage("Warp actif : " + (island.isWarpEnabled() ? "Oui" : "Non") );
				
			} else player.sendMessage("Vous n'avez pas d'île");
		}
		return false;
	}

	@Override
	public String getCommand() {
		return "debugisland";
	}

	@Override
	public List<String> aliases() {
		return new ArrayList<String>();
	}

}
