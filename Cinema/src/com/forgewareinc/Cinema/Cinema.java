package com.forgewareinc.Cinema;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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
	public static final String persistentPath = "plugins/cinemafile";
	public static final String savePath = "plugins/cinema/";
	public static final int version = 151;
	public static int newestVersion = version;
	
	public boolean newVersionAvail(){
		try {
		    URL url = new URL("http://fredlllll.fr.ohost.de/cinemaversion.txt");
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    newestVersion = Integer.parseInt(in.readLine());
		    if(newestVersion > version){
		    	return true;
		    }
		    in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			log.info("couldnt look up version for cinema. host not reachable?");
		}
		return false;
	}
	
	Logger log;
	CinemaEditor cedit;
	public void onEnable(){
		log = this.getLogger();
		
		//create folders in plugins...
		new File("plugins/cinema").mkdirs();
		
		//look for new version
		if(newVersionAvail()){
			log.info("NEW VERSION AVAILABLE FOR CINEMA PLUGIN!!! you have "+ version + " and the newest version is " + newestVersion);
	    	log.info("get it here: https://sourceforge.net/projects/cinemaforbukkit/files/");
		}
		
		
		//load config
		
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
		
		getServer().getPluginManager().registerEvents(new WorldsLoadedListener(this), this);
	}
	
	public void restorePlayers(){
		//load old anims
		File input = new File(persistentPath);
		if(input.exists()){
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile(input,"r");
				int playercount = raf.readInt();
				for(int i = 0;i < playercount;i++){
					CinemaPlayer cp = new CinemaPlayer(raf, this);
					CinemaPlayer.players.put(cp.name, cp);
				}
				raf.close();
			} catch (FileNotFoundException e) {} catch (IOException e) {log.info("Error loading a persistent player: " + e.getMessage());}
		}
	}
	
	public void onDisable(){
		File output = new File(persistentPath);
		if(output.exists()){
			if(!output.delete())//delete old file
				log.info("cant delete old persistent cinema file?? well lets just build up on it then");
		}
		try {
			output.createNewFile();
		} catch (IOException e) {
			log.info("Were not able to create file for cinema persistence");
		}
		try {
			RandomAccessFile raf = new RandomAccessFile(output,"rw");
			raf.writeInt(CinemaPlayer.players.size());
			for(String key: CinemaPlayer.players.keySet()){
				CinemaPlayer.players.get(key).writeToCinemaFile(raf);
			}
			raf.close();
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
	
	// default params for cplay
	boolean defaultSetAir = true;
	int defaultPlayCount = 0;
	boolean defaultRestoreAfterStop = true;
	int defaultFrameDuration = 75;
			
	//normal stuff
	boolean saveair = true;
	//public HashMap<String,CinemaPlayer> players = new HashMap<String,CinemaPlayer>();
	Location pos1=null,pos2=null;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(!sender.hasPermission("Cinema.cinema")){
			sender.sendMessage("you dont have permission to use this plugin");
			return true;
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
			if(args.length != 1 && args.length != 2){
				return false;
			}
			if(pos1.getWorld() != pos2.getWorld()){
				sender.sendMessage("both positions have to be in the same world");
				return true;
			}
			String savefile= savePath+args[0];
			try {
				File file = new File(savefile);
				
				if(!file.exists()){
					//create file
					file.createNewFile();
					RandomAccessFile raf2 = new RandomAccessFile(savefile,"rw");
					raf2.writeInt(0);
					raf2.close();
					raf2 = null;
				}
				RandomAccessFile raf = new RandomAccessFile(savefile,"rw");
				int framecount = raf.readInt();
				int posToSafe =0;
				if(args.length == 2){
					posToSafe = Math.min(Integer.parseInt(args[1]), framecount);
				}else{
					posToSafe = framecount;
				}
				framecount++;
				raf.seek(0);//increment framecount and write back
				raf.writeInt(framecount);
				//raf.seek(raf.length());//jump to end of file
				long rewrite = 0;
				for(int i =0;i< posToSafe;i++){
					int blocks = raf.readInt();
					raf.seek(raf.getFilePointer()+17*blocks);//each block 4 ints and one byte = 17
				}
				rewrite = raf.getFilePointer();//store pos to write frame
				byte[] ba = new byte[(int)(raf.length()-rewrite)];
				for(int i =0;i<ba.length;i++){
					ba[i]= raf.readByte();
				}
				raf.seek(rewrite);
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
				
				raf.write(ba);//write back rest
				raf.close();
				sender.sendMessage("frame saved as frame index " + posToSafe);
			} catch (FileNotFoundException e) {
				sender.sendMessage("File not found");
				return true;
			}
			catch (IOException e) {
				sender.sendMessage("Some IOException occured. maybe the server (bukkit) has no write access on the hdd. it doesnt change anything if you are op or whatever rank");
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
			String filePath = savePath + args[1];
			if(!CinemaPlayer.players.containsKey(args[0])){
				//players.put(args[0], 
				new CinemaPlayer(args[0], filePath, setair, playcount, restoreafterstop, frameduration, pos1,sender,this);//);
				return true;
			}else{
				sender.sendMessage("Player already in use");
				return true;
			}
		}
		//cstop playername
		else if(cmd.getName().equalsIgnoreCase("cstop")){
			if(args.length == 1 && CinemaPlayer.players.containsKey(args[0])){
				CinemaPlayer.players.get(args[0]).stop();
				CinemaPlayer.players.remove(args[0]);
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
			sender.sendMessage("Cinema Version: "+version);
			if(newVersionAvail()){
				sender.sendMessage("NEW VERSION AVAILABLE!!! newest Version: "+newestVersion);
				sender.sendMessage("you have version: "+version);
			}
			return true;
		}
		//ceditopen <filename>
		else if(cmd.getName().equalsIgnoreCase("ceditopen")){
			if(args.length!=1){
				return false;
			}
			try {
				if(cedit !=null){
					cedit.close();cedit=null;
				}
				cedit = new CinemaEditor(savePath +args[0], pos1, sender);
			} catch (IOException e) {
				sender.sendMessage("some error occoured opening that file");
			}
			return true;
		}
		//ceditremove <index>
		else if(cmd.getName().equalsIgnoreCase("ceditremove")){
			try{
				if(cedit !=null){
					cedit.deleteFrame(Integer.parseInt(args[0]));
				}else{
					sender.sendMessage("No file in Editor");
				}
			}catch(Exception e){
				return false;
			}
			return true;
		}
		//ceditsave
		else if(cmd.getName().equalsIgnoreCase("ceditsave")){
			try{
				if(cedit != null){
					cedit.save();
				}else{
					sender.sendMessage("No file in Editor");
				}
			}catch(Exception e){
				return false;
			}
			return true;
		}
		//ceditclose
		else if(cmd.getName().equalsIgnoreCase("ceditclose")){
			if(cedit != null){
				cedit.close();
				cedit = null;
			}else{
				sender.sendMessage("No file in Editor");
			}
			return true;
		}
		//ceditinfo
		else if(cmd.getName().equalsIgnoreCase("ceditinfo")){
			if(cedit != null){
				sender.sendMessage("File in editor: " + cedit.file);
			}else{
				sender.sendMessage("No file in Editor");
			}
			return true;
		}
		//ceditshow <index>
		else if(cmd.getName().equalsIgnoreCase("ceditshow")){
			if(cedit != null){
				if(pos1!=null){
					cedit.cf.showFrameforEditor(Integer.parseInt(args[0]), pos1);
				}else{
					sender.sendMessage("Pos1 not set. use pos1 to determine place for the frame to show");
				}
			}else{
				sender.sendMessage("No file in Editor");
			}
			return true;
		}
		//cinemaremove <filename>
		else if(cmd.getName().equalsIgnoreCase("cinemaremove")){
			if(args.length!=1){
				return false;
			}
			File f = new File(savePath + args[0]);
			if(f.exists()){
				f.delete();
				sender.sendMessage("deleted animation: "+args[0]);
			}else{
				sender.sendMessage("Animation not found");
			}
			return true;
		}
		//cplayers
		else if(cmd.getName().equalsIgnoreCase("cplayers")){
			sender.sendMessage("Cinemaplayers active:");
			for(String name:CinemaPlayer.players.keySet()){
				sender.sendMessage(name);
			}
			return true;
		}
		//cinemagif <in> <out> <alignment>
		else if(cmd.getName().equalsIgnoreCase("cinemagif")){
			if(args.length!=3){
				return false;
			}
			if(Integer.parseInt(args[2])<0 ||Integer.parseInt(args[2])>4){
				sender.sendMessage("invalid alignment");
				return true;
			}
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
			ImageReader reader = readers.next();
			ImageInputStream iis=null;
			try {
				iis = ImageIO.createImageInputStream(new File(savePath + args[0]));
			}catch(FileNotFoundException e){
				sender.sendMessage("input file not found");
				return true;
			} catch (IOException e) {}
			reader.setInput(iis);
			LinkedList<Frame> frames = new LinkedList<Frame>();
			for(int i =0;true;i++){
				try {
					BufferedImage bi = reader.read(i);
					frames.add(new Frame(bi,Alignment.values()[Integer.parseInt(args[2])]));
				} catch (IndexOutOfBoundsException e){
					break;
				} catch (IOException e) {}
			}
			Frame[] fa = new Frame[frames.size()];
			frames.toArray(fa);
			CinemaFile cf = new CinemaFile(fa, savePath + args[1]);
			try {
				cf.save();
				sender.sendMessage("Conversion successful");
			} catch (IOException e) {}
			return true;
		}
		//cinemagifalignments
		else if(cmd.getName().equalsIgnoreCase("cinemagifalignments")){
			sender.sendMessage("Available Alignments:");
			int i =0;
			for(Alignment al:Alignment.values()){
				sender.sendMessage(i + ": " + al.name());
				i++;
			}
			return true;
		}
		return false;
		
	}
}
