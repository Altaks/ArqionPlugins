package fr.altaks.arqionpets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKickDelayingEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
	
	private Player player;
	private long secondsToDelay;
	
	public PlayerKickDelayingEvent(Player player, long secondsToDelay) {
		this.player = player;
		this.secondsToDelay = secondsToDelay;
	}

	@Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
	
	public Player getPlayer() {
		return player;
	}

	public long getSecondsToDelay() {
		return secondsToDelay;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
