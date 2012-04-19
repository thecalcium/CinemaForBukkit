package com.forgewareinc.Cinema;

import java.io.IOException;
import java.util.*;

import org.bukkit.*;
import org.bukkit.command.CommandSender;

public class CinemaPlayer extends TimerTask {

	boolean setAir=true;
	int playCount;
	Timer t;
	Cinema c;
	String name;
	CinemaFile cf;
	public CinemaPlayer(String name, String filePath, boolean setAir,int playCount,boolean restoreafterstop,int frameTime, Location loc, CommandSender sender,Cinema c) throws IOException{
		this.setAir = setAir;
		this.playCount = playCount;
		this.c = c;
		this.name = name;
		
		cf = new CinemaFile(filePath, loc, setAir,restoreafterstop,false);
		t = new Timer();
		t.schedule(this, 0, frameTime);
		sender.sendMessage("Player loaded with " + cf.frameCount + " Frames");
	}
	
	int nowframe=0;
	int played =0;
	
	@Override
	public void run() {
		atEnd = false;
		cf.fa[nowframe].Draw(setAir);
		nowframe++;
		if(nowframe >= cf.frameCount){
			nowframe = 0;
		}
		atEnd = true;
		if(playCount != 0){
			played++;
			if((played/cf.frameCount) >= playCount){
				this.stop();
				c.players.remove(name);
			}
		}
	}
	
	boolean atEnd = false;
	boolean stopped = false;
	public void stop(){
		if(stopped){
			return;//so noone tries to stop this thing twice. (unlucky stop after atend=true;)
		}
		stopped = true;
		t.cancel();
		t.purge();
		t=null;
		while(!atEnd){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
			}
		}
		cf.unload();
	}
}
