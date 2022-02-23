package fr.nowayy.arqionbox.util;


import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentUtil {
	
	private TextComponent build;
	
	public TextComponentUtil(String text) {
		build = new TextComponent(text);
	}
	
	public void sendToPlayer(Player target) {
		target.spigot().sendMessage(build);
	}
	
	public TextComponent build() {
		return this.build;
	}
	
	public TextComponentUtil addExtra(BaseComponent component) {
		build.addExtra(component);
		return this;
	}
	
	public TextComponentUtil addExtra(String extra) {
		build.addExtra(extra);
		return this;
	}
	
	public TextComponentUtil setHover_showText(String showedText) {
		build.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new TextComponent[]{new TextComponent(showedText)}));
		return this;
	}
	
	public TextComponentUtil setHover_showEntity(String showedEntity) {
		build.setHoverEvent(new HoverEvent(Action.SHOW_ENTITY, new TextComponent[]{new TextComponent(showedEntity)}));
		return this;
	}
	
	public TextComponentUtil setHover_showItem(BaseComponent[] showeditem) {
		build.setHoverEvent(new HoverEvent(Action.SHOW_ITEM, showeditem));
		return this;
	}
	
	public TextComponentUtil setHover_showAchievement(String showedAchievement) {
		build.setHoverEvent(new HoverEvent(Action.SHOW_ACHIEVEMENT, new TextComponent[]{new TextComponent(showedAchievement)}));
		return this;
	}
	
	public TextComponentUtil setClick_openURL(String url) {
		build.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, url));
		return this;
	}
	
	public TextComponentUtil setClick_openFile(String path) {
		build.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_FILE, path));
		return this;
	}
	
	public TextComponentUtil setClick_changePage(String url) {
		build.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.CHANGE_PAGE, url));
		return this;
	}
	
	public TextComponentUtil setClick_runCommand(String command) {
		build.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command));
		return this;
	}
	
	public TextComponentUtil setClick_suggestCommand(String command) {
		build.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, command));
		return this;
	}
	
	public static BaseComponent[] getTextualDescriptionFromItem(ItemStack item) {
		return new ComponentBuilder(CraftItemStack.asNMSCopy(item).getOrCreateTag().toString()).create(); 
	}
	
}