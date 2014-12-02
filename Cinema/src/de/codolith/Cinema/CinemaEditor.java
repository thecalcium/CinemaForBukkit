package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.bukkit.command.CommandSender;

public class CinemaEditor {
	private Cinema cinema;
	private CommandSender owner;
	private CinemaFile cinemaFile;

	public CinemaEditor(Cinema cinema, CinemaFile cinemaFile, CommandSender owner) throws IOException{
		this.cinema = cinema;
		this.owner = owner;
		this.cinemaFile = cinemaFile;
	}

	public void deleteFrame(int index) {
		cinemaFile.removeFrame(index);
	}

	public void save() throws IOException {
		cinemaFile.save(null);
	}
	
	public void save(File file) throws IOException
	{
		cinemaFile.save(file);
	}

	public void close() {
		//not really anything to do here... yet
	}

	public void sendInfo(CommandSender sender) {
		sender.sendMessage("Animation \""+cinemaFile.getName()+"\" with "+cinemaFile.getFrameCount()+" frames opened by "+owner.getName());
	}

	public void showFrame(int index,CommandSender sender) {
		Frame frame = cinemaFile.getFrame(index);
		frame.draw(cinema.getRegion(sender).getPos1());
	}

	public void reverse() {
		Collections.reverse(cinemaFile.getFrames());
	}
}
