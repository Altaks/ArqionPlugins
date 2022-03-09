package fr.altaks.arqionpets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKickUndelayingEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
	
	private Player player;
	
	public PlayerKickUndelayingEvent(Player player) {
		this.player = player;
	}

	@Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
	
	public Player getPlayer() {
		return player;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
