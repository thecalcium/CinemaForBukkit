package com.forgewareinc.Cinema;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class UndoMaker extends Thread{

	public World w;
	public HashMap<String,myBlock> oldBlocks = null;
	
	boolean setair=true;
	public void Undo(boolean setair){
		this.setair= setair;
		this.start();
	}
	
	public UndoMaker(){}
	
	public UndoMaker(RandomAccessFile raf,World w) throws IOException{
		this.w = w;
		int count=0;
		count = raf.readInt();
		for(int i =0;i<count;i++){
			AddmyBlock(new myBlock(raf.readInt(), raf.readInt(), raf.readInt(), Material.getMaterial(raf.readInt()), raf.readByte()));
		}
	}
	
	public void AddmyBlock(myBlock mb){
		if(oldBlocks == null){
			oldBlocks = new HashMap<String,myBlock>();
		}
		String id = mb.x+";"+mb.y+";"+mb.z;
		if(!oldBlocks.containsKey(id)){
			oldBlocks.put(id, mb);
		}
	}
	
	public void run(){
		if(oldBlocks==null){return;}
		Set<String> keys = oldBlocks.keySet();
		for(String s: keys){
			myBlock b = oldBlocks.get(s);
			if(setair || !b.m.equals(Material.AIR)){
				Block bb = w.getBlockAt(b.x, b.y, b.z);
				bb.setType(b.m);
				bb.setData(b.data);
			}
		}
		Clear();
	}
	
	public void Clear(){
		oldBlocks.clear();
		oldBlocks = null;
	}
	
	public void writeToCinemaFile(RandomAccessFile raf) throws IOException{
		raf.writeInt(oldBlocks.size());
		for(String key : oldBlocks.keySet()){
			myBlock mb = oldBlocks.get(key);
			raf.writeInt(mb.x);
			raf.writeInt(mb.y);
			raf.writeInt(mb.z);
			raf.writeInt(mb.m.getId());
			raf.writeByte(mb.data);
		}
	}
}
