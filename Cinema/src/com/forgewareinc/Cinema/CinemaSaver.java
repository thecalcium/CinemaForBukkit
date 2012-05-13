package com.forgewareinc.Cinema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class CinemaSaver extends Thread {

	String[] args;
	World w;
	CommandSender sender;
	boolean delta;
	String savePath;
	Location pos1;
	Location pos2;
	Logger log;
	public CinemaSaver(String[] args, World w, CommandSender sender,boolean delta,String savePath,Location pos1,Location pos2){
		log = Bukkit.getLogger();
		this.args = args;
		this.w = w;
		this.sender = sender;
		this.delta = delta;
		this.savePath = savePath;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	public void run(){
		if(delta){
			csaveDelta();
		}else{
			csave();
		}
	}

	private void csaveDelta() {
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
			int posToSave =0;
			if(args.length == 2){
				posToSave = Math.min(Integer.parseInt(args[1]), framecount);
			}else{
				posToSave = framecount;
			}
			framecount++;
			raf.seek(0);//increment framecount and write back
			raf.writeInt(framecount);
			//raf.seek(raf.length());//jump to end of file
			long rewrite = 0;
			Frame preFrame=null;
			myBlockBuffer preblocks = new myBlockBuffer();
			if(posToSave>0){
				for(int i =0;i< posToSave;i++){
					int blocks = raf.readInt();//build frame from all pre frames
					for(int b = 0; b < blocks;b++){
						int x = raf.readInt();
						int y = raf.readInt();
						int z = raf.readInt();
						myBlock mb = new myBlock(x,y,z,Material.getMaterial(raf.readInt()),raf.readByte());
						preblocks.add(mb);
					}
					//raf.seek(raf.getFilePointer()+17*blocks);//each block 4 ints and one byte = 17
				}
			}
			myBlock[] mba = preblocks.toArray();
			preblocks = null;
			preFrame = new Frame(mba,null);
			
			
			/*if(posToSave >0){
				int preBlocks = raf.readInt();
				myBlock[] mba = new myBlock[preBlocks];
				for(int i = 0;i<preBlocks;i++){
					int x = raf.readInt();
					int y = raf.readInt();
					int z = raf.readInt();
					mba[i] = new myBlock(x,y,z,Material.getMaterial(raf.readInt()),raf.readByte());
				}
				preFrame = new Frame(mba,null);
			}*/
			
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
			long blockCountPos = raf.getFilePointer();
			raf.writeInt(blockcount);//write it now so i dont overwrite other stuff later
			int nowx=0,nowy=0,nowz=0,nowmat=0,nowdata=0;
			for(int xx = minx;xx<=maxx;xx++){
				for(int yy = miny;yy<=maxy;yy++){
					for(int zz = minz;zz<=maxz;zz++){
						nowx = xx-pos1.getBlockX();
						nowy = yy-pos1.getBlockY();
						nowz = zz-pos1.getBlockZ();
						nowmat = w.getBlockAt(xx, yy, zz).getType().getId();
						nowdata = w.getBlockAt(xx, yy, zz).getData();
						if(posToSave >0){
							myBlock preblock = preFrame.getBlockAt(nowx, nowy, nowz);
							if(preblock!= null){
								if(preblock.m.getId() != nowmat || preblock.data != nowdata ){
									raf.writeInt(nowx);
									raf.writeInt(nowy);
									raf.writeInt(nowz);
									raf.writeInt(nowmat);
									raf.writeByte(nowdata);
								}else{
									blockcount--;
								}
							}else{
								raf.writeInt(nowx);
								raf.writeInt(nowy);
								raf.writeInt(nowz);
								raf.writeInt(nowmat);
								raf.writeByte(nowdata);
							}
						}else{
							raf.writeInt(nowx);
							raf.writeInt(nowy);
							raf.writeInt(nowz);
							raf.writeInt(nowmat);
							raf.writeByte(nowdata);
						}
					}	
				}
			}
			sender.sendMessage(blockcount + " blocks");
			rewrite = raf.getFilePointer();
			raf.seek(blockCountPos);
			raf.writeInt(blockcount);
			raf.seek(rewrite);
			raf.write(ba);//write back rest
			raf.close();
			sender.sendMessage("frame saved as frame index " + posToSave);
		} catch (FileNotFoundException e) {
			sender.sendMessage("File not found");
		}
		catch (IOException e) {
			sender.sendMessage("Some IOException occured. maybe the server (bukkit) has no write access on the hdd. it doesnt change anything if you are op or whatever rank");
			log.info(e.getMessage());
		}
	}
	
	private void csave(){
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
						raf.writeInt(xx-pos1.getBlockX());
						raf.writeInt(yy-pos1.getBlockY());
						raf.writeInt(zz-pos1.getBlockZ());
						raf.writeInt(w.getBlockAt(xx, yy, zz).getType().getId());
						raf.writeByte(w.getBlockAt(xx, yy, zz).getData());
					}	
				}
			}
			
			raf.write(ba);//write back rest
			raf.close();
			sender.sendMessage("frame saved as frame index " + posToSafe);
		} catch (FileNotFoundException e) {
			sender.sendMessage("File not found");
		}
		catch (IOException e) {
			sender.sendMessage("Some IOException occured. maybe the server (bukkit) has no write access on the hdd. it doesnt change anything if you are op or whatever rank");
		}
	}
}
