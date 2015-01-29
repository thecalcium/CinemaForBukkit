package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Creverse_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Creverse_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==1){
			if(cinema.containsCinemaPlayer(args[0])){
				cinema.getCinemaPlayer(args[0]).reverse();
				//sender.sendMessage("Player \""+args[0]+"\" reversed");
				sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_reversed),args[0]));
			}else{
				sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_doesnt_exist),args[0]));
				//sender.sendMessage("No animation with id \""+args[0]+"\"");
			}			
			return true;
		}
		return false;
	}
}
