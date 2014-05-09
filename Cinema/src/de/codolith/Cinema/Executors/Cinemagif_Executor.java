package de.codolith.Cinema.Executors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Alignment;
import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaFile;
import de.codolith.Cinema.Frame;

public class Cinemagif_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cinemagif_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==3){
			Alignment alignment = Alignment.vertical0;
			try{
				int align = Integer.parseInt(args[2]);
				alignment = Alignment.values()[align];
			}catch(NumberFormatException nfe){
				try{
					alignment = Alignment.valueOf(args[2]);
				}catch(IllegalArgumentException e){
					sender.sendMessage("Invalid value for parameter \"alignment\"");
				}
			} catch(ArrayIndexOutOfBoundsException ex){
				sender.sendMessage("Invalid value for parameter \"alignment\"");
			}
			
			File inputFile = new File(cinema.getExtDataFolder(),args[0]);
			if(!inputFile.exists()){
				sender.sendMessage("Input file not found \""+args[0]+"\"");
				return true;
			}
			File outputFile = new File(cinema.getExtDataFolder(),args[1]);
			
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
			ImageReader reader = readers.next();
			ImageInputStream iis=null;
			try {
				iis = ImageIO.createImageInputStream(inputFile);
			}catch (IOException e) {
				e.printStackTrace();
			}
			reader.setInput(iis);
			LinkedList<Frame> frames = new LinkedList<Frame>();
			for(int i =0;true;i++){
				try {
					BufferedImage bi = reader.read(i);
					frames.add(new Frame(bi,alignment));
				} catch (IndexOutOfBoundsException e){
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			CinemaFile cf = new CinemaFile(frames);
			try {
				cf.save(outputFile);
				sender.sendMessage("Conversion successful");
			} catch (IOException e) {
				sender.sendMessage("Conversion failed, can't save file");
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
}
