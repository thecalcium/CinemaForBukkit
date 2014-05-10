package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;

public class CinemaSaver extends Thread {

	private Cinema cinema;
	private File file;
	private int index;
	private CommandSender sender;
	private boolean delta = false, insert = false;
	
	public CinemaSaver(Cinema cinema,CommandSender sender, File file, int index){
		this.cinema = cinema;
		this.sender = sender;
		this.file = file;
		this.index = index;
	}	

	@Override
	public void run(){
		CinemaFile cinemaFile=null;
		try {
			cinemaFile = new CinemaFile(file);
		} catch (IOException e) {
			sender.sendMessage("Error while saving");
			e.printStackTrace();
			return;
		}
		int insertedAs= 0;
		Frame frame = null;
		if(isDelta()){
			Frame previousFrame = cinemaFile.getPreviousFrameFull(index);
			frame = new Frame(cinema.getRegion(),previousFrame);
		}else{
			frame = new Frame(cinema.getRegion());
		}
		if(isInsert()){
			insertedAs=cinemaFile.insertFrame(frame, index);
			sender.sendMessage("Inserted frame at "+insertedAs+" ("+frame.getBlockCount()+" blocks)");
		}else{
			insertedAs=cinemaFile.setFrame(frame, index);
			sender.sendMessage("Saved frame at "+insertedAs+" ("+frame.getBlockCount()+" blocks)");
		}
		try
		{
			cinemaFile.save(null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isInsert() {
		return insert;
	}

	public void setInsert(boolean insert) {
		this.insert = insert;
	}

	public boolean isDelta() {
		return delta;
	}

	public void setDelta(boolean delta) {
		this.delta = delta;
	}

}
