package fr.nowayy.ores.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTBase;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
 
public class ItemManager {
    
    public static class PrebuiltItems {
        
        public static final ItemStack previousArrow = new ItemBuilder(Material.ARROW, 1, "§cPrevious").build();
        public static final ItemStack nextArrow = new ItemBuilder(Material.ARROW, 1, "§cNext").build();
        public static final ItemStack inventoryFillingGlassPane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, " ").build();
        
        public static final ItemStack hubAndLobbyCompass = new ItemBuilder(Material.COMPASS, 1, "§6Teleport").build();
        
        public static final ItemStack     	aluminium_block = new ItemManager.ItemBuilder(Material.IRON_BLOCK, 1, "§8Bloc d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                aluminium_ingot = new ItemManager.ItemBuilder(Material.IRON_INGOT, 1, "§8Lingot d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                aluminium_nugget = new ItemManager.ItemBuilder(Material.IRON_NUGGET, 1, "§8Pépite d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                
							                copper_block = new ItemManager.ItemBuilder(Material.GOLD_BLOCK, 1, "§6Bloc de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                copper_ingot = new ItemManager.ItemBuilder(Material.GOLD_INGOT, 1, "§6Lingot de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                copper_nugget = new ItemManager.ItemBuilder(Material.GOLD_NUGGET, 1, "§6Pépite de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                
							                cobalt_block = new ItemManager.ItemBuilder(Material.PRISMARINE_BRICKS, 1, "§1Bloc de cobalt").addFakeEnchant().build(),
							                cobalt_ingot = new ItemManager.ItemBuilder(Material.PRISMARINE_SHARD, 1, "§1Crystal de cobalt").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                cobalt_nugget = new ItemManager.ItemBuilder(Material.PRISMARINE_CRYSTALS, 1, "§1Morceaux de cobalt").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                
							                platinium_block = new ItemManager.ItemBuilder(Material.DIAMOND_BLOCK, 1, "§bBloc de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                platinium_ingot = new ItemManager.ItemBuilder(Material.DIAMOND, 1, "§bLingot de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                platinium_nugget = new ItemManager.ItemBuilder(Material.BLUE_DYE, 1, "§Poudre de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                
							                actinium_block = new ItemManager.ItemBuilder(Material.BEACON, 1, "Bloc d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                actinium_ingot = new ItemManager.ItemBuilder(Material.NETHER_STAR, 1, "§cLingot d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                actinium_nugget = new ItemManager.ItemBuilder(Material.GHAST_TEAR, 1, "§cPépite d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
							                
					                		coal = new ItemManager.ItemBuilder(Material.COAL).build(),
											iron_nugget = new ItemManager.ItemBuilder(Material.IRON_NUGGET).build(),
											gold_nugget = new ItemManager.ItemBuilder(Material.GOLD_NUGGET).build(),
											redstone = new ItemManager.ItemBuilder(Material.REDSTONE).build(),
											lapis = new ItemManager.ItemBuilder(Material.LAPIS_LAZULI).build(),
											quartz = new ItemManager.ItemBuilder(Material.QUARTZ).build(),
											diamond = new ItemManager.ItemBuilder(Material.DIAMOND).build(),
											emerald = new ItemManager.ItemBuilder(Material.EMERALD).build();
        
    }
    
    public boolean lightCompare(ItemStack firstItem, ItemStack secondItem) {
        if(firstItem.getType() == secondItem.getType()) {
            if(firstItem.hasItemMeta() && secondItem.hasItemMeta()) {
                // verif les metas
                ItemMeta firstMeta = firstItem.getItemMeta();
                ItemMeta secondMeta = secondItem.getItemMeta();
                
                if(!firstMeta.equals(secondMeta)) return false;
                if((firstMeta.hasDisplayName() && !secondMeta.hasDisplayName()) || (!firstMeta.hasDisplayName() && secondMeta.hasDisplayName())) return false;
 
            }
            // v�rif si les un des deux a un meta et pas l'autre
            if((firstItem.hasItemMeta() && !secondItem.hasItemMeta()) || (!firstItem.hasItemMeta() && secondItem.hasItemMeta())) return false;
        } else return false;
        return true;
    }
    
    public boolean compare(ItemStack firstItem, ItemStack secondItem) {
        if(firstItem.getType() == secondItem.getType()) {
            if(firstItem.hasItemMeta() && secondItem.hasItemMeta()) {
                // verif les metas
                ItemMeta firstMeta = firstItem.getItemMeta();
                ItemMeta secondMeta = secondItem.getItemMeta();
                
                if(!firstMeta.equals(secondMeta)) return false;
                
                if(firstMeta.hasDisplayName() && secondMeta.hasDisplayName()) {
                    
                    String firstDisplayName = firstMeta.getDisplayName();
                    String secondDisplayName = secondMeta.getDisplayName();
                    
                    if(!firstDisplayName.equals(secondDisplayName)) return false;
                }
                
                if((firstMeta.hasDisplayName() && !secondMeta.hasDisplayName()) || (!firstMeta.hasDisplayName() && secondMeta.hasDisplayName())) return false;
 
            }
            // v�rif si les un des deux a un meta et pas l'autre
            if((firstItem.hasItemMeta() && !secondItem.hasItemMeta()) || (!firstItem.hasItemMeta() && secondItem.hasItemMeta())) return false;
        } else return false;
        return true;
    }
    
    public boolean deepCompare(ItemStack firstItem, ItemStack secondItem) {
        if(firstItem.getType() == secondItem.getType()) {
            if(firstItem.hasItemMeta() && secondItem.hasItemMeta()) {
                // verif les metas
                ItemMeta firstMeta = firstItem.getItemMeta();
                ItemMeta secondMeta = secondItem.getItemMeta();
                
                if(!firstMeta.equals(secondMeta)) return false;
                
                if(firstMeta.hasDisplayName() && secondMeta.hasDisplayName()) {
                    
                    String firstDisplayName = firstMeta.getDisplayName();
                    String secondDisplayName = secondMeta.getDisplayName();
                    
                    if(!firstDisplayName.equals(secondDisplayName)) return false;
                }
                
                if((firstMeta.hasDisplayName() && !secondMeta.hasDisplayName()) || (!firstMeta.hasDisplayName() && secondMeta.hasDisplayName())) return false;
 
            }
            // v�rif si les un des deux a un meta et pas l'autre
            if((firstItem.hasItemMeta() && !secondItem.hasItemMeta()) || (!firstItem.hasItemMeta() && secondItem.hasItemMeta())) return false;
            
            ItemBuilder firstBuilder = new ItemBuilder(firstItem);
            ItemBuilder secondBuilder = new ItemBuilder(secondItem);
            
            if(firstBuilder.hasAlreadyAnNBT() && secondBuilder.hasAlreadyAnNBT()) {
                
                String firstNBT = firstBuilder.getFullNBTasString();
                String secondNBT = firstBuilder.getFullNBTasString();
                
                if(!firstNBT.equals(secondNBT)) return false;
                
            }
            
            if((firstBuilder.hasAlreadyAnNBT() && !secondBuilder.hasAlreadyAnNBT()) || (!firstBuilder.hasAlreadyAnNBT() && secondBuilder.hasAlreadyAnNBT())) return false;
            
        } else return false;
        return true;
    }
    
    public static String toString(ItemStack item) {
        
        String material = item.getType().getKey().toString();
        int amount = item.getAmount();
        String nbt = CraftItemStack.asNMSCopy(item).getOrCreateTag().toString();
        
        return material+"///"+amount+"///"+nbt;
    }
    
    public static ItemStack fromString(String itemdata) throws NumberFormatException, ItemBuildingError {
        String[] data = itemdata.split("///");
        return new ItemBuilder(data[0], Integer.parseInt(data[1])).setFullNBT(data[2]).build();
    }
    
    public static class ItemBuilder {
        
        protected ItemStack item;
        protected ItemMeta meta;
        
        public ItemStack build() {
            ItemStack itemToBuild = this.item.clone();
            itemToBuild.setItemMeta(meta);
            return itemToBuild;
        }
        
        public ItemBuilder clone() {
            return new ItemBuilder(this.build());
        }
        
        public String toString() {
            String material = item.getType().getKey().toString();
            int amount = item.getAmount();
            String nbt = CraftItemStack.asNMSCopy(item).getOrCreateTag().toString();
            
            return material+"///"+amount+"///"+nbt;
        }
        
        public ItemBuilder(Material material) {
            this.item = new ItemStack(material);
            this.meta = item.getItemMeta();
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(Material material, short damage) {
            this.item = new ItemStack(material, 0, (short)damage);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(Material material, int amount) {
            this.item = new ItemStack(material, amount);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(Material material, int amount, String displayName) {
            this.item = new ItemStack(material, amount);
            this.meta = item.getItemMeta();
            this.meta.setDisplayName(displayName);
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(Material material, int amount, String displayName, short damage) {
            this.item = new ItemStack(material, amount, damage);
            this.meta = item.getItemMeta();
            this.meta.setDisplayName(displayName);
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(Material material, int amount, short damage) {
            this.item = new ItemStack(material, amount, damage);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(String namespacedKey) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material);
            this.meta = item.getItemMeta();
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(String namespacedKey, short damage) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material, 1, damage);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(String namespacedKey, int amount) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material, amount);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(String namespacedKey, int amount, String displayName) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material, amount);
            this.meta = item.getItemMeta();
            this.meta.setDisplayName(displayName);
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(String namespacedKey, int amount, String displayName, short damage) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material, amount, damage);
            this.meta = item.getItemMeta();
            this.meta.setDisplayName(displayName);
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder(String namespacedKey, int amount, short damage) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            
            this.item = new ItemStack(material, amount, damage);
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder(ItemStack item) {
            this.item = item;
            this.meta = item.getItemMeta();
        }
        
        public ItemBuilder incrementAmount() throws ItemBuildingError {
            if(item.getType().getMaxStackSize() < item.getAmount() + 1) throw new ItemBuildingError("You cannot build an itemstack with more than max stack size of this type");
            item.setAmount(item.getAmount() + 1);
            return this;
        }
        
        public ItemBuilder decrementAmount() throws ItemBuildingError {
            if(0 < item.getAmount() - 1) throw new ItemBuildingError("You cannot build an itemstack with less than 1 item");
            item.setAmount(item.getAmount() - 1);
            return this;
        }
        
        public ItemBuilder addAmount(int amountToAdd) throws ItemBuildingError {
            if(item.getType().getMaxStackSize() < item.getAmount() + amountToAdd) throw new ItemBuildingError("You cannot build an itemstack with more than max stack size of this type");
            item.setAmount(item.getAmount() + amountToAdd);
            return this;
        }
        
        public ItemBuilder removeAmount(int amountToRemove) throws ItemBuildingError {
            if(0 < item.getAmount() - amountToRemove) throw new ItemBuildingError("You cannot build an itemstack with less than 1 item");
            item.setAmount(item.getAmount() - amountToRemove);
            return this;
        }
        
        public ItemBuilder setAmount(int amountToSet) {
            if(amountToSet < 0 && amountToSet > this.item.getType().getMaxStackSize()) return this;
            item.setAmount(amountToSet);
            return this;
        }
        
        public ItemBuilder setMaterial(String namespacedKey) throws ItemBuildingError {
            Material material = Material.AIR;
            for(Material type : Material.values()) {
                if(type.getKey().toString().equalsIgnoreCase(namespacedKey)) {
                    material = type;
                    break;
                }
            }
            if(material == Material.AIR) throw new ItemBuildingError("You cannot build a AIR type item, item type unrecognized");
            this.item.setType(material);
            return this;
        }
        
        public ItemBuilder setMaterial(Material material) {
            this.item.setType(material);
            return this;
        }
        
        public ItemBuilder setDisplayName(String displayName) {
            this.meta.setDisplayName(displayName);
            return this;
        }
        
        public ItemBuilder removeItemName() {
            this.meta.setDisplayName("�4 ");
            return this;
        }
        
        public ItemBuilder setLore(String...loreLines) {
            this.meta.setLore(Arrays.asList(loreLines));
            return this;
        }
        
        public ItemBuilder addLore(String...loreLines) {
            List<String> actualLore = this.meta.getLore();
            for(String line : loreLines) actualLore.add(line);
            this.meta.setLore(actualLore);
            return this;
        }
        
        public ItemBuilder clearLore() {
            this.meta.setLore(new ArrayList<String>());
            return this;
        }
        
        public ItemBuilder removeLoreLines(int...linesToRemove) {
            List<String> actualLore = this.meta.getLore();
            for(int indexesToRemove : linesToRemove) actualLore.remove(indexesToRemove);
            this.meta.setLore(actualLore);
            return this;
        }
        
        public ItemBuilder modifyLoreLine(int lineIndex, String newValue) {
            List<String> actualLore = this.meta.getLore();
            actualLore.set(lineIndex, newValue);
            this.meta.setLore(actualLore);
            return this;
        }
        
        public ItemBuilder insertLoreLine(int lineIndex, String newValue) {
            List<String> actualLore = this.meta.getLore();
            actualLore.add(lineIndex, newValue);
            this.meta.setLore(actualLore);
            return this;
        }
        
        public String[] getLoreAsArray() {
            return this.meta.getLore().toArray(new String[this.meta.getLore().size()]);
        }
        
        public List<String> getLore(){
            return this.meta.getLore();
        }
        
        public ItemBuilder addItemFlags(ItemFlag...flags) {
            this.meta.addItemFlags(flags);
            return this;
        }
        
        public ItemBuilder removeItemFlags(ItemFlag...flags) {
            this.meta.removeItemFlags(flags);
            return this;
        }
        
        public ItemBuilder clearItemFlags() {
            this.meta.removeItemFlags(this.meta.getItemFlags().toArray(new ItemFlag[this.meta.getItemFlags().size()]));
            return this;
        }
        
        public ItemBuilder addSafeEnchant(Enchantment ench, int level) {
            this.item.addEnchantment(ench, level);
            return this;
        }
        
        public ItemBuilder addNotSafeEnchant(Enchantment ench, int level) {
            this.item.addUnsafeEnchantment(ench, level);
            return this;
        }
        
        public ItemBuilder removeEnchant(Enchantment ench) {
            this.item.removeEnchantment(ench);
            return this;
        }
        
        public ItemBuilder addFakeEnchant() {
            this.item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return this;
        }
        
        public ItemBuilder addFakeEnchant(Enchantment ench, int lv) {
        	this.item.addUnsafeEnchantment(ench, lv);
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return this;
        }
        
        public ItemBuilder setEnchants(Map<Enchantment, Integer> enchants) {
            for(Enchantment enchant : this.item.getEnchantments().keySet()) {
                this.item.removeEnchantment(enchant);
            }
            for(Entry<Enchantment, Integer> enchantEntry : enchants.entrySet()) {
                this.item.addUnsafeEnchantment(enchantEntry.getKey(), enchantEntry.getValue());
            }
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, String nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setString(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, int nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setInt(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, float nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setFloat(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, double nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setDouble(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, boolean nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setBoolean(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, long nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setLong(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, int[] nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setIntArray(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, short nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setShort(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, byte[] nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.setByteArray(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder addNBT(String nbtkey, NBTBase nbtvalue) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.set(nbtkey, nbtvalue);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder removeNBT(String nbtkey) {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            totalNBTTag.remove(nbtkey);
            nmsItem.setTag(totalNBTTag);
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public boolean hasNBT(String nbtkey) {
            
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound totalNBTTag = nmsItem.getOrCreateTag();
            return totalNBTTag.hasKey(nbtkey);
            
        }
        
        public ItemBuilder setFullNBT(String fullnbt) throws ItemBuildingError {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            try {
                NBTTagCompound totalNBTTag = MojangsonParser.parse(fullnbt);
                nmsItem.setTag(totalNBTTag);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                throw new ItemBuildingError("NBT unrecognized, item cannot be edit : ");
            }
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public ItemBuilder clearNBT() {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            
            try {
                NBTTagCompound totalNBTTag = MojangsonParser.parse("{}");
                nmsItem.setTag(totalNBTTag);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            
            this.item = CraftItemStack.asBukkitCopy(nmsItem);
            
            return this;
        }
        
        public String getFullNBTasString() {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
            return nmsItem.getOrCreateTag().toString();
        }
        
        public NBTTagCompound getFullNBT() {
            return CraftItemStack.asNMSCopy(this.item).getOrCreateTag();
        }
        
        public boolean hasAlreadyAnNBT() {
            return CraftItemStack.asNMSCopy(this.item).hasTag();
        }
        
        public ItemBuilder setUnbreakable(boolean unbreakable) {
            this.meta.setUnbreakable(unbreakable);
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder incrementItemDurability() throws ItemBuildingError {
            if(this.item.getDurability() + 1 <= this.item.getType().getMaxDurability()) throw new ItemBuildingError("You cannot increment the durability further than the max durability");
            this.item.setDurability((short) (this.item.getDurability() + 1));
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder decrementItemDurability() throws ItemBuildingError {
            if(this.item.getDurability() - 1 <= 0) throw new ItemBuildingError("You cannot decrement the durability under or equal to 0");
            this.item.setDurability((short)( this.item.getDurability() - 1));
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder setDurability(short durability) throws ItemBuildingError {
            if(!(0 < durability && durability <= this.item.getType().getMaxDurability())) throw new ItemBuildingError("You cannot set a illegal durability to this item !");
            this.item.setDurability(durability);
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder addDurability(short durability) throws ItemBuildingError {
            if(this.item.getDurability() + durability <= this.item.getType().getMaxDurability()) throw new ItemBuildingError("You cannot increment the durability further than the max durability");
            this.item.setDurability((short) (this.item.getDurability() + durability));
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder removeDurability(short durability) throws ItemBuildingError {
            if(this.item.getDurability() - durability <= 0) throw new ItemBuildingError("You cannot decrement the durability under or equal to 0");
            this.item.setDurability((short)(this.item.getDurability() - durability));
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public ItemBuilder resetDurability() {
            this.item.setDurability(this.item.getType().getMaxDurability());
            return this;
        }
        
    }
    
    public static class PotionBuilder extends ItemBuilder {
 
        public PotionBuilder(int amount, String displayName) throws ItemBuildingError {
            super(Material.POTION, amount, displayName);
        }
        
        public PotionBuilder addEffect(PotionEffectType effecttype, int duration, int level, boolean showPotionParticles, boolean overwrite) {
            PotionMeta meta = (PotionMeta) this.meta;
            meta.addCustomEffect(new PotionEffect(effecttype, duration, level, showPotionParticles), overwrite);
            this.meta = meta;
            return this;
        }
        
        public PotionBuilder removeEffect(PotionEffectType effecttype) {
            PotionMeta meta = (PotionMeta) this.meta;
            meta.removeCustomEffect(effecttype);
            this.meta = meta;
            return this;
        }
        
        public PotionBuilder clearPotionEffects() {
            PotionMeta meta = (PotionMeta) this.meta;
            meta.clearCustomEffects();
            this.meta = meta;
            return this;
        }
        
        public List<PotionEffect> getPotionEffects(){
            return ((PotionMeta) meta).getCustomEffects();
        }
        
        public boolean hasCustomEffect(PotionEffectType potionType) {
            return ((PotionMeta) meta).hasCustomEffect(potionType);
        }
        
        @SuppressWarnings("deprecation")
        public PotionBuilder setPotionMainEffect(PotionEffectType type) {
            PotionMeta meta = (PotionMeta) this.meta;
            meta.setMainEffect(type);
            this.meta = meta;
            return this;
        }
        
        public PotionBuilder setSplash() {
            this.item.setType(Material.SPLASH_POTION);
            return this;
        }
        
        public PotionBuilder setPersistent() {
            this.item.setType(Material.LINGERING_POTION);
            return this;
        }
        
        public PotionBuilder changePotionColor(Color color) {
            PotionMeta meta = (PotionMeta) this.meta;
            meta.setColor(color);
            this.meta = meta;
            return this;
        }
        
        public Color getPotionColor() {
            PotionMeta meta = (PotionMeta) this.meta;
            return meta.getColor();
        }
        
    }
    
    public static class SkullBuilder extends ItemBuilder {
 
        public SkullBuilder(int amount, String displayName) {
            super(Material.PLAYER_HEAD, amount, displayName);
        }
        
        @SuppressWarnings("deprecation")
        public SkullBuilder setOwner(String ownerNickname) {
            SkullMeta meta = (SkullMeta) this.meta;
            meta.setOwner(ownerNickname);
            this.meta = meta;
            return this;
        }
        
        public SkullBuilder setOwner(OfflinePlayer owner) {
            SkullMeta meta = (SkullMeta) this.meta;
            meta.setOwningPlayer(owner);
            this.meta = meta;
            return this;
        }
        
        public SkullBuilder setBase64Texture(String textureValue) {
            
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", textureValue));
            Field profileField = null;
            try {
                profileField = this.meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(this.meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            
            return this;
        }
        
        public OfflinePlayer getOfflineOwner() {
            return ((SkullMeta) this.meta).getOwningPlayer();
        }
        
        @SuppressWarnings("deprecation")
        public String getOwnerNickname() {
            return ((SkullMeta) this.meta).getOwner();
        }
        
    }
    
    public static class BannerBuilder extends ItemBuilder {
 
        public BannerBuilder(Material material, int amount, String displayName) {
            super(material, amount, displayName);
        }
        
        public BannerBuilder addPattern(PatternType type, DyeColor dyeColor) {
            BannerMeta meta = (BannerMeta) this.meta;
            meta.addPattern(new Pattern(dyeColor, type));
            this.meta = meta;
            return this;
        }
        
        public BannerBuilder addPatterns(Map<PatternType, DyeColor> patterns) {
            BannerMeta meta = (BannerMeta) this.meta;
            for(Entry<PatternType, DyeColor> patternEntry : patterns.entrySet()) meta.addPattern(new Pattern(patternEntry.getValue(), patternEntry.getKey()));
            this.meta = meta;
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public BannerBuilder setBaseColor(DyeColor color) {
            BannerMeta meta = (BannerMeta) this.meta;
            meta.setBaseColor(color);
            this.meta = meta;
            return this;
        }
        
        public BannerBuilder removeBannerPattern(int index) {
            BannerMeta meta = (BannerMeta) this.meta;
            meta.removePattern(index);
            this.meta = meta;
            return this;
        }
        
        public Pattern getPatternAtIndex(int index) {
            return ((BannerMeta) meta).getPattern(index);
        }
        
        public int getNumberOfAppliedPatterns() {
            return ((BannerMeta) meta).numberOfPatterns();
        }
    
    }
    
    public static class BookBuilder extends ItemBuilder {
 
        public BookBuilder(int amount, String displayName) {
            super(Material.BOOK, amount, displayName);
        }
        
        public BookBuilder setAuthor(String authorNickName) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setAuthor(authorNickName);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setGeneration(Generation generation) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setGeneration(generation);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder addPages(String... pages) {
            BookMeta meta = (BookMeta) this.meta;
            meta.addPage(pages);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder clearPage(int index) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setPage(index, "");
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setPageText(int page, String text) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setPage(page, text);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setPages(Map<Integer, String> pages) {
            BookMeta meta = (BookMeta) this.meta;
            for(Entry<Integer, String> page : pages.entrySet()) meta.setPage(page.getKey(), page.getValue());
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setPages(String...pages) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setPages(pages);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setPages(List<String> pages) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setPages(pages);
            this.meta = meta;
            return this;
        }
        
        public BookBuilder setBookTitle(String title) {
            BookMeta meta = (BookMeta) this.meta;
            meta.setTitle(title);
            this.meta = meta;
            return this;
        }
        
        public String getAuthorNickname() {
            return ((BookMeta) meta).getAuthor();
        }
        
        public int getPageCount() {
            return ((BookMeta) meta).getPageCount();
        }
        
    }
    
    public static class CrossBowBuilder extends ItemBuilder {
 
        public CrossBowBuilder(Material material, int amount, String displayName) {
            super(Material.CROSSBOW, amount, displayName);
        }
        
        public CrossBowBuilder addChargedProjectile(ItemStack projectile) {
            CrossbowMeta meta = (CrossbowMeta) this.meta;
            meta.addChargedProjectile(projectile);
            this.meta = meta;
            return this;
        }
        
        public CrossBowBuilder setChargedProjectile(List<ItemStack> projectiles) {
            CrossbowMeta meta = (CrossbowMeta) this.meta;
            meta.setChargedProjectiles(projectiles);
            this.meta = meta;
            return this;
        }
        
    }
    
    public static class EnchantedBookBuilder extends ItemBuilder {
 
        public EnchantedBookBuilder(Material material, int amount, String displayName) {
            super(Material.ENCHANTED_BOOK, amount, displayName);
        }
        
        public EnchantedBookBuilder addEnchant(Enchantment ench, int level, boolean ignoreSafety) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.meta;
            meta.addStoredEnchant(ench, level, ignoreSafety);
            this.meta = meta;
            return this;
        }
        
        public EnchantedBookBuilder removeEnchant(Enchantment ench) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.meta;
            meta.removeStoredEnchant(ench);
            this.meta = meta;
            return this;
        }
        
        public boolean willCauseIncompatibilty(Enchantment ench) {
            return ((EnchantmentStorageMeta) meta).hasConflictingStoredEnchant(ench);
        }
        
        public boolean hasEnchants() {
            return ((EnchantmentStorageMeta) meta).hasEnchants();
        }
    }
    
    public static class FireWorkBuilder extends ItemBuilder {
 
        public FireWorkBuilder(Material material, int amount, String displayName) {
            super(Material.FIREWORK_ROCKET, amount, displayName);
        }
        
        public FireWorkBuilder addEffect(FireworkEffect... effects) {
            FireworkMeta meta = (FireworkMeta) this.meta;
            meta.addEffects(effects);
            this.meta = meta;
            return this;
        }
        
        public FireWorkBuilder removeEffect(int effectIndex) {
            FireworkMeta meta = (FireworkMeta) this.meta;
            meta.removeEffect(effectIndex);
            this.meta = meta;
            return this;
        }
        
        public FireWorkBuilder setFireworkPower(int power) {
            FireworkMeta meta = (FireworkMeta) this.meta;
            meta.setPower(power);
            this.meta = meta;
            return this;
        }
        
        public List<FireworkEffect> getFireworkEffects(){
            return ((FireworkMeta)meta).getEffects();
        }
        
        public int getFireworkPower() {
            return ((FireworkMeta)meta).getPower();
        }
        
    }
    
    public static class LeatherArmorBuilder extends ItemBuilder {
 
        public LeatherArmorBuilder(Material material, int amount, String displayName) throws ItemBuildingError {
            super(material, amount, displayName);
            if(!Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS).contains(material)) {
                throw new ItemBuildingError("You cannot build a leather armor without a leather-type material");
            }
        }
        
        public LeatherArmorBuilder setColor(Color color) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.meta;
            meta.setColor(color);
            this.meta = meta;
            return this;
        }
        
        public LeatherArmorBuilder setRGBColor(int red, int green, int blue) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.meta;
            meta.setColor(Color.fromRGB(red, green, blue));
            this.meta = meta;
            return this;
        }
        
        public LeatherArmorBuilder setHexColor(String hexcode) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.meta;
            java.awt.Color hexColor = java.awt.Color.decode(hexcode);
            meta.setColor(Color.fromRGB(hexColor.getRed(), hexColor.getGreen(), hexColor.getBlue()));
            this.meta = meta;
            return this;
        }
        
        public Color getColor() {
            return ((LeatherArmorMeta) meta).getColor();
        }
        
        
    }
    
    public static class ItemMapBuilder extends ItemBuilder {
 
        public ItemMapBuilder(Material material, int amount, String displayName) {
            super(material, amount, displayName);
        }
        
        public ItemMapBuilder addRenderer(MapRenderer renderer) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().addRenderer(renderer);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder removeRenderer(MapRenderer renderer) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().removeRenderer(renderer);;
            this.meta = meta;
            return this;
        }
        
        public List<MapRenderer> getRenderers(){
            return ((MapMeta) meta).getMapView().getRenderers();
        }
        
        public ItemMapBuilder setCenter(int x, int z) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().setCenterX(x);
            meta.getMapView().setCenterZ(z);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder setLocked(boolean locked) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().setLocked(locked);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder setTrackingPosition(boolean tracking) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().setTrackingPosition(tracking);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder setTrackingLimit(boolean isUnlimited) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().setUnlimitedTracking(isUnlimited);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder setWorldTrack(World worldToTrack) {
            MapMeta meta = (MapMeta) this.meta;
            meta.getMapView().setWorld(worldToTrack);
            this.meta = meta;
            return this;
        }
        
        public ItemMapBuilder setScaling(boolean scaling) {
            MapMeta meta = (MapMeta) this.meta;
            meta.setScaling(scaling);
            this.meta = meta;
            return this;
        }
        
        public Color getMapColor() {
            return ((MapMeta) this.meta).getColor();
        }
        
        public int getCenterX() {
            return ((MapMeta) this.meta).getMapView().getCenterX();
        }
        
        public int getCenterZ() {
            return ((MapMeta) this.meta).getMapView().getCenterZ();
        }
        
        @SuppressWarnings("deprecation")
        public int getId() {
            return ((MapMeta) this.meta).getMapId();
        }
        
        public World getWorld() {
            return ((MapMeta) this.meta).getMapView().getWorld();
        }
        
        public boolean isLocked() {
            return ((MapMeta) this.meta).getMapView().isLocked();
        }
        
        public boolean isVirtual() {
            return ((MapMeta) this.meta).getMapView().isVirtual();
        }
        
    }
    
    public static class SpawnEggBuilder extends ItemBuilder {
 
        public SpawnEggBuilder(Material material, int amount, String displayName) {
            super(material, amount, displayName);
        }
        
        @SuppressWarnings("deprecation")
        public SpawnEggBuilder setSpawnedType(EntityType type) {
            SpawnEggMeta meta = (SpawnEggMeta) this.meta;
            meta.setSpawnedType(type);
            this.meta = meta;
            return this;
        }
        
        @SuppressWarnings("deprecation")
        public EntityType getSpawnedType() {
            return ((SpawnEggMeta) this.meta).getSpawnedType();
        }
    }
    
    public static class SuspiciousStewBuilder extends ItemBuilder {
 
        public SuspiciousStewBuilder(Material material, int amount, String displayName) {
            super(material, amount, displayName);
        }
        
        public SuspiciousStewBuilder addEffect(PotionEffectType effecttype, int duration, int level, boolean showPotionParticles, boolean overwrite) {
            SuspiciousStewMeta meta = (SuspiciousStewMeta) this.meta;
            meta.addCustomEffect(new PotionEffect(effecttype, duration, level, showPotionParticles), overwrite);
            this.meta = meta;
            return this;
        }
        
        public SuspiciousStewBuilder removeEffect(PotionEffectType effecttype) {
            SuspiciousStewMeta meta = (SuspiciousStewMeta) this.meta;
            meta.removeCustomEffect(effecttype);
            this.meta = meta;
            return this;
        }
        
        public SuspiciousStewBuilder clearPotionEffects() {
            SuspiciousStewMeta meta = (SuspiciousStewMeta) this.meta;
            meta.clearCustomEffects();
            this.meta = meta;
            return this;
        }
        
        public List<PotionEffect> getPotionEffects(){
            return ((SuspiciousStewMeta) meta).getCustomEffects();
        }
        
        public boolean hasCustomEffect(PotionEffectType potionType) {
            return ((SuspiciousStewMeta) meta).hasCustomEffect(potionType);
        }
        
    }
    
    public static class ItemBuildingError extends Exception {
 
        static final long serialVersionUID = 8660602255405420460L;
        
        public ItemBuildingError(String errorMessage) {
            super(errorMessage);
        }
        
       
    }
}
