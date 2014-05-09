package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;

public class Ceditremove_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Ceditremove_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			int index = 0;
			try{
				index = Integer.parseInt(args[0]);
			}catch(NumberFormatException nfe){
				sender.sendMessage("Invalid value for parameter \"index\"");
				return true;
			}
			if(cinema.getCinemaEditor() !=null){
				cinema.getCinemaEditor().deleteFrame(index);
			}else{
				sender.sendMessage("No file opened in editor");
			}
			return true;
		}
		return false;
	}
}
