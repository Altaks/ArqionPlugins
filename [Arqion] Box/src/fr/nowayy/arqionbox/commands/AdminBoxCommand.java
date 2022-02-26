package fr.nowayy.arqionbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxType;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;

public class AdminBoxCommand implements CommandExecutor {
	
	private Main main;
	
	public AdminBoxCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// adminbox givekey <player> <key> [amount]
		if(cmd.getName().equalsIgnoreCase("adminbox") && sender instanceof Player) {
			
			if(args.length == 0){
				
				Inventory inv = Bukkit.createInventory(null, 5 * 9, "§cArqionBox - §cAdminBox");
			
				Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
					
					for (int i = 0; i < inv.getSize(); i++) {
						if(inv.getItem(i) != null) {
							if(inv.getItem(i).getType() == Material.AIR) {
								inv.setItem(i, BoxItems.invFiller);
							}
						} else inv.setItem(i, BoxItems.invFiller);
					}
					
					
					inv.setItem(20, BoxItems.ironBoxSymbol);
					inv.setItem(24, BoxItems.goldBoxSymbol);
					inv.setItem(30, BoxItems.diamondBoxSymbol);
					inv.setItem(32, BoxItems.emeraldBoxSymbol);
					inv.setItem(12, BoxItems.spawnerBoxSymbol);
					inv.setItem(14, BoxItems.donatorBoxSymbol);
					
				});
				
				((Player)sender).openInventory(inv);
				return true;
			} else if(args.length >= 3){
				
				if(args[0].equalsIgnoreCase("givekey")) {
					
					Player target = Bukkit.getPlayer(args[1]);
					
					BoxType box = BoxType.IRON;
					for(BoxType type : BoxType.values()) {
						if(type.getId().equalsIgnoreCase(args[2])) {
							box = type;
							break;
						}
					}
					
					
					int amount = (args.length == 4) ? Integer.parseInt(args[3]) : 1;
					
					ItemStack keyToGive = box.getKeyItem().clone();
					keyToGive.setAmount(amount);
					
					target.getInventory().addItem(keyToGive);
					target.sendMessage(Main.PREFIX + "§aVous venez de recevoir §6" + amount + " §a clés pour la box §6" + box.getName()+ " §a.");
					sender.sendMessage(Main.PREFIX + "§aVous venez de donner §6" + amount + " §aclés pour la box §6" + box.getName()+ " §aà §6" + target.getDisplayName() + "§a.");
					return true;
				}
				
			}
			
		}
			
		return false;
	}
}


