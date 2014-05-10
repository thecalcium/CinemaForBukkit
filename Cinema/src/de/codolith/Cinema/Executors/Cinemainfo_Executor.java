package de.codolith.Cinema.Executors;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Version;

public class Cinemainfo_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cinemainfo_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Location pos1 = cinema.getRegion().getPos1();
		Location pos2 = cinema.getRegion().getPos2();
		if(pos1 != null){
			sender.sendMessage("Position 1: " + cinema.getRegion().getPos1AsString());
		}else{
			sender.sendMessage("Position 1 not set");
		}
		if(pos2 != null){
			sender.sendMessage("Position 2: " + cinema.getRegion().getPos2AsString());
		}else{
			sender.sendMessage("Position 2 not set");
		}
		if(pos1 != null && pos2 != null){
			sender.sendMessage("Blocks currently selected: "+cinema.getRegion().getBlockCount());
		}
		if(cinema.getConfig().getBoolean("Plugin.ShowApiKeyInInfo") && cinema.getConfig().contains("Plugin.ApiKey")){
			sender.sendMessage("ApiKey: "+cinema.getConfig().getString("Plugin.ApiKey"));
		}
		sender.sendMessage("Cinema Version: "+Cinema.version);
		if(cinema.getConfig().getBoolean("Plugin.CheckForNewVersion")){
			Version newest = cinema.getNewestVersion();
			if(newest.isNewerThan(Cinema.version)){
				sender.sendMessage("NEW VERSION AVAILABLE FOR CINEMA PLUGIN! You have \""+ Cinema.version + "\" and the newest version is \"" + newest+"\"");
				sender.sendMessage("Get Cinema here: http://dev.bukkit.org/server-mods/cinema/files/");
			}
		}
		return true;
	}
}
