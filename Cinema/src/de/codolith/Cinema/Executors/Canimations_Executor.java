package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;

public class Canimations_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Canimations_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cinema.getPlayerCount() == 0){
			sender.sendMessage("No Animations active");
		}else if(cinema.getPlayerCount() == 1){
			sender.sendMessage(cinema.getPlayerCount()+" Animation active:");
		}else{
			sender.sendMessage(cinema.getPlayerCount()+" Animations active:");
		}
		for(CinemaPlayer cp : cinema.getPlayers()){
			sender.sendMessage(cp.getId());
		}
		return true;
	}
}
