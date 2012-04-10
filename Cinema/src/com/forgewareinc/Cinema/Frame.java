package com.forgewareinc.Cinema;

import net.minecraft.server.Material;

import org.bukkit.Location;
import org.bukkit.World;

public class Frame {

	myBlock[] mba;
	World w;
	Location from;
	
	public Frame(myBlock[] mba, World w,Location from){
		this.mba = mba;
		this.w = w;
	}
	
	public void Draw(boolean setAir){
		for(int i = 0; i < mba.length; i++){
			if(setAir){
				w.getBlockAt(mba[i].x+from.getBlockX(), mba[i].y+from.getBlockY(), mba[i].z+from.getBlockZ()).setType(mba[i].m);
			}else{
				if(mba[i].m.equals(Material.AIR)){
					w.getBlockAt(mba[i].x+from.getBlockX(), mba[i].y+from.getBlockY(), mba[i].z+from.getBlockZ()).setType(mba[i].m);
				}
			}
		}
	}
}
