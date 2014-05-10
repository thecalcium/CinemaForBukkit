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
	@SuppressWarnings("deprecation")
	public myBlock(Block b){
		this.x = b.getX();
		this.y = b.getY();
		this.z = b.getZ();
		this.m = b.getType();
		this.data = b.getData();
	}
	
	@SuppressWarnings("deprecation")
	public String toString(){
		return "myblock: X: "+x+" Y: "+y+" Z: "+z+" Mat: " +m.getId()+" data: "+data;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((m == null) ? 0 : m.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		myBlock other = (myBlock) obj;
		if (data != other.data) {
			return false;
		}
		if (m != other.m) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		if (z != other.z) {
			return false;
		}
		return true;
	}
}
