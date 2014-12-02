package de.codolith.Cinema.Executors;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaSaver;

public class Csave_Executor implements CommandExecutor{

	private Cinema cinema;
	
	public Csave_Executor(Cinema cinema, boolean delta){
		this.cinema = cinema;
		this.delta = delta;
	}
	
	private boolean delta;
	
	public boolean isDelta() {
		return delta;
	}

	public void setDelta(boolean delta) {
		this.delta = delta;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 1 || args.length == 2){
			Location pos1 = cinema.getRegion(sender).getPos1(), pos2 = cinema.getRegion(sender).getPos2();
			if(pos1 == null || pos2 == null)
			{
				sender.sendMessage("Positions were not set. can't continue");
				return true;
			}
			if(pos1.getWorld() != pos2.getWorld()){
				sender.sendMessage("Both positions have to be in the same world");
				return true;
			}
			int index = Integer.MAX_VALUE;
			String filename=args[0];
			if(args.length == 2){
				try{
					index = Integer.parseInt(args[1]);
				}catch(NumberFormatException nfe){
					sender.sendMessage("Invalid value for parameter \"index\"");
					return true;
				}
			}
			
			CinemaSaver saver = new CinemaSaver(cinema, sender, new File(cinema.getExtDataFolder(), filename),index);
			saver.setDelta(delta);
			saver.setInsert(false);
			saver.start();
			return true;
		}
		return false;
	}

}