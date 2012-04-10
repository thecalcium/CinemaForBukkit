package com.forgewareinc.Cinema;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import org.bukkit.*;

public class CinemaPlayer extends TimerTask {

	boolean setAir=true;
	int frameCount;
	Frame[] fa;
	Timer t;
	public CinemaPlayer(String filePath, boolean setAir,int frameTime, Location loc, World w) throws IOException{
		this.setAir = setAir;
		RandomAccessFile raf = new RandomAccessFile(filePath,"r");
		
		frameCount = raf.readInt();//how much frames?
		fa = new Frame[frameCount];
		for(int i = 0; i < frameCount; i++){
			int blocks = raf.readInt();
			myBlock[] mba = new myBlock[blocks];
			for(int b = 0; b < blocks;b++){
				int x = raf.readInt()+loc.getBlockX();
				int y = raf.readInt();
				int z = raf.readInt();
				mba[b] = new myBlock(x,y,z,Material.getMaterial(raf.readInt()));
			}
			fa[i]= new Frame(mba,w,loc);
		}
		raf.close();
		t = new Timer();
		t.schedule(this, 0, frameTime);
	}
	
	int nowframe=0;
	
	@Override
	public void run() {
		fa[nowframe].Draw(setAir);
		nowframe++;
		if(nowframe >= frameCount){
			nowframe = 0;
		}
	}
	
	public void stop(){
		t.cancel();
		t.purge();
		t=null;
	}
	
}
