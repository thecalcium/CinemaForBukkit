package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;

public class Ceditshow_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Ceditshow_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			if(cinema.getCinemaEditor() != null){
				if(cinema.getRegion().getPos1()!=null){
					int index = 0;
					try{
						index = Integer.parseInt(args[0]);
					}catch(NumberFormatException nfe){
						sender.sendMessage("Invalid value for parameter \"index\"");
					}
					cinema.getCinemaEditor().showFrame(index);
				}else{
					sender.sendMessage("Position 1 not set. Use /cpos1 to set it");
				}
			}else{
				sender.sendMessage("No file in Editor");
			}
			return true;
		}
		return false;
	}
}
