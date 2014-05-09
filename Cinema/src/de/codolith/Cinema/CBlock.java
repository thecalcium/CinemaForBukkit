package de.codolith.Cinema;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class CBlock implements Cloneable{

	public static final int blockVersion = 0;
	
	private Vector3Int position;
	private Material type;
	private byte data;
	
	@SuppressWarnings("deprecation")
	public CBlock(Block block, Vector3Int vector3Int) {
		position = vector3Int;
		type = block.getType();
		data = block.getData();
	}
	
	public CBlock(Vector3Int position, Material type, byte data) {
		this.position = position;
		this.type = type;
		this.data = data;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CBlock other = (CBlock) obj;
		if (data != other.data) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	public CBlock(RandomAccessFile raf) throws IOException{
		int version = raf.readInt();
		switch (version){
		case 0:
			int x,y,z;
			x = raf.readInt();
			y = raf.readInt();
			z = raf.readInt();
			position = new Vector3Int(x,y,z);
			type = Material.getMaterial(raf.readUTF());
			data = raf.readByte();
			break;
		default:
			throw new RuntimeException("unknown block version: "+version);
		}
	}

	public void save(RandomAccessFile raf) throws IOException{
		raf.writeInt(blockVersion);
		raf.writeInt(position.getX());
		raf.writeInt(position.getY());
		raf.writeInt(position.getZ());
		raf.writeUTF(type.name());
		raf.writeByte(data);
	}

	public Vector3Int getPosition() {
		return position;
	}

	public void setPosition(Vector3Int position) {
		this.position = position;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}
	
	@SuppressWarnings("deprecation")
	public void draw(Location offset){
		World world = offset.getWorld();
		Block block = world.getBlockAt(offset.getBlockX() + position.getX(), offset.getBlockY() + position.getY(), offset.getBlockZ() + position.getZ());
		block.setType(type);
		block.setData(data);
	}
	
	@Override
	public CBlock clone() {
		CBlock retval;
		try
	    {
	        retval = (CBlock)super.clone();
	    }
	    catch (CloneNotSupportedException e)
	    {
	        throw new Error();
	    }
		retval.position = position.clone();
		retval.data = data;
		retval.type = type;
		
		return retval;
	}
}
