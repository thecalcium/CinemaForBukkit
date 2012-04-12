package com.forgewareinc.Cinema;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class myBlock {

	public Material m;
	public byte data;
	public int x,y,z;
	public myBlock(int x, int y, int z, Material m, byte data){
		this.x = x;
		this.y = y;
		this.z = z;
		this.m = m;
		this.data = data;
	}
	public myBlock(Block b){
		this.x = b.getX();
		this.y = b.getY();
		this.z = b.getZ();
		this.m = b.getType();
		this.data = b.getData();
	}
}
