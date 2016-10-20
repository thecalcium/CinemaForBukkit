package com.forgewareinc.Cinema;

import java.util.LinkedList;

public class myBlockBuffer {

	LinkedList<myBlock> blocks = new LinkedList<myBlock>();
	public void add(myBlock mb){
		myBlock already = null;
		for(myBlock mbi : blocks){
			if(mbi.x == mb.x && mbi.y == mb.y && mbi.z == mb.z){
				already = mbi; break;
			}
		}
		if(already != null) {
			blocks.remove(already);
		}
		blocks.add(mb);
	}
	
	public myBlock[] toArray(){
		myBlock[] mba = new myBlock[blocks.size()];
		blocks.toArray(mba);
		return mba;
	}
}
