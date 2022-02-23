package fr.nowayy.helecore.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.ItemBuilder;
import fr.nowayy.helecore.utils.Messages;

public class OpenInventoryCommand implements CommandExecutor, Listener {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("openinv") && sender instanceof Player && args.length == 1) {
			Player user = (Player) sender;
			
			Player target = null;
			
			try {
				target = Bukkit.getPlayer(args[0]);
				Inventory inv = Bukkit.createInventory(null, 45, "§6Inventaire");
				inv.setContents(target.getInventory().getContents());
				

				ItemStack health = new ItemBuilder(Material.RED_DYE, (int) target.getHealth(), "§e"+target.getHealth() +"/"+target.getMaxHealth()).build();
				ItemStack food = new ItemBuilder(Material.COOKED_BEEF, (int) target.getFoodLevel(), "§e"+target.getFoodLevel()+"/20").build();
				inv.setItem(43, health);
				inv.setItem(44, food);
				
				user.openInventory(inv);
			} catch(NullPointerException e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
			}
		}

		return false;
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		Inventory inv = e.getInventory();
		if(((Command) inv).getName().equalsIgnoreCase("§6Inventaire")) {
			if (current == null) return;
			e.setCancelled(false);
			switch(e.getSlot()) {
			
			case 52:
				e.setCancelled(true);
				break;
			case 53:
				e.setCancelled(true);
			}
		}
			
			
		
	}

}





