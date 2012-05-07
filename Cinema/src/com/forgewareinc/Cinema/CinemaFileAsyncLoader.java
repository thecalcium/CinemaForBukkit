package com.forgewareinc.Cinema;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class CinemaFileAsyncLoader extends Thread{

	CinemaPlayer cpl;
	private CommandSender sender;
	private String filePath;
	private Location loc;
	private boolean setAir;
	private boolean restoreafterstop;
	public CinemaFileAsyncLoader(String filePath,Location loc,boolean setAir,boolean restoreafterstop,boolean forEditor,CommandSender sender,CinemaPlayer cpl){
		this.cpl = cpl;
		
		this.sender =sender;
		this.filePath = filePath;
		this.loc = loc;
		this.setAir = setAir;
		this.restoreafterstop = restoreafterstop;
	}
	
	public void run(){
		try {
			cpl.cf = new CinemaFile(filePath, loc, setAir,restoreafterstop,false);
			cpl.mystart(sender);
		} catch(FileNotFoundException e){
			sender.sendMessage("File not found");
			cpl.removeDueToEx();
		} catch (IOException e) {
			sender.sendMessage("Some IOException occured. maybe the file is corrupted?");
			cpl.removeDueToEx();
		}
	}
}
