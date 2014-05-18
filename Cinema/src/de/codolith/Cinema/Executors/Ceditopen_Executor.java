package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaEditor;
import de.codolith.Cinema.CinemaFile;

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
					sender.sendMessage("There is still an animation loaded in the editor. You have to close it first with /ceditclose");
					return true;
				}
				File file = new File(cinema.getExtDataFolder(),args[0]);
				if(file.exists()){
					CinemaEditor cinemaEditor = new CinemaEditor(cinema, new CinemaFile(file), sender);
					cinema.setCinemaEditor(cinemaEditor);
					sender.sendMessage("Animation \""+args[0]+"\" loaded");
				}else{
					sender.sendMessage("Animation \""+args[0]+"\" does not exist");
				}
			} catch (IOException e) {
				sender.sendMessage("An error occoured while opening that file. See server console for more infos");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
