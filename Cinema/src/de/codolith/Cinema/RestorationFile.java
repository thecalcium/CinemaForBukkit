package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class RestorationFile {
	
	public static final int fileVersion = 0;
	public static final String fileID = "cinemaRestore";
	
	private Cinema cinema;
	
	public RestorationFile(Cinema cinema, File file) throws IOException{
		this.cinema = cinema;
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		String id = raf.readUTF();
		if(!id.equals(fileID)){
			raf.close();
			throw new IllegalArgumentException("this is not a cinema restoration file");
		}
		int version = raf.readInt();
		switch(version){
		case 0:
			boolean oneSet = raf.readBoolean();
			if(oneSet){
				int x = raf.readInt();
				int y = raf.readInt();
				int z = raf.readInt();
				World world = Bukkit.getWorld(raf.readUTF());
				if(world != null){
					cinema.getRegion().setPos1(new Location(world,x,y,z));
				}
			}
			boolean twoSet = raf.readBoolean();
			if(twoSet){
				int x = raf.readInt();
				int y = raf.readInt();
				int z = raf.readInt();
				World world = Bukkit.getWorld(raf.readUTF());
				if(world != null){
					cinema.getRegion().setPos2(new Location(world,x,y,z));
				}
			}
			int count = raf.readInt();
			for(int i =0; i< count; i++){
				cinema.getPlayers().add(new CinemaPlayer(cinema, raf));
			}
			break;
		default:
			raf.close();
			throw new IllegalArgumentException("unknown restoration file version: "+version);
		}
	}
	
	public RestorationFile(Cinema cinema){
		this.cinema = cinema;
	}
	
	public void save(File file) throws IOException{
		file.delete();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.writeUTF(fileID);
		raf.writeInt(fileVersion);
		
		Region reg = cinema.getRegion();
		boolean oneSet = reg.getPos1() != null;
		boolean twoSet = reg.getPos2() != null;
		raf.writeBoolean(oneSet);
		if(oneSet){
			raf.writeInt(reg.getPos1().getBlockX());
			raf.writeInt(reg.getPos1().getBlockY());
			raf.writeInt(reg.getPos1().getBlockZ());
			raf.writeUTF(reg.getPos1().getWorld().getName());
		}
		raf.writeBoolean(twoSet);
		if(twoSet){
			raf.writeInt(reg.getPos2().getBlockX());
			raf.writeInt(reg.getPos2().getBlockY());
			raf.writeInt(reg.getPos2().getBlockZ());
			raf.writeUTF(reg.getPos2().getWorld().getName());
		}
		
		raf.writeInt(cinema.getPlayerCount());
		for(CinemaPlayer cp : cinema.getPlayers()){
			cp.save(raf);		
		}
		raf.close();
	}
}
