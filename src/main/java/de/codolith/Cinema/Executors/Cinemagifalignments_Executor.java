package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Alignment;
import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Cinemagifalignments_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cinemagifalignments_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage(Messages.available_alignments);//"Available Alignments (you can use the number too):");
		int i =0;
		for(Alignment al:Alignment.values()){
			sender.sendMessage(String.format(cinema.getMessage(Messages.X_colon_Y), Integer.toString(i),al.name()));
			//sender.sendMessage(i + ": " + al.name());
			i++;
		}
		return true;
	}
}
