package com.forgewareinc.Cinema;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WorldsLoadedListener implements Listener {
	
	Cinema c;
	public WorldsLoadedListener(Cinema c){
		this.c = c;
	}
	
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
    	c.restorePlayers();
    }
}
