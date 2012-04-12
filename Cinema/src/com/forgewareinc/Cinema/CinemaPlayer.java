package com.forgewareinc.Cinema;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import org.bukkit.*;
import org.bukkit.command.CommandSender;

public class CinemaPlayer extends TimerTask {

	boolean setAir=true,restoreafterstop=true;
	int frameCount,playCount;
	Frame[] fa;
	Timer t;
	Cinema c;
	String name;
	UndoMaker um;
	public CinemaPlayer(String name, String filePath, boolean setAir,int playCount,boolean restoreafterstop,int frameTime, Location loc, CommandSender sender,Cinema c) throws IOException{
		this.setAir = setAir;
		this.restoreafterstop = restoreafterstop;
		this.playCount = playCount;
		this.c = c;
		this.name = name;
		RandomAccessFile raf = new RandomAccessFile(filePath,"r");
		um = new UndoMaker();
		um.w = loc.getWorld();
		
		frameCount = raf.readInt();//how much frames?
		fa = new Frame[frameCount];
		int xx = loc.getBlockX();
		int yy = loc.getBlockY();
		int zz = loc.getBlockZ();
		for(int i = 0; i < frameCount; i++){
			int blocks = raf.readInt();
			myBlock[] mba = new myBlock[blocks];
			for(int b = 0; b < blocks;b++){
				int x = raf.readInt()+xx;
				int y = raf.readInt()+yy;
				int z = raf.readInt()+zz;
				um.AddmyBlock(new myBlock(um.w.getBlockAt(x, y, z)));
				myBlock mb = new myBlock(x,y,z,Material.getMaterial(raf.readInt()),raf.readByte());
				mba[b] = mb;
			}
			fa[i]= new Frame(mba,um.w);
		}
		raf.close();
		raf = null;
		t = new Timer();
		t.schedule(this, 0, frameTime);
		sender.sendMessage("player loaded with " + frameCount + " Frames");
	}
	
	int nowframe=0;
	int played =0;
	
	@Override
	public void run() {
		atend = false;
		fa[nowframe].Draw(setAir);
		nowframe++;
		if(nowframe >= frameCount){
			nowframe = 0;
		}
		atend = true;
		if(playCount != 0){
			played++;
			if((played/fa.length) >= playCount){
				this.stop();
				c.players.remove(name);
			}
		}
	}
	
	boolean atend = false;
	boolean stopped = false;
	public void stop(){
		if(stopped){
			return;//so noone tries to stop this thing twice. (unlucky stop after atend=true;)
		}
		stopped = true;
		t.cancel();
		t.purge();
		t=null;
		while(!atend){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
			}
		}
		fa=null;
		if(restoreafterstop){
			um.Undo(setAir);
		}
	}
	
}
