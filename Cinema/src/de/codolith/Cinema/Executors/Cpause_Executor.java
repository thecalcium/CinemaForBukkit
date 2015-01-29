package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;

public class Cpause_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cpause_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			if(cinema.containsCinemaPlayer(args[0])){
				CinemaPlayer cp = cinema.getCinemaPlayer(args[0]);
				cp.pause();
				sender.sendMessage("Player \""+args[0]+"\" paused");
				return true;
			}else{
				sender.sendMessage("Player \""+args[0]+"\" doesn't exist");
				return true;
			}
		}
		return false;
	}

}
