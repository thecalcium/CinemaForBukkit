package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;
import de.codolith.Cinema.Region;
import de.codolith.Cinema.Version;

public class Cinemainfo_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cinemainfo_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Region region = cinema.getRegion(sender);
		sender.sendMessage(String.format(cinema.getMessage(Messages.s),region.toString()));
		/*Location pos1 = cinema.getRegion(sender).getPos1();
		Location pos2 = cinema.getRegion(sender).getPos2();
		if(pos1 != null){
			sender.sendMessage("Position 1: " + cinema.getRegion(sender).getPos1AsString());
		}else{
			sender.sendMessage("Position 1 not set");
		}
		if(pos2 != null){
			sender.sendMessage("Position 2: " + cinema.getRegion(sender).getPos2AsString());
		}else{
			sender.sendMessage("Position 2 not set");
		}
		if(pos1 != null && pos2 != null){
			sender.sendMessage("Blocks currently selected: "+cinema.getRegion(sender).getBlockCount());
		}*/
		if(cinema.getConfig().getBoolean("Plugin.ShowApiKeyInInfo") && cinema.getConfig().contains("Plugin.ApiKey")){
			sender.sendMessage(String.format(cinema.getMessage(Messages.apikey_colon_X), cinema.getConfig().getString("Plugin.ApiKey")));
			//sender.sendMessage("ApiKey: "+cinema.getConfig().getString("Plugin.ApiKey"));
		}
		sender.sendMessage(String.format(cinema.getMessage(Messages.cinema_version_colon_X),Cinema.version.toString()));
		//sender.sendMessage("Cinema Version: "+Cinema.version);
		if(cinema.getConfig().getBoolean("Plugin.CheckForNewVersion")){
			Version newest = cinema.getNewestVersion();
			if(newest.isNewerThan(Cinema.version)){
				sender.sendMessage(String.format(cinema.getMessage(Messages.new_version_you_have_X_newest_is_Y),Cinema.version.toString(),newest.toString()));
				//sender.sendMessage("NEW VERSION AVAILABLE FOR CINEMA PLUGIN! You have \""+ Cinema.version + "\" and the newest version is \"" + newest+"\"");
				sender.sendMessage(String.format(cinema.getMessage(Messages.get_cinema_here_colon_X),"http://dev.bukkit.org/server-mods/cinema/files/"));
				//sender.sendMessage("Get Cinema here: http://dev.bukkit.org/server-mods/cinema/files/");
			}
		}
		return true;
	}
}
