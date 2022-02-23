package fr.altaks.helenia.bungeecommands;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Find extends Command {

	private Main main;
	
	public Find(String find, Main main) {
		super("find","helenia.find");
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (sender instanceof ProxiedPlayer && args.length > 0) {

			String targetname = args[0];
			
			ProxiedPlayer target = main.getProxy().getPlayer(targetname);
			if(target == null) {
				sender.sendMessage(Main.PREFIX + "Joueur non trouvé");
			} else {
				ProxiedPlayer proxiedsender = (ProxiedPlayer)sender;
				proxiedsender.connect(target.getServer().getInfo());
				proxiedsender.sendMessage(Main.PREFIX + "§6Vous avez été téléporté vers §e" + target.getDisplayName());
			}
		} else sender.sendMessage(Main.PREFIX + "Vous ne pouvez pas exécuter cette commande si vous n'êtes pas un joueur !");
		
	}

}
