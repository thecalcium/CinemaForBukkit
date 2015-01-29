package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Ceditshow_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Ceditshow_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			if(cinema.getCinemaEditor() != null){
				if(cinema.getRegion(sender).getPos1()!=null){
					int index = 0;
					try{
						index = Integer.parseInt(args[0]);
					}catch(NumberFormatException nfe){
						sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"index"));
					}
					cinema.getCinemaEditor().showFrame(index,sender);
				}else{
					sender.sendMessage(String.format(cinema.getMessage(Messages.pos_X_not_set),"1"));
					//sender.sendMessage("Position 1 not set. Use /cpos1 to set it");
				}
			}else{
				sender.sendMessage(cinema.getMessage(Messages.no_file_in_editor));
			}
			return true;
		}
		return false;
	}
}
