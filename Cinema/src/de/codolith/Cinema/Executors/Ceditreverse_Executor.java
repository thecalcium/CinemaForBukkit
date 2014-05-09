package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;

public class Ceditreverse_Executor implements CommandExecutor {
private Cinema cinema;
	
	public Ceditreverse_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cinema.getCinemaEditor() !=null){
			cinema.getCinemaEditor().reverse();
		}else{
			sender.sendMessage("No file opened in editor");
		}
		return true;
	}
}
