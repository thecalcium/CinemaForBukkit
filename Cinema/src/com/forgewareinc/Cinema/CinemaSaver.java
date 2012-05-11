package com.forgewareinc.Cinema;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class CinemaSaver extends Thread {

	String[] args;
	World w;
	CommandSender sender;
	boolean delta;
	public CinemaSaver(String[] args, World w, CommandSender sender,boolean delta){
		this.args = args;
		this.w = w;
		this.sender = sender;
		this.delta = delta;
	}
	
	public void run(){
		if(delta){
			
		}else{
			
		}
	}
}
