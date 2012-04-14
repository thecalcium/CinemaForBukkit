package com.forgewareinc.Cinema;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;

import net.minecraft.server.Material;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Cinema extends JavaPlugin{

	public static final String configPath = "plugins/cinema.cfg";

	Logger log;
	
	public void onEnable(){ 
		log = this.getLogger();
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(configPath);
		} catch (FileNotFoundException e) {
			log.info("Cinema config not found. generating...");
			//generate file
			File temp = new File(configPath);
			try {temp.createNewFile();} catch (IOException e1) {/*cant create file*/ log.info("cant create new cinema config file. using defaults");return; }
			PrintWriter pw = null;
			try {pw = new PrintWriter(new FileOutputStream(configPath));} catch (FileNotFoundException e1) {/*cant happen*/}
			pw.println("setair=" + defaultSetAir);
			pw.println("playcount=" + defaultPlayCount);
			pw.println("restoreafterstop=" + defaultRestoreAfterStop);
			pw.println("frameduration=" + defaultFrameDuration);
			pw.close();
			try {fstream = new FileInputStream(configPath);} catch (FileNotFoundException e1) {/*cant happen*/}
			log.info("generated new Ciname config file");
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while(true){
			try {
				String[] sa = br.readLine().split("=");
				if(sa.length!=2){continue;}
				if(sa[0].equalsIgnoreCase("setair")){
					defaultSetAir = Boolean.parseBoolean(sa[1]);
				}
				else if(sa[0].equalsIgnoreCase("playcount")){
					defaultPlayCount = Integer.parseInt(sa[1]);
				}
				else if(sa[0].equalsIgnoreCase("setair")){
					defaultRestoreAfterStop = Boolean.parseBoolean(sa[1]);
				}
				else if(sa[0].equalsIgnoreCase("setair")){
					defaultFrameDuration = Integer.parseInt(sa[1]);
				}
			} catch (IOException e) {
				break;
			}catch (NullPointerException e) {
				break;
			}
		}
		try {
			br.close();
		} catch (IOException e) {
		}
	}
	
	// default params for cplay
	boolean defaultSetAir = true;
	int defaultPlayCount = 0;
	boolean defaultRestoreAfterStop = true;
	int defaultFrameDuration = 75;
			
	//normal stuff
	boolean saveair = true;
	public HashMap<String,CinemaPlayer> players = new HashMap<String,CinemaPlayer>();
	Location pos1=null,pos2=null;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			if(!sender.hasPermission("Cinema.cinema")){
				sender.sendMessage("you dont have permission to use this plugin");
				return true;
			}
		}
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
				sender.sendMessage("Pos1 set");
				return true;
			}else{
				if(args.length == 4){
					pos1 = new Location(Bukkit.getWorld(args[3]), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					sender.sendMessage("Pos1 set");
					return true;
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
				sender.sendMessage("Pos2 set");
				return true;
			}else{
				if(args.length == 4){
					pos2 = new Location(Bukkit.getWorld(args[3]), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					sender.sendMessage("Pos2 set");
					return true;
				}else{
					return false;
				}
			}
		}
		//csave filename
		else if(cmd.getName().equalsIgnoreCase("csave")){
			if(pos1 == null || pos2 == null)
			{
				sender.sendMessage("Positions were not set. cant continue");
				return true;
			}
			World w = pos1.getWorld();
			if(args.length != 1){
				return false;
			}
			if(pos1.getWorld() != pos2.getWorld()){
				sender.sendMessage("both positions have to be in the same world");
				return true;
			}
			try {
				File file = new File(args[0]);
				
				if(!file.exists()){
					//create file
					file.createNewFile();
					RandomAccessFile raf2 = new RandomAccessFile(args[0],"rw");
					raf2.writeInt(0);
					raf2.close();
					raf2 = null;
				}
				RandomAccessFile raf = new RandomAccessFile(args[0],"rw");
				int framecount = raf.readInt()+1;
				raf.seek(0);//increment framecount and write back
				raf.writeInt(framecount);
				raf.seek(raf.length());//jump to end of file
				//write all blocks of this frame
				int minx = Math.min(pos1.getBlockX(), pos2.getBlockX());
				int miny = Math.min(pos1.getBlockY(), pos2.getBlockY());
				int minz = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
				int maxx = Math.max(pos1.getBlockX(), pos2.getBlockX());
				int maxy = Math.max(pos1.getBlockY(), pos2.getBlockY());
				int maxz = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
				int blockcount = (maxx-minx+1)*(maxy-miny+1)*(maxz-minz+1);
				sender.sendMessage(blockcount + " blocks");
				raf.writeInt(blockcount);
				for(int xx = minx;xx<=maxx;xx++){
					for(int yy = miny;yy<=maxy;yy++){
						for(int zz = minz;zz<=maxz;zz++){
							if(saveair || !w.getBlockAt(xx, yy, zz).getType().equals(Material.AIR)){
								raf.writeInt(xx-pos1.getBlockX());
								raf.writeInt(yy-pos1.getBlockY());
								raf.writeInt(zz-pos1.getBlockZ());
								raf.writeInt(w.getBlockAt(xx, yy, zz).getType().getId());
								raf.writeByte(w.getBlockAt(xx, yy, zz).getData());
							}
						}	
					}
				}
				raf.close();
				sender.sendMessage("frame saved as frame number " + framecount);
			} catch (FileNotFoundException e) {
				sender.sendMessage("File not found");
				return true;
			}
			catch (IOException e) {
				sender.sendMessage("Some IOException occured. maybe you dont have write access?");
				return true;
			}
			return true;
		}
		//cplay playername filename 0/1setair playcount 0/1restoreafterstop framedurationInMillis
		else if(cmd.getName().equalsIgnoreCase("cplay")){
			if(pos1 == null)
			{
				sender.sendMessage("Position1 not set. cant continue");
				return true;
			}
			if(args.length > 6 || args.length < 2){
				return false;
			}
			boolean setair = defaultSetAir;
			boolean restoreafterstop = defaultRestoreAfterStop;
			int playcount = defaultPlayCount;
			int frameduration = defaultFrameDuration;
			if(args.length == 6){
				setair = args[2].equalsIgnoreCase("1");
				playcount = Integer.parseInt(args[3]);
				restoreafterstop = args[4].equalsIgnoreCase("1");
				frameduration = Integer.parseInt(args[5]);
			}
			if(!players.containsKey(args[0])){
				try {;
					players.put(args[0], new CinemaPlayer(args[0], args[1], setair, playcount, restoreafterstop, frameduration, pos1,sender,this));
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
		}
		//cstop playername
		else if(cmd.getName().equalsIgnoreCase("cstop")){
			if(args.length == 1 && players.containsKey(args[0])){
				players.get(args[0]).stop();
				players.remove(args[0]);
				sender.sendMessage("Player stopped");
				return true;
			}else{
				sender.sendMessage("Player doesnt exist");
				return true;
			}
		}
		//cinfo
		else if(cmd.getName().equalsIgnoreCase("cinfo")){
			if(pos1 != null){
				sender.sendMessage("pos1: " + pos1.getBlockX() + " "+pos1.getBlockY() + " "+ pos1.getBlockZ());
			}else{
				sender.sendMessage("pos1 not set");
			}
			if(pos2 != null){
				sender.sendMessage("pos2: " + pos2.getBlockX() + " "+pos2.getBlockY() + " "+ pos2.getBlockZ());
			}else{
				sender.sendMessage("pos2 not set");
			}
			return true;
		}
		return false; 
	}
}
