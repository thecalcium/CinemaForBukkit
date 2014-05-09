package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;

public class Cstop_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cstop_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			if(cinema.containsPlayer(args[0])){
				CinemaPlayer cp = cinema.getPlayer(args[0]);
				cp.stop();
				cinema.removePlayer(cp);
				sender.sendMessage("Player \""+args[0]+"\" stopped");
				return true;
			}else{
				sender.sendMessage("Player \""+args[0]+"\" doesn't exist");
				return true;
			}
		}
		return false;
	}

}
