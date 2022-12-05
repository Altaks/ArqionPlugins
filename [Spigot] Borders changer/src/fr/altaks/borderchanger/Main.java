package fr.altaks.borderchanger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin implements Listener, CommandExecutor {
	
	public static final HashMap<UUID, Integer> radiuses = new HashMap<>();
	
	@Override
	public void onEnable() {
		getCommand("getchanger").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("getchanger")) {
			
			Player player = (Player)sender;
			radiuses.put(player.getUniqueId(), ((args.length > 0) ? Integer.parseInt(args[0]) : 5));
			
			ItemStack item = new ItemStack(Material.STICK, 1);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setDisplayName("§6Border changer");
			
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
			
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerUseBorderChanger(PlayerInteractEvent event) {
		
		// check interaction
		// get targeted block
		// scan radius
		// get highest block
		// check composition
			// if yes, then suppress and add bedrock
		
		if(!event.hasItem()) return;
		if(event.isBlockInHand()) return;
		if(event.getItem().getType() != Material.STICK) return;
		if(!event.getItem().getItemMeta().getDisplayName().equals("§6Border changer")) return;
		
		Player player = event.getPlayer();
		
		Location targetedBlock = player.getLineOfSight(null, 100).get(0).getLocation();
		
		int playerRadius = radiuses.get(player.getUniqueId());
		
		for(int rx = -playerRadius; rx <= playerRadius; rx++) for(int rz = -playerRadius; rz <= playerRadius; rz++) {
			// get highest block at x,z
			Block block = targetedBlock.getWorld().getBlockAt(
					targetedBlock.getBlockX() + rx, 
					targetedBlock.getWorld().getHighestBlockYAt(targetedBlock.getBlockX() + rx, targetedBlock.getBlockZ() + rz),
					targetedBlock.getBlockZ() + rz
			);
			
			if(block.getType() != Material.IRON_BARS) continue;
			
			Block down_1 = block.getRelative(BlockFace.DOWN);
			if(down_1.getType() != Material.COBBLESTONE) continue;
			

			Block down_2 = down_1.getRelative(BlockFace.DOWN);
			if(down_2.getType() != Material.COBBLESTONE) continue;
			
			// good blocks
			
			Arrays.asList(block, down_1, down_2).forEach(e -> e.setType(Material.AIR, false));
			down_2.getRelative(BlockFace.DOWN).setType(Material.BEDROCK);
			
		}
		
		
	}

}
