package com.forgewareinc.Cinema;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.server.Material;

import org.bukkit.World;
import org.bukkit.block.Block;

public class UndoMaker extends Thread{

	public World w;
	public HashMap<String,myBlock> oldBlocks = new HashMap<String,myBlock>();
	
	boolean setair=true;
	public void Undo(boolean setair){
		this.setair= setair;
		this.start();
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
}
