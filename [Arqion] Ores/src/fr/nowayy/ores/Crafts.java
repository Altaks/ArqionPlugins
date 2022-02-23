package fr.nowayy.ores;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import fr.nowayy.ores.utils.ItemManager;
import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;
import fr.nowayy.ores.utils.Items;

@SuppressWarnings("deprecation")
public class Crafts {
	
	private static Items items = new Items();
	public PrebuiltItems prebuild = new ItemManager.PrebuiltItems();
	
	public static void loadAll() {
		hammerRecipe();
		rockbreakerRecipe();
		compressorRecipe();
		cobbleGeneratorRecipe();
		cobbleGenT1();
		cobbleGenT2();
		cobbleGenT3();
		cobbleGenT4();
		cobbleGenT5();
	}
	
	public static void hammerRecipe() {
		ShapedRecipe hammerRecipe = new ShapedRecipe(items.hammerItem);
		hammerRecipe.shape("SSS", "SLS", " L ");
		hammerRecipe.setIngredient('S', Material.STONE);
		hammerRecipe.setIngredient('L', Material.OAK_LOG);
		
		Bukkit.addRecipe(hammerRecipe);
	}
	
	public static void rockbreakerRecipe() {
		ShapedRecipe rockbreakerRecipe = new ShapedRecipe(items.rockBreaker);
		rockbreakerRecipe.shape("CCC", "MAM", "PPP");
		rockbreakerRecipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		rockbreakerRecipe.setIngredient('M', new RecipeChoice.ExactChoice(items.cable));
		rockbreakerRecipe.setIngredient('P', new RecipeChoice.ExactChoice(PrebuiltItems.platinium_block));
		
		Bukkit.addRecipe(rockbreakerRecipe);
	}
	
	public static void compressorRecipe() {
		ShapedRecipe compressorRecipe = new ShapedRecipe(items.compressor);
		compressorRecipe.shape("CPM", "PAP", "QPZ");
		compressorRecipe.setIngredient('C', new RecipeChoice.ExactChoice(PrebuiltItems.cobalt_block));
		compressorRecipe.setIngredient('P', Material.PISTON);
		compressorRecipe.setIngredient('M', new RecipeChoice.ExactChoice(items.cable));
		compressorRecipe.setIngredient('A', Material.COBBLESTONE);
		compressorRecipe.setIngredient('Q', new RecipeChoice.ExactChoice(PrebuiltItems.actinium_ingot));
		compressorRecipe.setIngredient('Z', new RecipeChoice.ExactChoice(PrebuiltItems.copper_block));
		
		Bukkit.addRecipe(compressorRecipe);
	}
	
	public static void cobbleGeneratorRecipe() {
		ShapedRecipe cobbleGeneratorRecipe = new ShapedRecipe(items.cobbleGen);
		cobbleGeneratorRecipe.shape("TCT", "AZA", "PPP");
		cobbleGeneratorRecipe.setIngredient('T', new RecipeChoice.ExactChoice(PrebuiltItems.platinium_block));
		cobbleGeneratorRecipe.setIngredient('C', new RecipeChoice.ExactChoice(PrebuiltItems.cobalt_block));
		cobbleGeneratorRecipe.setIngredient('A', new RecipeChoice.ExactChoice(items.cable));
		cobbleGeneratorRecipe.setIngredient('Z', new RecipeChoice.ExactChoice(PrebuiltItems.actinium_block));
		cobbleGeneratorRecipe.setIngredient('P', new RecipeChoice.ExactChoice(items.compressCobble));
		
		Bukkit.addRecipe(cobbleGeneratorRecipe);
	}

	public static void cobbleGenT1() {
		ShapedRecipe cobbleGenT1Recipe = new ShapedRecipe(items.cobbleGenT1);
		cobbleGenT1Recipe.shape("CAC", "ACA", "CAC");
		cobbleGenT1Recipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		cobbleGenT1Recipe.setIngredient('A', new RecipeChoice.ExactChoice(PrebuiltItems.aluminium_block));
		
		Bukkit.addRecipe(cobbleGenT1Recipe);
	}

	public static void cobbleGenT2() {
		ShapedRecipe cobbleGenT2Recipe = new ShapedRecipe(items.cobbleGenT2);
		cobbleGenT2Recipe.shape("CAC", "APA", "CAC");
		cobbleGenT2Recipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		cobbleGenT2Recipe.setIngredient('A', new RecipeChoice.ExactChoice(PrebuiltItems.copper_block));
		cobbleGenT2Recipe.setIngredient('P', new RecipeChoice.ExactChoice(items.cobbleGenT1));
		
		Bukkit.addRecipe(cobbleGenT2Recipe);
	}
	
	public static void cobbleGenT3() {
		ShapedRecipe cobbleGenT3Recipe = new ShapedRecipe(items.cobbleGenT3);
		cobbleGenT3Recipe.shape("CAC", "APA", "CAC");
		cobbleGenT3Recipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		cobbleGenT3Recipe.setIngredient('A', new RecipeChoice.ExactChoice(PrebuiltItems.copper_block));
		cobbleGenT3Recipe.setIngredient('P', new RecipeChoice.ExactChoice(items.cobbleGenT2));
		
		Bukkit.addRecipe(cobbleGenT3Recipe);
	}
	
	public static void cobbleGenT4() {
		ShapedRecipe cobbleGenT4Recipe = new ShapedRecipe(items.cobbleGenT4);
		cobbleGenT4Recipe.shape("CAC", "APA", "CAC");
		cobbleGenT4Recipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		cobbleGenT4Recipe.setIngredient('A', new RecipeChoice.ExactChoice(PrebuiltItems.copper_block));
		cobbleGenT4Recipe.setIngredient('P', new RecipeChoice.ExactChoice(items.cobbleGenT3));
		
		Bukkit.addRecipe(cobbleGenT4Recipe);
	}
	
	public static void cobbleGenT5() {
		ShapedRecipe cobbleGenT5Recipe = new ShapedRecipe(items.cobbleGenT5);
		cobbleGenT5Recipe.shape("CAC", "APA", "CAC");
		cobbleGenT5Recipe.setIngredient('C', new RecipeChoice.ExactChoice(items.compressCobble));
		cobbleGenT5Recipe.setIngredient('A', new RecipeChoice.ExactChoice(PrebuiltItems.copper_block));
		cobbleGenT5Recipe.setIngredient('P', new RecipeChoice.ExactChoice(items.cobbleGenT4));
		
		Bukkit.addRecipe(cobbleGenT5Recipe);
	}
		
	}
