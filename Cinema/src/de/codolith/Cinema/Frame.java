package de.codolith.Cinema;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Frame {

	public static final int frameVersion = 0;
	
	private HashMap<Vector3Int,CBlock> blocks  = new HashMap<Vector3Int,CBlock>();
	
	public Frame(BufferedImage bi, Alignment alignment) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		Vector3Int position;
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				switch(alignment){
				case faceup:
					//y=0
					//x=x
					//z=y
					position = new Vector3Int(width-1-x, 0, y);
					break;
				case facedown:
					position = new Vector3Int(x, 0, y);
					break;
				case vertical0:
					//z=0
					//x=x
					//y=y
					position = new Vector3Int(x, y, 0);
					break;
				case vertical90:
					//z=x
					//y=y
					//x=0
					position = new Vector3Int(0, y, x);
					break;
				case vertical180:
					position = new Vector3Int(-width+1+x, y, 0);
					break;
				case vertical270:
					position = new Vector3Int(0, y, -width+1+x);
					break;
				default:
					position = new Vector3Int();
				}
				addBlock(Color.fromColor(position, bi.getRGB(x, height-1-y)));
			}
		}
	}
	
	public Frame(Region region){
		this(region,null);
	}
	
	//delta if previousFrame not null
	public Frame(Region region, Frame previousFrame) {
		int nowx,nowy,nowz;
		Location pos1 = region.getPos1();
		World world = pos1.getWorld();
		boolean delta = previousFrame != null;
		for(int xx = region.getMinX();xx<=region.getMaxX();xx++){
			for(int yy =region.getMinY();yy<=region.getMaxY();yy++){
				for(int zz = region.getMinZ();zz<=region.getMaxZ();zz++){
					nowx = xx-pos1.getBlockX();
					nowy = yy-pos1.getBlockY();
					nowz = zz-pos1.getBlockZ();
					Block b = world.getBlockAt(xx, yy, zz);
					CBlock cb = new CBlock(b, new Vector3Int(nowx,nowy,nowz));
					if(delta){
						if(!cb.equals(previousFrame.getBlock(cb.getPosition()))){
							//add
							addBlock(cb);
						}
					}else{
						//add
						addBlock(cb);
					}
				}
			}
		}
	}

	public Frame() {}
	
	public Frame(RandomAccessFile raf) throws IOException{
		int version = raf.readInt();
		switch(version){
		case 0:
			int count = raf.readInt();
			for(int i =0; i< count;i++){
				CBlock cb = new CBlock(raf);
				addBlock(cb);
			}
			break;
		default:
			throw new RuntimeException("unknown frame version: "+version);
		}
	}
	
	public void save(RandomAccessFile raf) throws IOException{
		raf.writeInt(frameVersion);
		raf.writeInt(blocks.size());
		for(CBlock cb : blocks.values()){
			cb.save(raf);
		}
	}
	
	public void addBlock(CBlock block){
		blocks.put(block.getPosition(), block);
	}
	
	public void removeBlock(CBlock block){
		blocks.remove(block.getPosition());
	}
	
	public CBlock getBlock(Vector3Int position){
		return blocks.get(position);
	}

	public int getBlockCount() {
		return blocks.size();
	}
	
	public void draw(Location offset){
		for(CBlock cb : blocks.values()){
			cb.draw(offset);
		}
	}

	/**
	 * Puts the new frame over this and overwrites already existent blocks, thus destructive
	 * example: 0 is empty (no block in frame, not even air) and x and y are different blocks
	 * x0x     0x0     xxx
	 * 000  +  x0x  =  x0x  
	 * x0x     0x0     xxx
	 * xxx     0y0     xyx
	 * x0x  +  0y0  =  xyx
	 * xxx     0y0     xyx
	 * @param frame
	 */
	public void destructiveLayOver(Frame frame){
		for(CBlock block : frame.blocks.values()){
			CBlock clone = block.clone();
			blocks.put(clone.getPosition(), clone);
		}
	}
}
