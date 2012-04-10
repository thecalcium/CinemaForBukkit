package com.forgewareinc.Cinema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Cinema extends JavaPlugin{

	boolean saveair = true;
	public HashMap<String,CinemaPlayer> players = new HashMap<String,CinemaPlayer>();
	Location pos1=null,pos2=null;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		//csaveair <0/1>
		if(cmd.getName().equalsIgnoreCase("csaveair")){
			if(args.length!=1){
				return false;
			}
			if(args[0].equals("0")){
				saveair = false;
				sender.sendMessage("Will not save air-blocks anymore");
			}else if(args[0].equals("1")){
				saveair = true;
				sender.sendMessage("Will save air-blocks");
			}else{
				return false;
			}
			return true;
		}
		//cpos1 <x> <y> <z> <world>
		else if(cmd.getName().equalsIgnoreCase("cpos1")){
			if(sender instanceof Player){
				if(args.length == 0){
					//take player pos
					pos1 = ((Player)sender).getLocation();
				}else if(args.length == 3){
					pos1 = new Location(((Player)sender).getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}else{
					return false;
				}
				return true;
			}else{
				if(args.length == 4){
					pos1 = new Location(Bukkit.getWorld(args[3]), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}else{
					return false;
				}
			}
		}
		//cpos2 <x> <y> <z> <world>
		else if(cmd.getName().equalsIgnoreCase("cpos2")){
			if(sender instanceof Player){
				if(args.length == 0){
					//take player pos
					pos2 = ((Player)sender).getLocation();
				}else if(args.length == 3){
					pos2 = new Location(((Player)sender).getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}else{
					return false;
				}
				return true;
			}else{
				if(args.length == 4){
					pos2 = new Location(Bukkit.getWorld(args[3]), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}else{
					return false;
				}
			}
		}
		//csave filename <worldname if console>
		else if(cmd.getName().equalsIgnoreCase("csave")){
			if(pos1 == null || pos2 == null)
			{
				sender.sendMessage("Positions were not set. cant continue");
				return true;
			}
			World w = null;
			if(sender instanceof Player){
				if(args.length != 1){
					return false;
				}
				w = ((Player)sender).getWorld();
			}else{
				if(args.length != 2){
					return false;
				}
				w = Bukkit.getWorld(args[1]);
			}
			try {
				File file = new File(args[0]);
				RandomAccessFile raf;
				if(!file.exists()){
					//create file
					file.createNewFile();
					raf = new RandomAccessFile(args[0],"w");
					raf.writeInt(0);
					raf.close();
				}
				raf = new RandomAccessFile(args[0],"rw");
				int framecount = raf.readInt()+1;
				raf.seek(0);//increment framecount and write back
				raf.write(framecount);
				raf.seek(raf.length());//jump to end of file
				/*for(int i =0; i<framecount;i++){
					//read through frames, overread them
					int blocks = raf.readInt();
					raf.seek(raf.getFilePointer()+blocks*16);//one block 16 bytes
				}*/
				//write all blocks of this frame
				int minx = Math.min(pos1.getBlockX(), pos2.getBlockX());
				int miny = Math.min(pos1.getBlockY(), pos2.getBlockY());
				int minz = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
				int maxx = Math.max(pos1.getBlockX(), pos2.getBlockX());
				int maxy = Math.max(pos1.getBlockY(), pos2.getBlockY());
				int maxz = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
				int blockcount = (maxx-minx)*(maxy-miny)*(maxz-minz);
				raf.write(blockcount);
				for(int xx = minx;xx<maxx;xx++){
					for(int yy = miny;yy<maxy;yy++){
						for(int zz = minz;zz<maxz;zz++){
							raf.write(xx-pos1.getBlockX());
							raf.write(yy-pos1.getBlockY());
							raf.write(zz-pos1.getBlockZ());
							raf.write(w.getBlockAt(xx, yy, zz).getType().getId());
						}	
					}
				}
				raf.close();
			} catch (FileNotFoundException e) {
				sender.sendMessage("File not found");
				return true;
			}
			catch (IOException e) {
				sender.sendMessage("Some IOException occured. maybe you dont have write access?");
				return true;
			}
		}
		//cplay playername filename 0/1setair framedurationInMillis <world as console>
		else if(cmd.getName().equalsIgnoreCase("cplay")){
			if(pos1 == null)
			{
				sender.sendMessage("Positions were not set. cant continue");
				return true;
			}
			if(sender instanceof Player && args.length==4){
				if(!players.containsKey(args[0])){
					try {
						boolean setair = args[2].equalsIgnoreCase("1");						
						players.put(args[0], new CinemaPlayer(args[1], setair, Integer.parseInt(args[3]), pos1, ((Player)sender).getWorld()));
					} catch(FileNotFoundException e){
						sender.sendMessage("File not found");
					} catch (IOException e) {
						sender.sendMessage("Some IOException occured. maybe the file is corrupted?");
					}
					return true;
				}else{
					sender.sendMessage("Player already in use");
					return true;
				}
			}else if(args.length==5){
				if(!players.containsKey(args[0])){
					try {
						boolean setair = args[2].equalsIgnoreCase("1");						
						players.put(args[0], new CinemaPlayer(args[1], setair, Integer.parseInt(args[3]), pos1, Bukkit.getWorld(args[4])));
					} catch(FileNotFoundException e){
						sender.sendMessage("File not found");
					} catch (IOException e) {
						sender.sendMessage("Some IOException occured. maybe the file is corrupted?");
					}
					return true;
				}else{
					sender.sendMessage("Player already in use");
					return true;
				}
			}else{
				return false;
			}
		}
		//cstop playername
		else if(cmd.getName().equalsIgnoreCase("cstop")){
			if(players.containsKey(args[0])){
				players.get(args[0]).stop();
				sender.sendMessage("Player stopped");
			}else{
				sender.sendMessage("Player doesnt exist");
			}
		}
		return false; 
	}
}
