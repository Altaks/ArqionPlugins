package fr.nowayy.helecore.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class GodModCommand implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("godmod") && sender instanceof Player) {
			
			Player target = null;
			if (args.length > 0) {
				try {
					target = Bukkit.getPlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", target.getDisplayName()));
					return true;
				}
			} else target = (Player) sender;
			
			if (!target.isInvulnerable()) {
				target.setInvulnerable(true);
				target.sendMessage(Main.prefix + Messages.GodMod_ChangeState_ToOnYourself.toString());
				if (target.getName() != sender.getName()) {
					sender.sendMessage(Main.prefix + Messages.GodMod_ChangeState_ToOnOther.toString().replace("{@target}", target.getDisplayName()));
				}
			} else {
				target.setInvulnerable(false);
				target.sendMessage(Main.prefix + Messages.GodMod_ChangeState_ToOffYourself .toString());
				if (target.getName() != sender.getName()) {
					sender.sendMessage(Main.prefix + Messages.GodMod_ChangeState_ToOffOther.toString().replace("{@target}", target.getDisplayName()));
				}
			}
			return true;
			
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageEvent e) {
		
		if (e.getEntity() instanceof Player && e.getEntity().isInvulnerable()) {
			Player p = (Player) e.getEntity();
			if ((p.getHealth() - e.getDamage()) < 1) {
				Inventory inv = p.getInventory();
				e.setCancelled(true);
				p.getInventory().setContents(inv.getContents());
			}
		}
		
	}

}
