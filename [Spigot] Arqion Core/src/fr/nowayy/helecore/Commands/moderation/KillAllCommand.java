package fr.nowayy.helecore.Commands.moderation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;

public class KillAllCommand implements TabExecutor {

	private Main main;
	public KillAllCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("killall") && args.length >= 1 && sender instanceof Player) {
			return Arrays.asList(new String[] {"all", "mobs", "minecarts", "boats", "arrow", "animals"});
		}
		

		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("killall") && sender instanceof Player && args.length == 1) {
			Player user = (Player) sender;
			String world = ((Entity)sender).getWorld().getName();
			switch (args[0]) {
			
			case "all":
				user.getWorld().getLivingEntities().forEach(entities -> {
					entities.remove();
				});
				break;
			
			case "mobs":
		        for (Iterator<Entity> iterator_hostiles = main.getServer().getWorld(world).getEntities().iterator(); iterator_hostiles.hasNext(); ) {
			          Entity entity = iterator_hostiles.next();
			          String mob = entity.getType().toString();
			          if (main.getConfig().getStringList("Hostiles").contains(mob))
				            entity.remove(); 
		        } 
		        break;
					
			case "animals":
					for (Iterator<Entity> iterator_animals = main.getServer().getWorld(world).getEntities().iterator(); iterator_animals.hasNext();) {
						Entity entity_animals = iterator_animals.next();
						String mob_a = entity_animals.getType().toString();
						if (main.getConfig().getString("Animals").contains(mob_a))
							entity_animals.remove();
					}
					break;
			case "minecart":
				for (Iterator<Entity> iterator_m = main.getServer().getWorld(world).getEntities().iterator(); iterator_m.hasNext();) {
					Entity entity_m = iterator_m.next();
					String mob_m = entity_m.getType().toString();
					if (main.getConfig().getString("Minecart").contains(mob_m))
						entity_m.remove();
				}
				break;
			
			case "arrow":
				for (Iterator<Entity> iterator_ar = main.getServer().getWorld(world).getEntities().iterator(); iterator_ar.hasNext();) {
					Entity entity_ar = iterator_ar.next();
					String mob_a = entity_ar.getType().toString();
					if (main.getConfig().getString("Arrows").contains(mob_a))
						entity_ar.remove();
				}
				break;
			}
			
			
			}
			
		



		return false;
	}

}
