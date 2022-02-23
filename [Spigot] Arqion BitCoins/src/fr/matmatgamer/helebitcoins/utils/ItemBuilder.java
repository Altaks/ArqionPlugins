package fr.matmatgamer.helebitcoins.utils;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

public class ItemBuilder {

	private ItemStack item;
	public Object inv;

	public ItemBuilder(Material material) {
		this.item = new ItemStack(material);
	}

	public ItemBuilder(Material material, int amount) {
		this.item = new ItemStack(material, amount);
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, short damage) {
		this.item = new ItemStack(material, amount, damage);
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, short damage, String name) {
		this.item = new ItemStack(material, amount, damage);
		ItemMeta itemMeta = this.item.getItemMeta();
		itemMeta.setDisplayName(name);
		this.item.setItemMeta(itemMeta);
	}

	/**
	 * Permet d'ajouter un enchant à l'item
	 * @param ench -> Enchant a ajouter
	 * @param level -> Niveau de l'enchant
	 * @return ItemBuilder
	 */
	public ItemBuilder addEnchant(Enchantment ench, int level) {
		this.item.addUnsafeEnchantment(ench, level);
		return this;
	}
	
	/**
	 * Permet d'ajouter un enchantement de façon legit
	 * @param ench -> Enchant a ajouter
	 * @param level -> Niveau de l'enchant
	 * @return ItemBuilder
	 * @throws IllegalArgumentException -> peut provoquer une erreur si l'enchant n'est normalement pas applicable
	 */
	public ItemBuilder addSafeEnchantement(Enchantment ench, int level) throws IllegalArgumentException {
		this.item.addEnchantment(ench, level);
		return this;
	}

	/**
	 * Permet de changer la description/le lore de l'item
	 * @param lore -> tableau / valeurs du lore
	 * @return ItemBuilder
	 */
	public ItemBuilder setLore(String... lore) {
		ItemMeta itemMeta = this.item.getItemMeta();
		itemMeta.setLore(Arrays.asList(lore));
		this.item.setItemMeta(itemMeta);
		return this;
	}

	/**
	 * Renvoie l'ItemStack
	 * @return ItemStack
	 */
	public ItemStack build() {
		return this.item;
	}

	/**
	 * Permet de mettre une couleur de cuir à l'armure
	 * @param color -> Couleur à mettre
	 * @return ItemBuilder
	 */
	public ItemBuilder setLeatherColor(Color color) throws Exception {
		LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this;
	}
	
	/**
	 * Permet de modifier la propriété incassable de l'item
	 * @param b -> booléen de la propriété
	 * @return ItemBuilder
	 */
	public ItemBuilder setUnbreakable(boolean b) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setUnbreakable(b);
		this.item.setItemMeta(meta);
		return this;
	}
	
	/**
	 * Permet de changer le nom visible de l'Item
	 * @param name -> String du nouveau nom de l'item
	 * @return ItemBuilder
	 */
	public ItemBuilder setName(String name) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder hideEnchants() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		this.item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder hideAttributes() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item.setItemMeta(meta);
		return this;
	}
	
	/**
	 * Permet d'ajouter un modificateur d'attributs à l'item en question
	 * @param attribute -> Attribut à modifier
	 * @param modifier -> Modificateur d'attribut
	 * @param hideFlags -> Booléen qui indique si les attributs de l'item sont cachés ou non dans l'inventaire
	 * @return ItemBuilder
	 */
	public ItemBuilder setAttributeModifier(Attribute attribute, AttributeModifier modifier, boolean hideFlags) {
		ItemMeta meta = this.item.getItemMeta();
		meta.addAttributeModifier(attribute, modifier);
		if(hideFlags) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item.setItemMeta(meta);
		return this;
	}
	
	/**
	 * Peut provoquer une erreur si le format du NBT n'est pas correct
	 * @param nbtTag -> String du NBTTag
	 * @return ItemBuilder
	 */
	public ItemBuilder setNBT(String nbt) throws Exception {
		NBTTagCompound compound = MojangsonParser.parse(nbt);
		net.minecraft.server.v1_15_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
		NMSItem.setTag(compound);
		this.item = CraftItemStack.asBukkitCopy(NMSItem);
		return this;
	}
	
}
