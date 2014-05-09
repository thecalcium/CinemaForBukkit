package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;

public class Ceditclose_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Ceditclose_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cinema.getCinemaEditor() != null){
			cinema.getCinemaEditor().close();
			cinema.setCinemaEditor(null);
		}else{
			sender.sendMessage("No file opened in editor");
		}
		return true;
	}
}
