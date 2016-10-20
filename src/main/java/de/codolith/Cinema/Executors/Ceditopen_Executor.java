package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaEditor;
import de.codolith.Cinema.CinemaFile;
import de.codolith.Cinema.Messages;

public class Ceditopen_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Ceditopen_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==1){
			try {
				if(cinema.getCinemaEditor() !=null){
					sender.sendMessage(cinema.getMessage(Messages.animation_still_loaded));
					//sender.sendMessage("There is still an animation loaded in the editor. You have to close it first with /ceditclose");
					return true;
				}
				File file = new File(cinema.getExtDataFolder(),args[0]);
				if(file.exists()){
					CinemaEditor cinemaEditor = new CinemaEditor(cinema, new CinemaFile(file), sender);
					cinema.setCinemaEditor(cinemaEditor);
					//sender.sendMessage("Animation \""+args[0]+"\" loaded");
					sender.sendMessage(String.format(cinema.getMessage(Messages.animation_X_loaded),args[0]));
				}else{
					//sender.sendMessage("Animation \""+args[0]+"\" does not exist");
					sender.sendMessage(String.format(cinema.getMessage(Messages.animation_X_doesnt_exist),args[0]));
				}
			} catch (IOException e) {
				sender.sendMessage(cinema.getMessage(Messages.error_see_console));
				//sender.sendMessage("An error occoured while opening that file. See server console for more infos");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
