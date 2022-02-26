package fr.matmatgamer.helebitcoins.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;
import fr.matmatgamer.helebitcoins.utils.items.AdminWrench;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.Essence;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;
import fr.matmatgamer.helebitcoins.utils.items.Tablette;
import fr.matmatgamer.helebitcoins.utils.items.Wires;
import fr.matmatgamer.helebitcoins.utils.items.Wrench;

public class CommandAbitcoins implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		Serveurs Serveurs = new Serveurs();
		AllimSolaire AllimSolaire = new AllimSolaire();
		PC PC = new PC();
		AllimEssence AllimEssence = new AllimEssence();
		Batterie Batterie = new Batterie();
		Wrench Wrench = new Wrench();
		AdminWrench AdminWrench = new AdminWrench();
		Wires Wires = new Wires();
		Tablette Tablette = new Tablette();
		Essence Essence = new Essence();
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			Inventory inv = Bukkit.createInventory(null, 36, Main.prefix + "§4Menu give - ADMIN");
			
			inv.setItem(10, Serveurs.getItem());
			inv.setItem(11, AllimSolaire.getItem());
			inv.setItem(12, PC.getItem());
			inv.setItem(13, AllimEssence.getItem());
			inv.setItem(14, Batterie.getItem());
			inv.setItem(15, Wrench.getItem());
			inv.setItem(16, Wires.getItem());
			inv.setItem(21, Tablette.getItem());
			inv.setItem(23, Essence.getItem());
			inv.setItem(22, AdminWrench.getItem());
			
			ItemStack glassPane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").hideAttributes().build();
			for (int i = 0; i < inv.getSize(); i++) {
				if(inv.getItem(i) == null) {
					inv.setItem(i, glassPane);
				}
				
			}
			
			player.openInventory(inv);
			
		}
		
		return false;
	}

}
