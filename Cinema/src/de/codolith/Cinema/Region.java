package de.codolith.Cinema;

import org.bukkit.Location;

public class Region {
	private Location pos1 = null, pos2 = null;
	private int minX=0,minY=0,minZ=0,maxX=0,maxY=0,maxZ=0,blockCount=0;
	private void recalc(){
		if(pos1 != null && pos2 != null){
			minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
			minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
			minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
			maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
			maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
			maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
			blockCount = (maxX-minX+1)*(maxY-minY+1)*(maxZ-minZ+1);
		}
	}
	public Location getPos1() {
		return pos1;
	}
	public void setPos1(Location pos1) {
		this.pos1 = pos1;
		recalc();
	}
	public Location getPos2() {
		return pos2;
	}
	public void setPos2(Location pos2) {
		this.pos2 = pos2;
		recalc();
	}
	public int getMinX() {
		return minX;
	}
	public int getMinY() {
		return minY;
	}
	public int getMinZ() {
		return minZ;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public int getBlockCount() {
		return blockCount;
	}
	public String getPos1AsString()
	{
		return "["+pos1.getBlockX()+", "+pos1.getBlockY()+", "+pos1.getBlockZ()+"] in world "+pos1.getWorld().getName();
	}
	public String getPos2AsString()
	{
		return "["+pos2.getBlockX()+", "+pos2.getBlockY()+", "+pos2.getBlockZ()+"] in world "+pos2.getWorld().getName();
	}
}
