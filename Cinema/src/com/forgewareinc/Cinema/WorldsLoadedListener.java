package com.forgewareinc.Cinema;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WorldsLoadedListener extends Thread implements Listener {
	
	Cinema c;
	public WorldsLoadedListener(Cinema c){
		this.c = c;
	}
	
	public void run(){
		c.restorePlayers();
	}
	
	boolean first = true;
	//well just listen for the first login huh?
	//cause bukkit does not support unregistering events... i hate you ...
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
    	if(first){
    		first = false;
    		this.start();
    	}
    }
}
