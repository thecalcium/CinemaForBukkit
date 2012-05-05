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
	
	public Frame(BufferedImage bi,Alignment al){
		mba = new myBlock[bi.getWidth()*bi.getHeight()];
		int i =0;
		for(int x=0;x<bi.getWidth();x++){
			for(int y=0;y<bi.getHeight();y++){
				switch(al){
				case faceupdown:
					//y = 0
					//x=x
					//z=y
					mba[i] = Color.fromColor(x, 0, y, bi.getRGB(x, bi.getHeight()-1-y));
					break;
				case vertical0:
					//z=0
					//x=x
					//y=y
					mba[i] = Color.fromColor(x, y, 0, bi.getRGB(x, bi.getHeight()-1-y));
					break;
				case vertical90:
					//z=x
					//y=y
					//x=0
					mba[i] = Color.fromColor(0, y, x, bi.getRGB(x, bi.getHeight()-1-y));
					break;
				case vertical180:
					mba[i] = Color.fromColor(bi.getWidth()-1-x, y, 0, bi.getRGB(x, bi.getHeight()-1-y));
					break;
				case vertical270:
					mba[i] = Color.fromColor(0, y, bi.getWidth()-1-x, bi.getRGB(x, bi.getHeight()-1-y));
					break;
				}
				i++;
			}
		}
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
