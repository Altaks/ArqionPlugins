package fr.altaks.arqionpets;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.altaks.arqionpets.utils.ItemManager;

public class PetCraft {
	
	private ItemStack output;
	private ItemStack[][] components;
	
	public PetCraft(ItemStack output, ItemStack[][] components) {
		this.output = output;
		this.components = components;
	}
	
	public boolean matchRecipe(Inventory petInfuserInv) {
		
		// collect matrix
		ItemStack[][] items = new ItemStack[3][3];
		for(int line = 0; line < 3; line++) for(int row = 0; row < 3; row++) {
			items[line][row] = petInfuserInv.getItem(/*11 est l'offset de l'inv*/ 11 + (int)(line*9 + row));
		}
		
		for(int line = 0; line < 3; line++) for(int row = 0; row < 3; row++) { // on check entre les deux matrix de crafts
			if(items[line][row] == null) return false;
			if(!new ItemManager().compare(this.components[line][row], items[line][row]) && this.components[line][row].getAmount() == items[line][row].getAmount()) return false;
		}
		
		// si il n'y a rien qui bloque, la recipe matche
		return true;
	}
	
	public ItemStack getOutput() {
		return this.output;
	}

	public ItemStack[][] getComponents() {
		return components;
	}
	
}

class PetCrafts {
	
	public static final PetCraft bat = new PetCraft(PluginItems.bat_pet, PetComponents.bat_components);
	public static final PetCraft silverfish = new PetCraft(PluginItems.silverfish_pet, PetComponents.silverfish_components);
	public static final PetCraft parrot = new PetCraft(PluginItems.parrot_pet, PetComponents.parrot_components);
	public static final PetCraft phantom = new PetCraft(PluginItems.phantom_pet, PetComponents.phantom_components);
	public static final PetCraft slime = new PetCraft(PluginItems.slime_pet, PetComponents.slime_components);
	public static final PetCraft pig = new PetCraft(PluginItems.pig_pet, PetComponents.pig_components);
	public static final PetCraft edrag = new PetCraft(PluginItems.ender_drag_pet, PetComponents.edrag_components);
	public static final PetCraft chicken = new PetCraft(PluginItems.chicken_pet, PetComponents.chicken_components);
	
	public static final PetCraft[] all_pets_crafts = {
		bat, silverfish, parrot, phantom, slime, pig, edrag, chicken	
	};
}

class PetComponents {
	
	public static final ItemStack[][] bat_components = {
			{ new ItemManager.ItemBuilder(Material.OBSIDIAN, 64).build(), new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.OBSIDIAN, 64).build()},
			{ new ItemManager.ItemBuilder(Material.FEATHER, 8).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(Material.FEATHER, 8).build()},
			{ new ItemManager.ItemBuilder(Material.OBSIDIAN, 64).build(), new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.OBSIDIAN, 64).build()},
	};
	
	public static final ItemStack[][] silverfish_components = {
			{ new ItemManager.ItemBuilder(Material.STONE, 64).build(),    new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.STONE, 64).build()},
			{ new ItemManager.ItemBuilder(Material.DIORITE, 1).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(Material.DIORITE, 1).build()},
			{ new ItemManager.ItemBuilder(Material.STONE, 64).build(),    new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.STONE, 64).build()},
	};
	
	public static final ItemStack[][] parrot_components = {
			{ new ItemManager.ItemBuilder(Material.FEATHER, 64).build(), new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.COOKIE, 64).build()},
			{ new ItemManager.ItemBuilder(PluginItems.flying_ring).setAmount(1).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(PluginItems.flying_ring).setAmount(1).build()},
			{ new ItemManager.ItemBuilder(Material.COOKIE, 64).build(),  new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.FEATHER, 64).build()},
	};
	
	public static final ItemStack[][] phantom_components = {
			{ new ItemManager.ItemBuilder(Material.PHANTOM_MEMBRANE, 32).build(), new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.LEATHER, 32).build()},
			{ new ItemManager.ItemBuilder(Material.ELYTRA, 1).build(),            new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(Material.DIORITE, 1).build()},
			{ new ItemManager.ItemBuilder(Material.LEATHER, 32).build(),          new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.PHANTOM_MEMBRANE, 32).build()},
	};
	
	public static final ItemStack[][] slime_components = {
			{ new ItemManager.ItemBuilder(Material.SLIME_BALL, 64).build(),   new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.SLIME_BALL, 64).build()},
			{ new ItemManager.ItemBuilder(Material.SLIME_BLOCK, 1).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(Material.SLIME_BLOCK, 1).build()},
			{ new ItemManager.ItemBuilder(Material.SLIME_BALL, 64).build(),   new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.SLIME_BALL, 64).build()},
	};
	
	public static final ItemStack[][] pig_components = {
			{ new ItemManager.ItemBuilder(Material.PORKCHOP, 64).build(),                  new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.PORKCHOP, 64).build()},
			{ new ItemManager.ItemBuilder(PluginItems.animal_fat).setAmount(16).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(PluginItems.animal_fat).setAmount(16).build()},
			{ new ItemManager.ItemBuilder(Material.PORKCHOP, 64).build(),                  new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.PORKCHOP, 64).build()},
	};
	
	public static final ItemStack[][] edrag_components = {
			{ new ItemManager.ItemBuilder(PluginItems.enchanted_feather).setAmount(16).build(), new ItemManager.ItemBuilder(PluginItems.compressed_pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(PluginItems.enchanted_feather).setAmount(16).build()},
			{ new ItemManager.ItemBuilder(Material.DRAGON_EGG, 1).build(),                      new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),             new ItemManager.ItemBuilder(Material.DRAGON_EGG, 1).build()},
			{ new ItemManager.ItemBuilder(PluginItems.enchanted_feather).setAmount(16).build(), new ItemManager.ItemBuilder(PluginItems.compressed_pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(PluginItems.enchanted_feather).setAmount(16).build()},
	};
	

	public static final ItemStack[][] chicken_components = {
			{ new ItemManager.ItemBuilder(Material.FEATHER, 64).build(),                  new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.FEATHER, 64).build()},
			{ new ItemManager.ItemBuilder(PluginItems.fall_ring).setAmount(16).build(),   new ItemManager.ItemBuilder(PluginItems.pet_core).setAmount(1).build(),  new ItemManager.ItemBuilder(PluginItems.fall_ring).setAmount(16).build()},
			{ new ItemManager.ItemBuilder(Material.FEATHER, 64).build(),                  new ItemManager.ItemBuilder(PluginItems.pet_dust).setAmount(64).build(), new ItemManager.ItemBuilder(Material.FEATHER, 64).build()},
	};
	
}