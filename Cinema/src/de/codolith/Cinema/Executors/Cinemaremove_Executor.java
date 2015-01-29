package de.codolith.Cinema.Executors;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Cinemaremove_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cinemaremove_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==1){
			File f = new File(cinema.getExtDataFolder(),args[0]);
			if(f.exists()){
				f.delete();
				sender.sendMessage(String.format(cinema.getMessage(Messages.deleted_animation_X),args[0]));
				//sender.sendMessage("Deleted animation \""+args[0]+"\"");
			}else{
				sender.sendMessage(String.format(cinema.getMessage(Messages.no_animation_with_name_X),args[0]));
				//sender.sendMessage("No animation with name \""+args[0]+"\"");
			}
			return true;
		}
		return false;
	}
}
