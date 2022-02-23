package fr.altaks.helesky.core.islandcore;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.altaks.helesky.core.levelup.LevelingItems;

public class Island {
	
	public static enum IslandType {
		
		DIRT("dirt_island",0,0,0),
		WATER("water_island",0,0,0),
		FIRE("fire_island",0,0,0),
		AIR("air_island",0,0,0);
		
		private String schematicPath;
		private double x,y,z;
		
		private IslandType(String schematicPath, double x, double y, double z) {
			this.schematicPath = schematicPath;
		}
		
		public String getSchematicRelativePath() {
			return this.schematicPath;
		}
		public Location getRelativeHome(Location anchor) {
			return anchor.clone().add(x, y, z);
		}

	}
	
	private Location anchor /* center */, home, warp;
	private long level, xp;
	private int id, tier;
	private String name;
	private boolean warpEnabled = true;
	
	private UUID ownerId;
	private List<UUID> membersId, cooperatorsId = new ArrayList<UUID>();
	private List<UUID> bannedIds = new ArrayList<UUID>();
	
	public Island(Location anchor, Location home, Location warp, long level, long xp, String name, UUID ownerId, List<UUID> membersId, List<UUID> bannedIds, int id, int tier, boolean warpEnabled) {
		this.anchor = anchor;
		this.home = home;
		this.warp = warp;
		this.level = level;
		this.xp = xp;
		this.name = name;
		this.ownerId = ownerId;
		this.membersId = membersId;
		this.bannedIds = bannedIds;
		this.id = id;
		this.warpEnabled = warpEnabled;
		this.tier = tier;
	}
	
	public String toString() {
		
		/*
		 * id/name/level/xp/ownerid/[anchorX,anchorY,anchorZ,anchorYaw,anchorPitch]/[home...]/[warp...]/(members)/(banneds)/tier
		 */
		
		StringBuilder builder = new StringBuilder();
		builder.append(id+"/"+name+"/"+level+"/"+xp+"/"+ownerId.toString()+
				"/["+anchor.getBlockX()+","+anchor.getBlockY()+","+anchor.getBlockZ()+","+anchor.getYaw()+","+anchor.getPitch()+"]/"
			   + "["+home.getBlockX()+","+home.getBlockY()+","+home.getBlockZ()+","+home.getYaw()+","+home.getPitch()+"]/"
			   + "["+warp.getBlockX()+","+warp.getBlockY()+","+warp.getBlockZ()+","+warp.getYaw()+","+warp.getPitch()+"]/(");
		
		StringJoiner memberJoiner = new StringJoiner(",");
		for(UUID member : this.membersId) memberJoiner.add(member.toString());
		builder.append(memberJoiner.toString()+")/(");
		
		StringJoiner bannedJoiner = new StringJoiner(",");
		for(UUID banned : this.bannedIds) bannedJoiner.add(banned.toString());
		builder.append(bannedJoiner+")/"+tier+"/");
		builder.append(this.warpEnabled);
		return builder.toString();
	}
	
	public static Island fromString(String islandData) {
		String[] totalData = islandData.replace("\n", "").split("/");
		
		int id = Integer.parseInt(totalData[0]);
		
		String name = totalData[1];
		long level = Long.parseLong(totalData[2]), xp = Long.parseLong(totalData[3]);
		
		UUID ownerId = UUID.fromString(totalData[4]);
		
		String[] anchorData = totalData[5].replace("[", "").replace("]", "").split(",");
		
		Location anchor = new Location(Bukkit.getWorld("world"),
				Double.parseDouble(anchorData[0]),
				Double.parseDouble(anchorData[1]),
				Double.parseDouble(anchorData[2]),
				
				Float.parseFloat(anchorData[3]),
				Float.parseFloat(anchorData[4]));
		
		String[] homeData = totalData[6].replace("[", "").replace("]", "").split(",");
		
		Location home = new Location(Bukkit.getWorld("world"),
				Double.parseDouble(homeData[0]),
				Double.parseDouble(homeData[1]),
				Double.parseDouble(homeData[2]),
				
				Float.parseFloat(homeData[3]),
				Float.parseFloat(homeData[4]));
		
		String[] warpData = totalData[7].replace("[", "").replace("]", "").split(",");
		
		Location warp = new Location(Bukkit.getWorld("world"),
				Double.parseDouble(warpData[0]),
				Double.parseDouble(warpData[1]),
				Double.parseDouble(warpData[2]),
				
				Float.parseFloat(warpData[3]),
				Float.parseFloat(warpData[4]));
		
		String membersStr = totalData[8], bannedStr = totalData[9];
				
		List<UUID> membersIDs = new ArrayList<UUID>(), bannedIDs = new ArrayList<UUID>();
		
		if(membersStr != "()") {
			membersStr = membersStr.replace("(","").replace(")", "").replace("\u00A0", "");
			if(membersStr.length() > 0) {
				// il y'a un pseudo ou plusieurs
				String[] membersID = membersStr.split(",");
				for(String memberid : membersID) membersIDs.add(UUID.fromString(memberid));
			}
		}
		
		if(bannedStr != "()") {
			bannedStr = bannedStr.replace("(","").replace(")", "").replace("\u00A0", "");
			if(bannedStr.length() > 0) {
				// il y'a un pseudo ou plusieurs
				String[] bannedsID = bannedStr.split(",");
				for(String bannedid : bannedsID) bannedIDs.add(UUID.fromString(bannedid));
			}
		}

		int tier = Integer.parseUnsignedInt(totalData[10]);
			
		boolean warpEnabled = totalData[11].equalsIgnoreCase("true");
		return new Island(anchor, home, warp, level, xp, name, ownerId, membersIDs, bannedIDs, id, tier, warpEnabled);
	}

	public Location getAnchor() {
		return anchor;
	}

	public Location getHome() {
		return home;
	}

	public Location getWarp() {
		return warp;
	}

	public long getLevel() {
		return level;
	}

	public long getXp() {
		return xp;
	}

	public String getName() {
		return name;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public List<UUID> getMembersId() {
		return membersId;
	}

	public List<UUID> getCooperatorsId() {
		return cooperatorsId;
	}

	public List<UUID> getBannedIds() {
		return bannedIds;
	}
	
	public List<Player> getVisitors(){
		List<Player> players = new ArrayList<Player>();
		this.anchor.getWorld().getNearbyEntities(this.anchor, 1500, 255, 1500).forEach(entity -> {
			if(entity instanceof Player) {
				if(!entity.getUniqueId().equals(ownerId) && !membersId.contains(entity.getUniqueId()) && !cooperatorsId.contains(entity.getUniqueId())) {
					 players.add((Player)entity);
				}
			}
		});
		return players;
	}

	public int getId() {
		return id;
	}

	public int getTier() {
		return tier;
	}

	public void setAnchor(Location anchor) {
		this.anchor = anchor;
	}

	public void setHome(Location home) {
		this.home = home;
	}

	public void setWarp(Location warp) {
		this.warp = warp;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public void setXp(long xp) {
		this.xp = xp;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public void setMembersId(List<UUID> membersId) {
		this.membersId = membersId;
	}

	public void setCooperatorsId(List<UUID> cooperatorsId) {
		this.cooperatorsId = cooperatorsId;
	}

	public void setBannedIds(List<UUID> bannedIds) {
		this.bannedIds = bannedIds;
	}

	public boolean isWarpEnabled() {
		return warpEnabled;
	}

	public void setWarpEnabled(boolean warpEnabled) {
		this.warpEnabled = warpEnabled;
	}
	
	public long getStrictXpOfIsland() {
		return (long) (75 * Math.pow(1.2, getLevel()) + getXp());
	}
	
	public long getMissingXpForNextLevelOfIsland() {
		return (long) (getNecessaryXpForNextLevel() - this.getXp());
	}
	
	public long getNecessaryXpForNextLevel() {
		return (long) (75 * Math.pow(1.2, getLevel()+1));
	}
	
	public void addXp(long xp) {
		if(xp > getMissingXpForNextLevelOfIsland()) {
			// changement de niveau
			// Donc soustraire l'xp utilisée a l'xp a ajouter puis ajouter. // récursion si nécessaire
			while(xp > getMissingXpForNextLevelOfIsland()) {
				xp -= getMissingXpForNextLevelOfIsland();
				this.setLevel(this.getLevel() + 1);
			}
			this.setXp(xp);
		} else {
			// ajout simple à l'xp de l'île
			this.setXp(this.getXp() + xp);
		}
		
	}
	
	public boolean itemWillMakeLevelUp(LevelingItems item, int amount) {
		return item.getXp() * amount > getMissingXpForNextLevelOfIsland();
	}
}
