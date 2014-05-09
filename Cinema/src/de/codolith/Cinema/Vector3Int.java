package de.codolith.Cinema;

public class Vector3Int implements Cloneable {
	private int x,y,z;
	
	public Vector3Int(){
		x=0;y=0;z=0;
	}
	
	public Vector3Int(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void Substract(Vector3Int other){
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}
	
	public void Add(Vector3Int other){
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Vector3Int other = (Vector3Int) obj;
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
	
	@Override
	public Vector3Int clone(){
		Vector3Int retval;
		try
	    {
	        retval = (Vector3Int)super.clone();
	    }
	    catch (CloneNotSupportedException e)
	    {
	        throw new Error();
	    }
		retval.x = x;
		retval.y = y;
		retval.z = z;
		
		return retval;
	}
}
