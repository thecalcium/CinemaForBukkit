package com.forgewareinc.Cinema;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WorldsLoadedListener implements Listener {
	
	Cinema c;
	public WorldsLoadedListener(Cinema c){
		this.c = c;
	}
	//well just listen for the first login huh?
	//cause bukkit does not support unregistering events... i hate you ...
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
    	c.restorePlayers();
    }
}
