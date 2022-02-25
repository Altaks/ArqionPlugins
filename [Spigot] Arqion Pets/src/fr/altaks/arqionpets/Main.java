package fr.altaks.arqionpets;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.arqionpets.commands.GiveItems;
import fr.altaks.arqionpets.commands.Pet;
import fr.altaks.arqionpets.commands.SpawnPet;
import fr.altaks.arqionpets.commands.SpawnPet.PetPlayerCouple;
import fr.altaks.arqionpets.listeners.EntityDropItemsListener;
import fr.altaks.arqionpets.listeners.ItemStackBurningListener;
import fr.altaks.arqionpets.listeners.PetInfuserInteractionListener;
import fr.altaks.arqionpets.listeners.PetMenuInteractionListener;
import fr.altaks.arqionpets.listeners.RecipeGive;
import fr.altaks.arqionpets.task.PetLocationUpdateTask;

public class Main extends JavaPlugin {
	
	public static final String PREFIX = "§7[§bArqionPets§7] \u00BB §r";
	public static boolean debugMode = false;
	
	private Deque<SpawnPet.PetPlayerCouple> petsArmorstand = new ArrayDeque<SpawnPet.PetPlayerCouple>();
	private List<Player> hasPetEquiped = new ArrayList<Player>();
	
	private Collection<NamespacedKey> recipeskeys = new ArrayList<NamespacedKey>();
	
	public Listener[] listeners = {
			new ItemStackBurningListener(), new PetInfuserInteractionListener(), new PetMenuInteractionListener(this), new RecipeGive(this), new EntityDropItemsListener()
	};				 
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		if(getConfig().isSet("debug-mode")) debugMode = getConfig().getBoolean("debug-mode");
		
		for(Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
		
		getCommand("giveitem").setTabCompleter(new GiveItems());		
		getCommand("giveitem").setExecutor(new GiveItems());
		
		getCommand("spawnpet").setTabCompleter(new SpawnPet(this));		
		getCommand("spawnpet").setExecutor(new SpawnPet(this));
		
		getCommand("pet").setExecutor(new Pet(this));
		
		new PetLocationUpdateTask(this).runTaskTimerAsynchronously(this, 0, 1);
		loadCrafts();
	}
	
	@Override
	public void onDisable() {
		
		Bukkit.getScheduler().cancelTasks(this);
		for(PetPlayerCouple couple : getPetsArmorstand()) couple.getStand().remove();
		petsArmorstand.clear();
		hasPetEquiped.clear();
		
	}
	
	@SuppressWarnings("deprecation")
	private void loadCrafts() {
		
		NamespacedKey 	witherkey = new NamespacedKey(this, "wither_pet_converter"), 
						processor_key = new NamespacedKey(this, "processor"), 
						alim_key = new NamespacedKey(this, "alimentation"), 
						pet_infuser_key = new NamespacedKey(this, "pet_infuser");
		
		this.recipeskeys.add(witherkey);
		this.recipeskeys.add(processor_key);
		this.recipeskeys.add(alim_key);
		this.recipeskeys.add(pet_infuser_key);
		
		ShapedRecipe witherconverter = new ShapedRecipe(witherkey,PluginItems.wither_pet_converter);
		witherconverter.shape(".a.","bcb",".d.");
		witherconverter.setIngredient('a', Material.ZOMBIE_HEAD);
		witherconverter.setIngredient('.', Material.AIR);
		witherconverter.setIngredient('b', Material.IRON_BLOCK);
		witherconverter.setIngredient('c', Material.NETHER_STAR);
		witherconverter.setIngredient('d', Material.DIAMOND_BLOCK);
		
		ShapedRecipe processor = new ShapedRecipe(processor_key, PluginItems.processor);
		processor.shape("abc","ded","cfa");
		processor.setIngredient('a', new RecipeChoice.ExactChoice(PluginItems.CrosspluginItems.actinium_nugget));
		processor.setIngredient('b', Material.CLOCK);
		processor.setIngredient('c', new RecipeChoice.ExactChoice(PluginItems.cable));
		processor.setIngredient('d', Material.REDSTONE_BLOCK);
		processor.setIngredient('e', new RecipeChoice.ExactChoice(PluginItems.CrosspluginItems.actinium_block));
		processor.setIngredient('f', Material.NETHER_STAR);
		
		ShapedRecipe pet_infuser = new ShapedRecipe(pet_infuser_key, PluginItems.pet_infuser);
		pet_infuser.shape("abc","ded", "cbf");
		pet_infuser.setIngredient('a', Material.DAYLIGHT_DETECTOR);
		pet_infuser.setIngredient('b', new RecipeChoice.ExactChoice(PluginItems.CrosspluginItems.actinium_block));
		pet_infuser.setIngredient('c', new RecipeChoice.ExactChoice(PluginItems.cable));
		pet_infuser.setIngredient('d', new RecipeChoice.ExactChoice(PluginItems.alimentation));
		pet_infuser.setIngredient('e', new RecipeChoice.ExactChoice(PluginItems.processor));
		pet_infuser.setIngredient('f', Material.SKELETON_SKULL);

		ShapedRecipe alimentation = new ShapedRecipe(alim_key,PluginItems.alimentation);
		alimentation.shape(".a.","bcb","ded");
		alimentation.setIngredient('a', Material.DAYLIGHT_DETECTOR);
		alimentation.setIngredient('b', new RecipeChoice.ExactChoice(PluginItems.CrosspluginItems.copper_ingot));
		alimentation.setIngredient('c', Material.END_CRYSTAL);
		alimentation.setIngredient('d', new RecipeChoice.ExactChoice(PluginItems.cable));
		alimentation.setIngredient('e', new RecipeChoice.ExactChoice(PluginItems.CrosspluginItems.platinium_ingot));

		getServer().addRecipe(witherconverter);
		getServer().addRecipe(processor);
		getServer().addRecipe(pet_infuser);
		getServer().addRecipe(alimentation);
	}

	public Deque<SpawnPet.PetPlayerCouple> getPetsArmorstand() {
		return petsArmorstand;
	}

	public List<Player> getHasPetEquiped() {
		return hasPetEquiped;
	}

	public Collection<NamespacedKey> getRecipeskeys() {
		return recipeskeys;
	}

}
