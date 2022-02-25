package fr.altaks.helesky.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.altaks.helesky.api.MoneyUtil;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.islandcore.IslandTier;

public class LoreUtil {
	
	public static final String loreDelimitation = "�7\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB";

	public static final String[] getModifiedLore(String...lines){
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");
		for(String line : lines) list.add(line);
		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static final String[] getInformationBookModifiedLore(Island island){
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");

		
		list.add("�cNiveau : �6" + island.getLevel());
		list.add("�cMembres : �6" + island.getMembersId().size());
		list.add("�cVisiteurs : �6" + ((island.getVisitors().size() > 0) ? island.getVisitors().size() : "�6Aucun"));

		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static final String[] getTierInformationLore(IslandTier tier){
		
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");
		
		list.add("�6Taille de l'�le : �e" + tier.getSizeAdding() + "x" + tier.getSizeAdding());
		list.add("�6Prix :�e " + MoneyUtil.formatAmount(tier.getPrice()));
		
		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static final String[] getTopIslandLore(Island island){
				
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");
		
		list.add("�6Propri�taire : �b" + Bukkit.getOfflinePlayer(island.getOwnerId()).getName());
		
		StringJoiner joiner = new StringJoiner("�e, ");
		for(UUID id : island.getMembersId()) {
			
			String pseudo = Bukkit.getOfflinePlayer(id).getName();
			joiner.add(pseudo);
			
		}
		list.add("�6Co�quipiers : �e" + joiner.toString());
		list.add("�6Niveau : �e" + island.getLevel() + " �7("+ island.getXp() + " / " + island.getNecessaryXpForNextLevel() + ") " + island.getXp()/island.getNecessaryXpForNextLevel() * 100 + "% ");
		
		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static final String[] getLevelingWither(int durability) {
		
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");
				
		durability /= 2;
		
		StringBuilder durabilityBuilder = new StringBuilder();
		durabilityBuilder.append("�a");
		for(int i = 0; i < durability; i++) durabilityBuilder.append("|");
		durabilityBuilder.append("�7");
		for(int i = 0; i < 50 - durability; i++) durabilityBuilder.append(".");
		
		list.add("�6Durabilit� : " + durabilityBuilder.toString()); // TODO : g�n�rer les duras automatis�s ||||||||||||||||||||||||||||||||||||||||||||||||||
		
		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
		
	}
	
	
	public static final String[] getBaseLevelingWitherData() {
		
		List<String> list = new ArrayList<String>();
		
		list.add(loreDelimitation);
		list.add("");
				
		list.add("§6Durabilité : §a||||||||||||||||||||||||||||||||||||||||||||||||||"); // TODO : générer les duras automatis�s ||||||||||||||||||||||||||||||||||||||||||||||||||
		
		list.add("");
		list.add(loreDelimitation);
		
		return (String[]) list.toArray(new String[list.size()]);
		
	}

}
