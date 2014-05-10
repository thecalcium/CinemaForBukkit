package com.forgewareinc.Cinema;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class CinemaEditor {

	CinemaFile cf;
	CommandSender sender;
	public String file;
	public CinemaEditor(String filePath, Location loc, CommandSender sender) throws IOException{
		cf = new CinemaFile(filePath, loc, true, true, true);
		this.file = filePath;
		sender.sendMessage("Loaded file into Cinemaeditor with " + cf.frameCount() + " frames");
		this.sender = sender;
	}

	public void deleteFrame(int frameIndex){
		if(cf.deleteFrame(frameIndex)){
			sender.sendMessage("Deleted Frame "+frameIndex);
		}else{
			sender.sendMessage("Index was out of Range");
		}
	}

	public void save() throws IOException {
		cf.save();
		sender.sendMessage("Saved file");
	}
	
	public void show(int index,Location pos1){
		cf.showFrameforEditor(index, pos1);
	}
	
	public void close(){
		cf.unload();
		sender = null;
	}
}
