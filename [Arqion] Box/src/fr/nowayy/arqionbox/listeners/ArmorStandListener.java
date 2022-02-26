package fr.nowayy.arqionbox.listeners;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItem;
import fr.nowayy.arqionbox.core.BoxType;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;
import fr.nowayy.arqionbox.util.ItemBuilder;

public class ArmorStandListener implements Listener{

	private Main main;

	public ArmorStandListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onArmorStandInteract(PlayerInteractAtEntityEvent e) {
		
		Player player = e.getPlayer();
		Entity entity = e.getRightClicked();
	
		if(entity instanceof ArmorStand) {
			for(BoxType box : BoxType.values()) {
				if(box.getArmorStandID().equals(entity.getUniqueId())) {
					
					if(!player.getInventory().containsAtLeast(box.getKeyItem(), 1)){
						player.sendMessage(Main.PREFIX + "§cVous devez vous munir d'une " + box.getKeyItem().getItemMeta().getDisplayName() + " pour ouvrir la box " + box.getName() + " !");
						return;
					}
					
					player.getInventory().removeItem(box.getKeyItem());
					
					Inventory inv = Bukkit.createInventory(null, 9*5, "§8Box " + box.getName() + " \u00BB");
					
					for (int i = 0; i < inv.getSize(); i++) {
						if(inv.getItem(i) != null) {
							if(inv.getItem(i).getType() == Material.AIR) {
								inv.setItem(i, BoxItems.invFiller);
							}
						} else inv.setItem(i, BoxItems.invFiller);
					}	
					
					ItemStack redstoneBlock = new ItemBuilder(Material.REDSTONE_BLOCK, 1, "§4Quitter").build();
					ItemStack redstoneTorch = new ItemBuilder(Material.REDSTONE_TORCH, 1, "§9Prix : ").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
					
					// 13 31 44
					
					inv.setItem(13, redstoneTorch);
					inv.setItem(31, redstoneTorch);
					inv.setItem(44, redstoneBlock);
					
					// 19 � 25
					
					ArrayDeque<ItemStack> deque = new ArrayDeque<ItemStack>();
					
					ItemStack droppedItem = BoxType.getDroppedItem(box).getItem();
					
					deque.addFirst(droppedItem);
					
					
					List<BoxItem> droppableItems = main.getBoxDrops().get(box);
					while(deque.size() < 7){
						Random random = new Random();
						int randomindex = random.nextInt(droppableItems.size());
						deque.add(droppableItems.get(randomindex).getItem());
					}					
					
					new BukkitRunnable() {
						
						int iterations = 7 * 2 * 5;
						
						@Override
						public void run() {
							
							if(iterations == 0) {
								player.getInventory().addItem(droppedItem);
								player.closeInventory();
								this.cancel();
							}
								
							int slot = 19;
							for(ItemStack item : deque) {
								if(slot == 26) break;
								inv.setItem(slot, item);
								slot++;
							}
						
							deque.addFirst(deque.pollLast());
							iterations--;
							
						}
					}.runTaskTimer(main, 0, (long) 0.8 * 20);
					
					player.openInventory(inv);
					
				}
			}
		}
	}
	
	@EventHandler
	public void onInvInteract(InventoryClickEvent event){
		
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().equals(event.getView().getTopInventory())){
			if(event.getView().getTitle().startsWith("§8Box ")) {
				
				if(event.getCurrentItem() != null){
					event.setCancelled(true);
					if(event.getCurrentItem().getType() == Material.REDSTONE_BLOCK && event.getCurrentItem().getItemMeta().getDisplayName().equals("§4Quitter")) {
						event.getWhoClicked().closeInventory();
					}
				} 	
			}
		}
	}
	
	@EventHandler
	public void onArmorStandBreak(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof ArmorStand) {
			for(BoxType type : BoxType.values()) {
				if(event.getEntity().getUniqueId().equals(type.getArmorStandID())) {
					
					Player player = (Player) event.getDamager();
					
					List<BoxItem> boxitems = main.getBoxDrops().get(type);
					
					ArrayDeque<ItemStack> itemToShow = new ArrayDeque<ItemStack>();
					
					for(BoxItem item : boxitems) {
						
						ItemStack mirrorItem = item.getItem().clone();
						
						ItemMeta meta = mirrorItem.getItemMeta();
						meta.setLore(Arrays.asList("§eTaux d'obtention : §6" + (item.getDropRate()/10) + "%"));
										
						mirrorItem.setItemMeta(meta);
						itemToShow.addLast(mirrorItem);
					}
					
					Inventory dropRateInv = Bukkit.createInventory(null, 9 * 6, "Taux d'obtention : " + type.getName());
					
					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						while(dropRateInv.firstEmpty() != -1 && !itemToShow.isEmpty()) {
							
							dropRateInv.addItem(itemToShow.pollFirst());
							
						}
						
					});
					
					player.openInventory(dropRateInv);
					event.setCancelled(true);
					return;
				}
			}
				
			
		}
		
	}
}
