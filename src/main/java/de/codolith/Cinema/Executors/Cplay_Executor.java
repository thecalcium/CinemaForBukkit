package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaFile;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

public class Cplay_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cplay_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length>=2 && args.length <=4){
			if(cinema.getRegion(sender).getPos1() == null)
			{
				sender.sendMessage(cinema.getMessage(Messages.positions_not_set));
				return true;
			}
			//reading args
			int playCount = 0;
			int frameDuration = 250;
			if(args.length > 2){
				try{
					frameDuration = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"framedurationInMillis"));
					//sender.sendMessage("Invalid value for parameter \"framedurationInMillis\"");
					return true;
				}
				if(args.length>3){
					try{
						playCount = Integer.parseInt(args[3]);
					}catch(NumberFormatException nfe){
						sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"playcount"));
						//sender.sendMessage("Invalid value for parameter \"playcount\"");
						return true;
					}
				}
			}

			File file = new File(cinema.getExtDataFolder(),args[1]);
			if(!file.exists()){
				sender.sendMessage(String.format(cinema.getMessage(Messages.animation_X_doesnt_exist),args[1]));
				//sender.sendMessage("Animation \""+args[1]+"\" does not exist");
				return true;
			}
			if(!cinema.containsCinemaPlayer(args[0])){
				CinemaFile cinemaFile = null;
				try{
					cinemaFile = new CinemaFile(file);
				}catch(IOException e){
					sender.sendMessage(String.format(cinema.getMessage(Messages.error_opening_animation_X),args[1]));
					//sender.sendMessage("Error opening animation \""+args[1]+"\"");
					e.printStackTrace();
					return true;
				}
				cinema.addCinemaPlayer(new CinemaPlayer(cinema, args[0], cinemaFile, frameDuration, playCount,cinema.getRegion(sender).getPos1()));
				return true;
			}else{
				sender.sendMessage(cinema.getMessage(Messages.player_id_already_in_use));
				return true;
			}
		}
		return false;
	}
}
