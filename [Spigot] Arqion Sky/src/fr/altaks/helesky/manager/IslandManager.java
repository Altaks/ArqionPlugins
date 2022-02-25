package fr.altaks.helesky.manager;

import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.islandcore.Island.IslandType;

public class IslandManager {
	
	private int lastX, lastZ, lastId = 0;
	private Main main;
	
	public IslandManager(Main main, int lastX, int lastZ, int lastId) {
		
		this.lastX = lastX;
		this.lastZ = lastZ;
		this.lastId = lastId;
		
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	public void generateIsland(IslandType type, UUID owner) {
		
		if(getLastX() + 1_500 > (10 * 1_500)) {
			this.lastZ += 1_500;
			this.lastX = 0;
		} else if(getLastZ() + 1_500 > (10 * 1_500)) {
			Main.LOGGER.warning(Main.ERROR_PREFIX + "La map comprend trop d'îles, veuillez prévenir le staff !");
			Bukkit.getPlayer(owner).sendMessage(Main.ERROR_PREFIX + "La map comprend trop d'îles, veuillez prévenir le staff !");
		} else {
			this.lastX += 1_500;
		}
		
		File schematic = new File(main.getSchematicsDirectory() + File.separator + type.getSchematicRelativePath() + ".schem");
		
		Location location = new Location(Bukkit.getWorld("world"), getLastX(), 100, getLastZ());
		
		// creation de l'ile
		ClipboardFormat format = ClipboardFormats.findByFile(schematic);
		try {
			
			ClipboardReader reader = format.getReader(new FileInputStream(schematic));
			Clipboard clipboard = (Clipboard) reader.read();
			
			try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), 100_000)) {
			    Operation operation = new ClipboardHolder((com.sk89q.worldedit.extent.clipboard.Clipboard) clipboard)
			            .createPaste(editSession)
			            .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
			            .ignoreAirBlocks(true)
			            .build();
			    Operations.complete(operation);
			    editSession.flushSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OfflinePlayer player = Bukkit.getOfflinePlayer(owner);

		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {

			this.lastId += 1;
			Island island = new Island(location, type.getRelativeHome(location.clone()), type.getRelativeHome(location.clone()), 0, 0, "�le de " + player.getName(), owner, new ArrayList<UUID>(), new ArrayList<UUID>(), this.lastId,1, true);
			
			this.main.getIslandsFromId().put(island.getId(), island);
			this.main.getIslandIDFromUUID().put(player.getUniqueId(), island.getId());
			
		});
		
		player.getPlayer().teleport(location.add(0, 1, 0));
		
	}
	
	public void deleteIsland(Island island) {
		
		Location pos1 = island.getAnchor().clone();
		pos1.subtract(400, 0, 400);
		pos1.setY(0);
		// Chunk chunk1 = pos1.getChunk();
		
		Location pos2 = island.getAnchor().clone();
		pos2.setY(256);
		pos2.add(400, 0, 400);
		// Chunk chunk2 = pos2.getChunk();
		

		island.getAnchor().getWorld().getNearbyEntities(island.getAnchor(), 400, 250, 400).forEach(entity -> {
			if(!(entity instanceof Player)) {
				entity.remove();
			} else {
				// ((Player)entity).teleport(Main.spawn);
				((Player)entity).sendMessage(Main.PREFIX + "§cVotre île a été détruite par son propriétaire !");
			}
		});
	
		// TODO : A smoother : �a fait lag le serv
		for (int x = Math.min(pos1.getBlockX(), pos2.getBlockX()); x <= Math.max(pos1.getBlockX(), pos2.getBlockX()); x++) {
			for (int y = Math.min(pos1.getBlockY(), pos2.getBlockY()); y <= Math.max(pos1.getBlockY(), pos2.getBlockY()); y++) {
				for (int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); z <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); z++) {
					Block block = pos1.getWorld().getBlockAt(x, y, z);
					if(block.getType() != Material.AIR) {
						block.setType(Material.AIR, false);
					}
				}
			}
		}
//		long begin = System.currentTimeMillis();
//		List<Entity> aroundPlayers = pos1.getWorld().getNearbyEntities(pos1, 400, 256, 4000).stream().filter(e -> e instanceof Player).collect(Collectors.toList());
//		
//		// x z
//		World world = pos1.getWorld();
//		for(int x = Math.min(chunk1.getX(), chunk2.getX()); x <= Math.max(chunk1.getX(), chunk2.getX()); x++) {
//			for(int z = Math.min(chunk1.getZ(), chunk2.getZ()); z <= Math.max(chunk1.getZ(), chunk2.getZ()); z++) {
//				if(world.isChunkGenerated(x, z)) {
//					Chunk temp = world.getChunkAt(x, z);
//					if(!temp.isLoaded()) temp.load();
//					for(int dx = 0; dx < 16; dx++) for(int dz = 0; dz < 16; dz++) for(int dy = 0; dy < 256; dy++) {
//						temp.getBlock(dx, dy, dz).setType(Material.AIR);
//					}
//					for(Entity entity : aroundPlayers) ((CraftPlayer)(Player)entity).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(((CraftChunk)temp).getHandle(), 20));
//				}
//			}
//		}
//		long end = System.currentTimeMillis();
//		System.out.println((end - begin) / 1000);
		
		File islandFile = new File(main.getIslandDirectory() + File.separator + island.getId() + ".yml");
		if(islandFile.exists()) {
			islandFile.delete();
			Main.LOGGER.info(Main.PREFIX + "L'île #" + island.getId() + " a été détruite par son propriétaire");
		}
		
		int id = main.getIslandIDFromUUID().get(island.getOwnerId());
		
		for(Entry<UUID, Integer> entry : main.getIslandIDFromUUID().entrySet()) {
			if(entry.getValue().equals(id)) {
				main.getUuidlinkYml().set(entry.getKey().toString(), null);
			}
		}
		
		for(UUID ids : island.getMembersId()) {
			main.getIslandIDFromUUID().remove(ids);
		}
		
		main.getIslandIDFromUUID().remove(island.getOwnerId());
		main.getIslandsFromId().remove(id);
		
		// TODO : TP de tous les joueurs au spawn
		
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastZ() {
		return lastZ;
	}

	public int getLastId() {
		return lastId;
	}

}
