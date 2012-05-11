package com.forgewareinc.Cinema;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import org.bukkit.*;
import org.bukkit.command.CommandSender;

public class CinemaPlayer implements /*TimerTask*/Runnable {

	
	int playCount;
	//Timer t;
	Cinema c;
	public CinemaFile cf;
	
	String name;
	String filePath;
	boolean setAir=true;
	//endplaycount = PlayCount-int(played/framecount)
	boolean restoreafterstop;
	int frameTime;
	Location loc;
	public CinemaPlayer(String name, String filePath, boolean setAir,int playCount,boolean restoreafterstop,int frameTime, Location loc, CommandSender sender,Cinema c){
		this.playCount = playCount;
		this.c = c;
		
		this.name = name;
		this.filePath = filePath;
		this.setAir = setAir;
		this.restoreafterstop = restoreafterstop;
		this.frameTime = frameTime;
		this.loc = loc;
		sender.sendMessage("Start loading file...");
		new CinemaFileAsyncLoader(filePath, loc, setAir,restoreafterstop,false,sender,this).start();
	}
	
	int frameTimetoTicks(int frameTime){
		return Math.max(((frameTime+10)/20),1);
	}
	
	int taskID=0;	
	public void mystart(CommandSender sender){
		players.put(name, this);
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(c, this, 0, frameTimetoTicks(frameTime));
		//t = new Timer();
		//t.schedule(this, 0, frameTime);
		sender.sendMessage("Player loaded with " + cf.frameCount() + " Frames");
	}
	
	public void writeToCinemaFile(RandomAccessFile raf) throws IOException{
		raf.writeUTF(name);
		raf.writeUTF(filePath);
		raf.writeBoolean(setAir);
		raf.writeInt(playCount-(int)(played/cf.frameCount()));
		raf.writeBoolean(restoreafterstop);
		raf.writeInt(frameTime);
		raf.writeInt(loc.getBlockX());
		raf.writeInt(loc.getBlockY());
		raf.writeInt(loc.getBlockZ());
		raf.writeUTF(loc.getWorld().getName());
		raf.writeInt(nowframe);
		cf.writeToCinemaFile(raf);
	}
	
	public CinemaPlayer(RandomAccessFile raf,Cinema c) throws IOException{

		this.c = c;
		
		this.name = raf.readUTF();
		this.filePath = raf.readUTF();
		this.setAir = raf.readBoolean();
		this.playCount = raf.readInt();
		this.restoreafterstop = raf.readBoolean();
		this.frameTime = raf.readInt();
		int x = raf.readInt(),y = raf.readInt(),z = raf.readInt();
		this.loc = new Location(Bukkit.getWorld(raf.readUTF()), x, y, z);
		nowframe = raf.readInt();
		
		cf = new CinemaFile(filePath, loc, setAir,restoreafterstop,raf);
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(c, this, 0, frameTimetoTicks(frameTime));
		//t = new Timer();
		//t.schedule(this, 0, frameTime);
	}
	
	int nowframe=0;
	int played =0;
	
	@Override
	public void run() {
		atEnd = false;
		cf.fa[nowframe].Draw(setAir);
		nowframe++;
		if(nowframe >= cf.frameCount()){
			nowframe = 0;
		}
		atEnd = true;
		if(playCount != 0 && !stopped){
			played++;
			if((played/cf.frameCount()) >= playCount){
				this.stop();
				//c.players.remove(name);
				players.remove(name);
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
		//t.cancel();
		//t.purge();
		//t=null;
		Bukkit.getScheduler().cancelTask(taskID);
		while(!atEnd){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
			}
		}
		cf.unload();
	}

	public void removeDueToEx() {
		//c.players.remove(name);
		players.remove(name);
	}
	
	public static HashMap<String,CinemaPlayer> players = new HashMap<String,CinemaPlayer>();
}
