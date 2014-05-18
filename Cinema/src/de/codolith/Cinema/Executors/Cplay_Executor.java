package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaFile;
import de.codolith.Cinema.CinemaPlayer;

public class Cplay_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cplay_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length>=2 && args.length <=4){
			if(cinema.getRegion().getPos1() == null)
			{
				sender.sendMessage("Position one not set. Can't continue");
				return true;
			}
			//reading args
			int playCount = 0;
			int frameDuration = 250;
			if(args.length > 2){
				try{
					frameDuration = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("Invalid value for parameter \"framedurationInMillis\"");
					return true;
				}
				if(args.length>3){
					try{
						playCount = Integer.parseInt(args[3]);
					}catch(NumberFormatException nfe){
						sender.sendMessage("Invalid value for parameter \"playcount\"");
						return true;
					}
				}
			}

			File file = new File(cinema.getExtDataFolder(),args[1]);
			if(!file.exists()){
				sender.sendMessage("Animation \""+args[1]+"\" does not exist");
				return true;
			}
			if(!cinema.containsPlayer(args[0])){
				CinemaFile cinemaFile = null;
				try{
					cinemaFile = new CinemaFile(file);
				}catch(IOException e){
					sender.sendMessage("Error opening animation \""+args[1]+"\"");
					e.printStackTrace();
					return true;
				}
				cinema.addPlayer(new CinemaPlayer(cinema, args[0], cinemaFile, frameDuration, playCount,cinema.getRegion().getPos1()));
				return true;
			}else{
				sender.sendMessage("Player id already in use");
				return true;
			}
		}
		return false;
	}
}
