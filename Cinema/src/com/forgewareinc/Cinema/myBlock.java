package com.forgewareinc.Cinema;

import org.bukkit.Material;

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
}
