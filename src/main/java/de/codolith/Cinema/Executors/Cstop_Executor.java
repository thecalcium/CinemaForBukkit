package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

public class Cstop_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cstop_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			if(cinema.containsCinemaPlayer(args[0])){
				CinemaPlayer cp = cinema.getCinemaPlayer(args[0]);
				cp.stop();
				cinema.removeCinemaPlayer(cp);
				sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_stopped),args[0]));
				return true;
			}else{
				sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_doesnt_exist),args[0]));
				return true;
			}
		}
		return false;
	}

}
