package com.forgewareinc.Cinema;

import java.awt.image.BufferedImage;

import net.minecraft.server.Material;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Frame {

	public myBlock[] mba;
	World w;
	
	public Frame(myBlock[] mba, World w){
		this.mba = mba;
		this.w = w;
	}
	
	public Frame(BufferedImage bi){
		
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

	public void Draw(boolean setAir, Location l,UndoMaker um) {
		int xx = l.getBlockX();
		int yy = l.getBlockY();
		int zz = l.getBlockZ();
		for(int i = 0; i < mba.length; i++){
			Block b = w.getBlockAt(mba[i].x+xx, mba[i].y+yy, mba[i].z+zz);
			um.AddmyBlock(new myBlock(b));
			if(setAir || !mba[i].m.equals(Material.AIR)){
				b.setType(mba[i].m);
				b.setData(mba[i].data);
			}
		}
	}
}
