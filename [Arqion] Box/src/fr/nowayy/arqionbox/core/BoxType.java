package fr.nowayy.arqionbox.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;

public enum BoxType {
	
	SPAWNER(BoxItems.spawnerBoxSymbol, BoxItems.spawnerBoxKey, UUID.fromString("d926543c-f34a-4bea-b0e7-51ad032b2490"),"spawnerbox.yml", "spawner", "§5Spawner", 1_000_000.0d),
	DONATOR(BoxItems.donatorBoxSymbol, BoxItems.donatorBoxKey, UUID.fromString("2f8901f1-73de-42f4-b084-5ee58e259823"),"donatorbox.yml", "donator", "§8Donator", Double.MAX_VALUE),
	EMERALD(BoxItems.emeraldBoxSymbol, BoxItems.emeraldBoxKey, UUID.fromString("94a487e0-9b98-4957-b4f8-ccb82cdc909c"),"emeraldbox.yml", "emerald", "§aEmerald", 500_000.0d),
	DIAMOND(BoxItems.diamondBoxSymbol, BoxItems.diamondBoxKey, UUID.fromString("7f668b17-0d78-4d71-a1ab-4b6206bb8b02"),"diamondbox.yml","diamond", "§bDiamond", 200_000.0d),
	GOLD(BoxItems.goldBoxSymbol, BoxItems.goldBoxKey, UUID.fromString("753dc76d-f15c-4cfa-8654-9927a5309ffe"),"goldbox.yml", "gold", "§6Gold", 100_000.0d),
	IRON(BoxItems.ironBoxSymbol, BoxItems.ironBoxKey, UUID.fromString("e7a60e55-4ddb-4f3a-87f2-c907ac95888b"),"ironbox.yml", "iron", "§7Iron", 50_000.0d);

	private ItemStack symbol, key;
	private UUID armorstandID;
	private String filename, name, id;
	private double price;
	
	// item-1: <type>/<amount>/<nbt>/<dropRate>

	private BoxType(ItemStack symbol, ItemStack key, UUID armorstandID, String filename, String id, String name, double price) {
		this.symbol = symbol;
		this.key = key;
		this.armorstandID = armorstandID;
		this.filename = filename;
		this.name = name;
		this.price = price;
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static BoxItem getDroppedItem(BoxType type) {
		List<BoxItem> droppedItems = new ArrayList<BoxItem>(); 
		for(BoxItem boxItem : Main.getBoxDrops(type)) {
			float rate = new Random().nextFloat();
			if(rate < boxItem.getDropRate() / 1000) {
				droppedItems.add(boxItem);
			}
		}
		
		BoxItem[] itemArray = droppedItems.toArray(new BoxItem[droppedItems.size()]);
		bubbleSort(itemArray);
		
		BoxItem droppedItem = itemArray[0];
		return droppedItem;
	}
	
	public static final void bubbleSort(final BoxItem[] arr) {

		int n = arr.length;
		for (int i = 0; i < n - 1; i++) for (int j = 0; j < n - i - 1; j++){
			
			if (arr[j].getDropRate() > arr[j + 1].getDropRate()) {
					
					// swap arr[j+1] and arr[j]
					
					BoxItem temp = arr[j];
					
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
			}
		}
	}
	
	public UUID getArmorStandID() {
		return(armorstandID);
	}
	
	public String getFileName() {
		return filename;
	}
	
	public ItemStack getKeyItem() {
		return this.key;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public ItemStack getSymbolItem() {
		return this.symbol;
	}
	
	public String getId() {
		return this.id;
	}
	
	
}