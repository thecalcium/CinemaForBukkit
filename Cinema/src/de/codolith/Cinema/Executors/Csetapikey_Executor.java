package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;

public class Csetapikey_Executor implements CommandExecutor {
	private Cinema cinema;
	
	public Csetapikey_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1){
			cinema.getVersionChecker().setApiKey(args[0]);
			cinema.getConfig().set("Plugin.ApiKey", args[0]);
			sender.sendMessage("Api key set to \""+args[0]+"\"");
			return true;
		}else if(args.length == 0){
			cinema.getVersionChecker().setApiKey(null);
			cinema.getConfig().set("Plugin.ApiKey", null);
			sender.sendMessage("Api key removed");
			return true;
		}
		return false;
	}
}
