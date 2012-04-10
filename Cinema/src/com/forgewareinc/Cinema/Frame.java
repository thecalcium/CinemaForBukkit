package com.forgewareinc.Cinema;

import net.minecraft.server.Material;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Frame {

	myBlock[] mba;
	World w;
	int x,y,z;
	
	public Frame(myBlock[] mba, World w,Location from){
		this.mba = mba;
		this.w = w;
		x = from.getBlockX();
		y = from.getBlockY();
		z = from.getBlockZ();
	}
	
	public void Draw(boolean setAir){
		for(int i = 0; i < mba.length; i++){
			Block b = w.getBlockAt(mba[i].x+x, mba[i].y+y, mba[i].z+z);
			if(setAir || !mba[i].m.equals(Material.AIR)){
				b.setType(mba[i].m);
				b.setData(mba[i].data);
			}
		}
	}
}
