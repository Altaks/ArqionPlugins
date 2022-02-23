package fr.altaks.helesky.core;

import java.util.List;

import org.bukkit.command.TabExecutor;

public interface HeleCommand extends TabExecutor {
	
	public abstract String getCommand();
	public abstract List<String> aliases();

}
