package fr.matmatgamer.helebitcoins.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
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

public class Abitcoins implements Listener {

	@EventHandler
	public void Onclick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		
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
		
		if(current == null) return;
		
		if(e.getView().getTitle().equals(Main.prefix + "§4Menu give - ADMIN")) {
			
			e.setCancelled(true);
			Inventory inv = player.getInventory();
			
			switch (current.getType()) {
			case OBSERVER:
				inv.addItem(Serveurs.getItem());
				break;
			case DAYLIGHT_DETECTOR:
				inv.addItem(AllimSolaire.getItem());
				break;
			case DISPENSER:
				inv.addItem(PC.getItem());
				break;
			case BLAST_FURNACE:
				inv.addItem(AllimEssence.getItem());
				break;
			case ENDER_CHEST:
				inv.addItem(Batterie.getItem());
				break;
			case TRIPWIRE_HOOK:
				inv.addItem(Wrench.getItem());
				break;
			case REDSTONE:
				inv.addItem(Wires.getItem());
				break;
			case BLAZE_ROD:
				inv.addItem(AdminWrench.getItem());
				break;
			case BOOK:
				inv.addItem(Tablette.getItem());
				break;
			case WATER_BUCKET:
				inv.addItem(Essence.getItem());
				break;
			default:
				break;
			}
		}

	}
}
