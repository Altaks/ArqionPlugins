package fr.altaks.helesky.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestClass implements CommandExecutor {

	public static void main(String[] args) {
		
		System.out.println(generateProgressBar(20, 489));
		
	}
	
	public static String generateProgressBar(int progress, int bar_lenght){
	    progress = bar_lenght * progress / 100;

	    //    100      46
	    //     pg       x

	    // x = bar_lenght * progress / 100


	    StringBuilder barBuilder = new StringBuilder();
	    barBuilder.append("§a");
	    for(int i = 0; i < progress; i++) barBuilder.append("|");
	    barBuilder.append("§7");
	    for(int i = 0; i < bar_lenght - progress; i++) barBuilder.append("|");

	    return barBuilder.toString();
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return false;
	}

}
