package de.codolith.Cinema.Executors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Cpos1_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Cpos1_Executor(Cinema cinema){
		this.cinema = cinema;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		int x=0,y=0,z=0;
		World world=null;
		if(sender instanceof Player && args.length == 0){
			Player player = (Player) sender;
			world = player.getWorld();
			Location loc = player.getLocation();
			x = loc.getBlockX();
			y = loc.getBlockY();
			z = loc.getBlockZ();
		}else if(args.length >= 3){
			if(sender instanceof Player){
				Player player = (Player) sender;
				world = player.getWorld();
			}
			try{
				x = Integer.parseInt(args[0]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"x"));
				return true;
			}
			try{
				y = Integer.parseInt(args[1]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"y"));
				return true;
			}
			try{
				z = Integer.parseInt(args[2]);
			}catch(NumberFormatException nfe){
				sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X),"z"));
				return true;
			}
			if(args.length == 4){
				world = Bukkit.getWorld(args[3]);
				if(world == null){
					sender.sendMessage(String.format(cinema.getMessage(Messages.invalid_value_for_param_X_no_quot),"\"world\" (world \""+args[3]+"\" does not exist)"));
					return true;
				}
			}	
		}
		if(world == null){
			return false;
		}
		cinema.getRegion(sender).setPos1(new Location(world,x,y,z));
		sender.sendMessage(String.format(cinema.getMessage(Messages.pos_X_set_to_Y),"1",cinema.getRegion(sender).getPos1AsString()));
		sender.sendMessage(String.format(cinema.getMessage(Messages.X_blocks_selected),Integer.toString(cinema.getRegion(sender).getBlockCount())));
		return true;
		/*if(sender instanceof Player){
			Player player = (Player) sender;
			world = player.getWorld();
			if(args.length == 0){
				//take player pos
				Location loc = player.getLocation();
				x = loc.getBlockX();
				y = loc.getBlockY();
				z = loc.getBlockZ();
			}else if(args.length == 3){
				try{
					x = Integer.parseInt(args[0]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"x"));
					return true;
				}
				try{
					y = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"y"));
					return true;
				}
				try{
					z = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"z"));
					return true;
				}
			}
		}else{ //is console
			if(args.length == 4){
				try{
					x = Integer.parseInt(args[0]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"x"));
					return true;
				}
				try{
					y = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"y"));
					return true;
				}
				try{
					z = Integer.parseInt(args[2]);
				}catch(NumberFormatException nfe){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"z"));
					return true;
				}
				world = Bukkit.getWorld(args[3]);
				if(world == null){
					sender.sendMessage(String.format(cinema.getMessage("@invalid_value_for_param_X"),"world (world \""+args[3]+"\" does not exist)"));
					return true;
				}
			}
		}*/
	}

}
