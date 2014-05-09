package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CinemaPlayer extends BukkitRunnable{
	
	public static final int msPerTick = 50;
	public static final int playerVersion = 0;
	
	private Cinema cinema;
	private String id;
	private CinemaFile cinemaFile;
	private int frameDuration;
	private int playCount, remainingPlayCount;
	private Location offset;
	private boolean reversed = false, running = true;
	
	private int msCounter = 0;
	private int currentFrame = 0;
	
	public Cinema getCinema()
	{
		return cinema;
	}

	public String getId() {
		return id;
	}
	
	public CinemaFile getFile(){
		return cinemaFile;
	}
	
	public int getFrameDuration(){
		return frameDuration;
	}
	
	public int getPlayCount(){
		return playCount;
	}
	
	public CinemaPlayer(Cinema cinema, String id, CinemaFile cinemaFile, int frameDuration, int playCount) {
		this(cinema,id,cinemaFile,frameDuration,playCount,cinema.getRegion().getPos1());		
	}
	
	private void shedule(){
		this.runTaskTimer(Cinema.currentInstance, 0, 0);
		msCounter = frameDuration;
	}

	public CinemaPlayer(Cinema cinema, String id, CinemaFile cinemaFile, int frameDuration, int playCount, Location offset) {
		this.cinema = cinema;
		this.id = id;
		this.cinemaFile = cinemaFile;
		this.frameDuration = frameDuration;
		this.playCount = playCount;
		this.offset = offset;
		shedule();
	}
	
	public CinemaPlayer(Cinema cinema, RandomAccessFile restorationFile) throws IOException, IllegalStateException{
		this.cinema = cinema;
		int version = restorationFile.readInt();
		switch(version){
		case 0:
			id = restorationFile.readUTF();
			cinemaFile = new CinemaFile(new File(cinema.getExtDataFolder(),restorationFile.readUTF()));
			frameDuration = restorationFile.readInt();
			playCount = restorationFile.readInt();
			remainingPlayCount = restorationFile.readInt();
			int x = restorationFile.readInt();
			int y = restorationFile.readInt();
			int z = restorationFile.readInt();
			String worldName = restorationFile.readUTF();
			World world = Bukkit.getWorld(worldName);
			if(world == null){
				throw new IllegalStateException("there is no such world as \""+worldName+"\"");
			}
			offset = new Location(world,x,y,z);
			break;
		default:
			throw new RuntimeException("unknown player version: "+version);
		}
		shedule();
	}

	public void save(RandomAccessFile restorationFile) throws IOException{
		restorationFile.writeInt(playerVersion);
		restorationFile.writeUTF(id);
		restorationFile.writeUTF(cinemaFile.getName());
		restorationFile.writeInt(frameDuration);
		restorationFile.writeInt(playCount);
		restorationFile.writeInt(remainingPlayCount);
		restorationFile.writeInt(offset.getBlockX());
		restorationFile.writeInt(offset.getBlockY());
		restorationFile.writeInt(offset.getBlockZ());
		restorationFile.writeUTF(offset.getWorld().getName());
	}

	public void play(){
		running = true;
	}
	
	public void stop() {
		running = false;
		this.cancel();
	}
	
	public void pause(){
		running = false;
	}
	
	public void reverse(){
		reversed = !reversed;
	}
	
	private void alterCurrentFrame(int value){
		currentFrame += value;
		while(currentFrame >= cinemaFile.getFrameCount()){
			currentFrame -= cinemaFile.getFrameCount();
		}
		while(currentFrame < 0){
			currentFrame += cinemaFile.getFrameCount();
		}
	}
	
	private void drawCurrentFrame(){
		cinemaFile.getFrame(currentFrame).draw(offset);
	}

	@Override
	public void run()
	{
		if(running){
			if(msCounter >= frameDuration){
				int count = msCounter / frameDuration;
				alterCurrentFrame(reversed? -count : count);
				drawCurrentFrame();
				msCounter -= count * frameDuration;
			}
			msCounter+=msPerTick;
		}
	}
}
