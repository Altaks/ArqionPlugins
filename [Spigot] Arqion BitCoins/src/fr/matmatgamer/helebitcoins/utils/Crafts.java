package fr.matmatgamer.helebitcoins.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.BidonEssenceVide;
import fr.matmatgamer.helebitcoins.utils.items.Essence;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;
import fr.matmatgamer.helebitcoins.utils.items.Tablette;
import fr.matmatgamer.helebitcoins.utils.items.Wires;
import fr.matmatgamer.helebitcoins.utils.items.Wrench;
import fr.matmatgamer.helebitcoins.utils.items.HeleOres.ingots.CopperIngot;
import fr.matmatgamer.helebitcoins.utils.items.HeleOres.ingots.HelenIngot;

@SuppressWarnings("deprecation")
public class Crafts {
	
	private Main main;
	
	public Crafts(Main main) {
		this.main = main;
	}

	public void LoadAll() {
		LoadAllimSolaireCraft();
		LoadServeurCraft();
		LoadAllimEssenceCraft();
		LoadBatterieCraft();
		LoadPCCraft();
		
		LoadTabletteCraft();
		LoadWiresCraft();
		LoadWrenchCraft();
		LoadBidonCraft();
	}
	
	public void unlockCrafts(Player player) {
		for (String str : new String[] {"Wrench", "Wires", "Tablette", "Serveur", "AllimSolaire", "AllimEssence", "Batterie", "PC", "BidonVide"}) {
			player.discoverRecipe(new NamespacedKey(main, str));
		}
	}
	
	public void LoadBidonCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "BidonVide");
        ShapedRecipe recipe = new ShapedRecipe(key, new BidonEssenceVide().getItem());

        recipe.shape(" I ", "C C", "IHI");
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(new CopperIngot().getItem()));

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadWrenchCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "Wrench");
        ShapedRecipe recipe = new ShapedRecipe(key, new Wrench().getItem());

        recipe.shape("FDF", " F ", " F ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('F', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadWiresCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "Wires");
        ShapedRecipe recipe = new ShapedRecipe(key, new Wires().getItemX4());

        recipe.shape(" F ", "FCF", " F ");
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(new CopperIngot().getItem()));
        recipe.setIngredient('F', Material.BLACK_CARPET);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadTabletteCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "Tablette");
        ShapedRecipe recipe = new ShapedRecipe(key, new Tablette().getItem());

        recipe.shape("ADA", "ICV", "AEA");
        recipe.setIngredient('A', Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('V', Material.WHITE_STAINED_GLASS_PANE);
        recipe.setIngredient('E', Material.EMERALD_BLOCK);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadServeurCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "Serveur");
        ShapedRecipe recipe = new ShapedRecipe(key, new Serveurs().getItem());

        recipe.shape("HCH", "IWI", "DIE");
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('C', Material.CLOCK);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadAllimSolaireCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "AllimSolaire");
        ShapedRecipe recipe = new ShapedRecipe(key, new AllimSolaire().getItem());

        recipe.shape("VVV", "WHW", "IDI");
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('V', Material.WHITE_STAINED_GLASS);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadAllimEssenceCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "AllimEssence");
        ShapedRecipe recipe = new ShapedRecipe(key, new AllimEssence().getItem());

        recipe.shape("WOE", "HPH", "MDM");
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('M', Material.EMERALD_BLOCK);
        recipe.setIngredient('O', Material.HOPPER);
        recipe.setIngredient('E', Material.ENDER_CHEST);
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(new Essence().getItem()));

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadBatterieCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "Batterie");
        ShapedRecipe recipe = new ShapedRecipe(key, new Batterie().getItem());

        recipe.shape("HWH", "OIO", "HOH");
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('I', Material.IRON_BLOCK);

        Bukkit.addRecipe(recipe);
	}
	
	public void LoadPCCraft() {
		
        NamespacedKey key = new NamespacedKey(main, "PC");
        ShapedRecipe recipe = new ShapedRecipe(key, new PC().getItem());

        recipe.shape("HHH", "IVI", "EWE");
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(new Wires().getItem()));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(new HelenIngot().getItem()));
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('V', Material.WHITE_STAINED_GLASS);

        Bukkit.addRecipe(recipe);
	}
}
