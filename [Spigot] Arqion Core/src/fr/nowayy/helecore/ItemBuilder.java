package fr.nowayy.helecore;
 
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
 

 
/**
 * @author Altaks
 */
public class ItemBuilder {
    
    /**
     * Default ItemStack and default ItemMeta
     */
    private ItemStack item = new ItemStack(Material.AIR);
    private ItemMeta itemMeta = item.getItemMeta();
    
    /**
     * Constructor to get an ItemBuilder from an other
     * @param builder -> Other builder to copy values from
     */
    public ItemBuilder(ItemBuilder builder) {
        this.item = builder.item;
        this.itemMeta = builder.itemMeta;
    }
    
    /**
     * Constructor to get an ItemBuilder from an ItemStack
     * @param item -> ItemStack to copy values from
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.itemMeta = item.getItemMeta();
    }
    
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/Type of the item
     */
    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        itemMeta = item.getItemMeta();
    }
    
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/Type of the item
     * @param amount -> Amount of items in the stack
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }
 
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/Type of the item
     * @param amount -> Amount of items in the stack
     * @param itemName -> Display name of the stack
     */
    public ItemBuilder(Material material, int amount, String itemName) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
    }
    
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/Type of the item
     * @param amount -> Amount of items in the stack
     * @param itemName -> Display name of the stack
     * @param damage -> Damages done to the ItemStack
     */
    @SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, String itemName, short damage) {
        item = new ItemStack(material, amount, damage);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
    }
    
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/Type of the item
     * @param amount -> Amount of items in the stack
     * @param itemName -> Display name of the stack
     * @param damage -> Damages done to the ItemStack
     * @param data -> bytes of data of the item
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder(Material material, int amount, String itemName, short damage, byte data) {
        item = new ItemStack(material, amount, damage, data);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
    }
    
    /**
     * Constructor to make a ItemBuilder
     * @param material -> Material/type of the item
     * @param amount -> amount of items in the stack
     * @param data -> bytes of data of the item
     */
    @SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, byte data) {
    	item = new ItemStack(material, amount, data);
    }
    
    /**
     * Method to get the real ItemStack from the builder
     * @return the real ItemStack
     */
    public ItemStack build() {
        ItemStack item = this.item;
        item.setItemMeta(this.itemMeta);
        return item;
    }
    
    /**
     * Method to set the display name of the item
     * @param name -> new name to apply to the stack
     * @return ItemBuilder
     */
    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }
    
    /**
     * Method to set the amount of items in the itemstack (amount have to be between 1 and 64)
     * @param amount -> new amount to apply
     * @return ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        if(amount > 64 || amount < 1) throw new IllegalArgumentException("You have to use an amount between 1 and 64 !");
        item.setAmount(amount);
        return this;
    }
    
    /**
     * Method to reduce the actual amount of items in the stack
     * @param howMany -> number of items to remove from the stack
     * @return ItemBuilder
     */
    public ItemBuilder reduceAmount(int howMany) {
        int newAmount = this.item.getAmount() - howMany;
        if(newAmount > 64) newAmount = 64;
        if(newAmount < 1) newAmount = 1;
        this.item.setAmount(newAmount);
        return this;
    }
    
    /**
     * Method to reduce the actual amount of items in the stack
     * @param howMany -> number of items to add from the stack
     * @return ItemBuilder
     */
    public ItemBuilder increaseAmount(int howMany) {
        int newAmount = this.item.getAmount() + howMany;
        if(newAmount > 64) newAmount = 64;
        if(newAmount < 1) newAmount = 1;
        this.item.setAmount(newAmount);
        return this;
    }
    
    /**
     * Method used to set the Lore of the Item
     * @param lines -> lines to set
     * @return ItemBuilder
     */
    public ItemBuilder setLore(String...lines) {
        itemMeta.setLore(Arrays.asList(lines));
        return this;
    }
    
    /**
     * Method used to set the Lore of the Item
     * @param lines -> lines to set
     * @param needSplit -> indicates if the line need to be split in multiple lines by "\n" delimiter
     * @return ItemBuilder
     */
    public ItemBuilder setLore(String line, boolean needSplit) {
        itemMeta.setLore((needSplit) ? Arrays.asList(line.split("\n")) : Arrays.asList(line));
        return this;
    }
    
    /**
    * Method used to add ItemFlags to hide things in Item description (in inventory view)
     * @param flags -> flags to add
     * @return ItemBuilder
     */
    public ItemBuilder addItemFlags(ItemFlag...flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }
    
    /**
     * Method used to add an enchant in a safe way, it checks if it's natural before applying it
     * @param enchantement -> enchant to add
     * @param level -> level of the enchant
     * @return ItemBuilder
     */
    public ItemBuilder addSafeEnchant(Enchantment enchantement, int level) {
        this.item.addEnchantment(enchantement, level);
        return this;
    }
    
    /**
     * Method used to add enchants in a safe way, it checks if it's natural before applying them 
     * @param enchantement -> (enchants <=> levels) to add
     * @return ItemBuilder
     */
    public ItemBuilder addSafeEnchant(Map<Enchantment, Integer> enchants) {
        item.addEnchantments(enchants);
        return this;
    }
    
    /**
     * Method used to add an enchant in a unsafe way, it doesn't check anything
     * @param enchantement -> enchant to add
     * @param level -> level of the enchant
     * @return ItemBuilder
     */
    public ItemBuilder addUnsafeEnchant(Enchantment enchantment, int level) {
    	item.addUnsafeEnchantment(enchantment, level);
        return this;
    }
    
    /**
     * Method used to add enchants in a unsafe way, it doesn't checks anything
     * @param enchantement -> (enchants <=> levels) to add
     * @return ItemBuilder
     */
    public ItemBuilder addUnsafeEnchant(Map<Enchantment, Integer> enchants) {
        item.addUnsafeEnchantments(enchants);
        return this;
    }
    
    /**
     * Method used to set the color of leather suits
     * @param color -> color to apply
     * @return ItemBuilder
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        LeatherArmorMeta colorMeta = (LeatherArmorMeta) this.itemMeta;
        colorMeta.setColor(color);
        this.itemMeta = colorMeta;
        return this;
    }
    
    /**
     * Method used to set a skin on the skull, ONLY WORKS IF THE SPECIFIED PLAYER HAS ALREADY JOINED THE SERVER
     * @param playerName -> Name of the player
     * @return ItemBuilder
     */
    @SuppressWarnings("deprecation")
	public ItemBuilder setSkullSkin(String playerName) {
        SkullMeta skullmeta = (SkullMeta) this.itemMeta;
        skullmeta.setOwner(playerName);
        this.itemMeta = skullmeta;
        return this;
    }
    
    /**
     * Method used to set a skin on the skull, skin stocked in a base64 string
     * @param base64texture -> texture of the skin in base64 value
     * @return ItemBuilder 
     */
    public ItemBuilder setCustomSkullTexture(String base64texture) {
        SkullMeta meta = (SkullMeta) this.itemMeta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64texture));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        this.itemMeta = meta;
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