package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

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
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"index"));
				return true;
			}
			if(cinema.getCinemaEditor() !=null){
				cinema.getCinemaEditor().deleteFrame(index);
			}else{
				sender.sendMessage(cinema.getMessage(Messages.no_file_in_editor));
			}
			return true;
		}
		return false;
	}
}
