package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;

public class Cstep_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cstep_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 2){
			if(cinema.containsCinemaPlayer(args[0])){
				CinemaPlayer cp = cinema.getCinemaPlayer(args[0]);
				if(!cp.isPaused()){
					sender.sendMessage("The given player has to be paused for this!");
					return true;
				}
				int count=0;
				try{
					count = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("Invalid value for parameter \"count\"");
					return true;
				}
				cp.alterCurrentFrame(count);
				cp.drawCurrentFrame();
				sender.sendMessage("Stepped "+count);
				return true;
			}else{
				sender.sendMessage("Player \""+args[0]+"\" doesn't exist");
				return true;
			}
		}
		return false;
	}

}
