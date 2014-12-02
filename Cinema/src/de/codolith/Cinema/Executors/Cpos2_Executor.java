package de.codolith.Cinema.Executors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.codolith.Cinema.Cinema;

public class Cpos2_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cpos2_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length == 0){
				//take player pos
				cinema.getRegion(sender).setPos2(player.getLocation().clone());
				sender.sendMessage("Position 2 set to "+cinema.getRegion(sender).getPos2AsString());
				sender.sendMessage(cinema.getRegion(sender).getBlockCount()+" blocks selected");
				return true;
			}else if(args.length == 3){
				int x,y,z;
				try{
					x = Integer.parseInt(args[0]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				try{
					y = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				try{
					z = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				cinema.getRegion(sender).setPos2(new Location(player.getWorld(), x,y,z));
				sender.sendMessage("Position 2 set to "+cinema.getRegion(sender).getPos2AsString());
				sender.sendMessage(cinema.getRegion(sender).getBlockCount()+" blocks selected");
				return true;
			}
		}else{ //is console
			if(args.length == 4){
				int x,y,z;
				World world;
				try{
					x = Integer.parseInt(args[0]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				try{
					y = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				try{
					z = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("invalid value for parameter \"x\"");
					return true;
				}
				world = Bukkit.getWorld(args[3]);
				if(world == null){
					sender.sendMessage("invalid value for parameter \"world\" (world does not exist)");
					return true;
				}
				cinema.getRegion(sender).setPos2(new Location(world,x,y,z));
				sender.sendMessage("Position 2 set to "+cinema.getRegion(sender).getPos2AsString());
				sender.sendMessage(cinema.getRegion(sender).getBlockCount()+" blocks selected");
				return true;
			}
		}
		return false;
	}

}