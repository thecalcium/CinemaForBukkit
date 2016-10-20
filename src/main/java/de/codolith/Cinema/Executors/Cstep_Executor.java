package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

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
					sender.sendMessage(cinema.getMessage(Messages.player_has_to_be_paused));
					//sender.sendMessage("The given player has to be paused for this!");
					return true;
				}
				int count=0;
				try{
					count = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"count"));
					//sender.sendMessage("Invalid value for parameter \"count\"");
					return true;
				}
				cp.alterCurrentFrame(count);
				cp.drawCurrentFrame();
				sender.sendMessage(String.format(cinema.getMessage(Messages.stepped_X_frames),Integer.toString(count)));
				//sender.sendMessage("Stepped "+count);
				return true;
			}else{
				sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_doesnt_exist),args[0]));
				//sender.sendMessage("Player \""+args[0]+"\" doesn't exist");
				return true;
			}
		}
		return false;
	}

}
