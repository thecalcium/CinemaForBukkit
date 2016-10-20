package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

public class Canimations_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Canimations_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cinema.getCinemaPlayerCount() == 0){
			sender.sendMessage(cinema.getMessage(Messages.no_anims_active));
		}else if(cinema.getCinemaPlayerCount() == 1){
			sender.sendMessage(String.format(cinema.getMessage(Messages.X_anim_active),Integer.toString(cinema.getCinemaPlayerCount())));
		}else{
			sender.sendMessage(String.format(cinema.getMessage(Messages.X_anims_active),Integer.toString(cinema.getCinemaPlayerCount())));
		}
		for(CinemaPlayer cp : cinema.getCinemaPlayers()){
			sender.sendMessage(cp.getId());
		}
		return true;
	}
}
