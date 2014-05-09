package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class RestorationFile {
	
	public static final int fileVersion = 0;
	public static final String fileID = "cinemaRestore";
	
	private List<CinemaPlayer> players;
	
	public RestorationFile(Cinema cinema, File file) throws IOException{
		players = new LinkedList<CinemaPlayer>();
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		String id = raf.readUTF();
		if(!id.equals(fileID)){
			raf.close();
			throw new IllegalArgumentException("this is not a cinema restoration file");
		}
		int version = raf.readInt();
		switch(version){
		case 0:
			int count = raf.readInt();
			for(int i =0; i< count; i++){
				players.add(new CinemaPlayer(cinema, raf));
			}
			break;
		default:
			raf.close();
			throw new IllegalArgumentException("unknown restoration file version: "+version);
		}
	}
	
	public RestorationFile(List<CinemaPlayer> players){
		this.players = players;
	}
	
	public void save(File file) throws IOException{
		file.delete();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.writeUTF(fileID);
		raf.writeInt(fileVersion);
		raf.writeInt(players.size());
		for(CinemaPlayer cp : players){
			cp.save(raf);		
		}
		raf.close();
	}
	
	public void addTo(List<CinemaPlayer> players){
		for(CinemaPlayer cp : this.players){
			players.add(cp);
		}
	}
}
