package com.forgewareinc.Cinema;

import net.minecraft.server.Material;

import org.bukkit.World;
import org.bukkit.block.Block;

public class Frame {

	public myBlock[] mba;
	World w;
	
	public Frame(myBlock[] mba, World w){
		this.mba = mba;
		this.w = w;
	}
	
	public void Draw(boolean setAir){
		for(int i = 0; i < mba.length; i++){
			Block b = w.getBlockAt(mba[i].x, mba[i].y, mba[i].z);
			if(setAir || !mba[i].m.equals(Material.AIR)){
				b.setType(mba[i].m);
				b.setData(mba[i].data);
			}
		}
	}
}
