package com.forgewareinc.Cinema;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.bukkit.Location;
import org.bukkit.Material;

public class CinemaFile {

	public Frame[] fa;
	//public int frameCount=0;
	public int frameCount(){
		return fa.length;
	}
	
	UndoMaker um;
	boolean setAir=true;
	boolean restoreAfterUnload = true;
	String filePath;
	
	public CinemaFile(String filePath,Location loc,boolean setAir,boolean restoreAfterUnload,boolean forEditor) throws IOException{
		um = new UndoMaker();
		um.w = loc.getWorld();
		this.setAir = setAir;
		this.restoreAfterUnload = restoreAfterUnload;
		RandomAccessFile raf = new RandomAccessFile(filePath,"r");
		this.filePath = filePath;
		
		int framecount = raf.readInt();//how much frames?
		fa = new Frame[framecount];
		int xx = loc.getBlockX();
		int yy = loc.getBlockY();
		int zz = loc.getBlockZ();
		if(forEditor){xx=0;yy=0;zz=0;}
		for(int i = 0; i < frameCount(); i++){
			int blocks = raf.readInt();
			myBlock[] mba = new myBlock[blocks];
			for(int b = 0; b < blocks;b++){
				int x = raf.readInt()+xx;
				int y = raf.readInt()+yy;
				int z = raf.readInt()+zz;
				if(!forEditor){
					um.AddmyBlock(new myBlock(um.w.getBlockAt(x, y, z)));
				}
				myBlock mb = new myBlock(x,y,z,Material.getMaterial(raf.readInt()),raf.readByte());
				mba[b] = mb;
			}
			fa[i]= new Frame(mba,um.w);
		}
		raf.close();
		raf = null;
	}
	
	public void showFrame(int frameIndex){
		try{
			fa[frameIndex].Draw(setAir);
		} catch(IndexOutOfBoundsException e){}
	}
	
	
	public void showFrameforEditor(int frameIndex,Location l){
		um.Undo(setAir);
		um.Clear();
		try{
			fa[frameIndex].Draw(setAir,l,um);
		} catch(IndexOutOfBoundsException e){}
	}
	
	public void undoAllFrames(){
		um.Undo(setAir);
	}

	public void unload() {
		fa = null;
		if(restoreAfterUnload){
			um.Undo(setAir);
		}
		um.Clear();
		um=null;
	}
	
	public boolean deleteFrame(int frameIndex){
		if(frameIndex >= fa.length || frameIndex < 0){
			return false;
		}
		Frame[] temp = new Frame[fa.length-1];
		int j=0;
		for(int i = 0;i < fa.length;i++){
			if(i != frameIndex){
				temp[j]=fa[i];
				j++;
			}
		}
		fa = temp;
		return true;
	}
	
	public void save() throws IOException{
		File file = new File(filePath);
		file.delete();
		file.createNewFile();
		RandomAccessFile raf = new RandomAccessFile(filePath,"rw");
		raf.writeInt(frameCount());
		//write all blocks of this frame
		for(int f = 0; f < fa.length;f++){
			raf.writeInt(fa[f].mba.length);
			for(int b = 0;b< fa[f].mba.length;b++){
				raf.writeInt(fa[f].mba[b].x);
				raf.writeInt(fa[f].mba[b].y);
				raf.writeInt(fa[f].mba[b].z);
				raf.writeInt(fa[f].mba[b].m.getId());
				raf.writeByte(fa[f].mba[b].data);
			}
		}
		raf.close();
	}
}