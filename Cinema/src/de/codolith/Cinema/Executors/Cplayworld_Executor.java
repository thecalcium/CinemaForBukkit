package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaFile;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

public class Cplayworld_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cplayworld_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	///cplayworld <ID> <filename> <x> <y> <z> <world> [framedurationInMillis=250] [playcount=0]
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length >= 6 && args.length <= 8){
			String id = args[0];
			String anim = args[1];
			
			int x=0,y=0,z=0;
			World world=null;
			try{
				x = Integer.parseInt(args[2]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"x"));
				return true;
			}
			try{
				y = Integer.parseInt(args[3]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"y"));
				return true;
			}
			try{
				z = Integer.parseInt(args[4]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"z"));
				return true;
			}
			world = Bukkit.getWorld(args[5]);
			if(world == null){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"world"));
			}
			
			int playCount = 0;
			int frameDuration = 250;
			if(args.length>6){
				try{
					frameDuration = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"framedurationInMillis"));
					return true;
				}
				if(args.length>7){
					try{
						playCount = Integer.parseInt(args[3]);
					}catch(NumberFormatException nfe){
						sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"playcount"));
						return true;
					}
				}
			}

			Location location = new Location(world,x,y,z);
			File file = new File(cinema.getExtDataFolder(),anim);
			if(!file.exists()){
				sender.sendMessage(String.format(cinema.getMessage(Messages.animation_X_doesnt_exist),args[1]));
				//sender.sendMessage("Animation \""+args[1]+"\" does not exist");
				return true;
			}
			if(!cinema.containsCinemaPlayer(id)){
				CinemaFile cinemaFile = null;
				try{
					cinemaFile = new CinemaFile(file);
				}catch(IOException e){
					sender.sendMessage(String.format(cinema.getMessage(Messages.error_opening_animation_X),args[1]));
					//sender.sendMessage("Error opening animation \""+args[1]+"\"");
					e.printStackTrace();
					return true;
				}
				cinema.addCinemaPlayer(new CinemaPlayer(cinema, id, cinemaFile, frameDuration, playCount,location));
				return true;
			}else{
				sender.sendMessage(cinema.getMessage(Messages.player_id_already_in_use));
				return true;
			}
		}
		return false;
	}
}
