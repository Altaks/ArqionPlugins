package fr.altaks.helenia.bungeecommands;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Modhelp extends Command {

	public Modhelp(String name) {
		super("modhelp","helenia.modhelp");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		sender.sendMessage(
				new TextComponent(
						Main.PREFIX+"\n�r"+
						"/ban <joueur> <raison> �rBannir un joueur\n�r"+
						"/unban <joueur �rD�bannir un joueur\n�r"+
						"/tempban <joueur> <dur�e> <raison> �rBannir temporairement un joueur\n�r"+
						"/find <joueur> �rSe rendre sur le serveur du joueur\n�r"+
						"/warn <joueur> <raison>�r Donner une sanction a un joueur\n�r"+
						"/unwarn <joueur> �rRetirer la derni�re sanction a un joueur\n�r"+
						"/clearwarn <joueur> �rRetirer toutes les sanctions d'un joueur\n�r"+
						"/history <joueur> �rAcc�der a la liste des sanctions d'un joueur"
				)
		);
	}

}
