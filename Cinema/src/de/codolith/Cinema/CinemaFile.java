package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class CinemaFile {

	public static final int fileVersion = 0;
	public static final String fileID = "cinemaanimation";
	
	private File file= null;
	private String name;
	private LinkedList<Frame> frames = new LinkedList<Frame>();
	
	public CinemaFile(File file) throws IOException{
		this.file = file;
		this.name = file.getName();
		if(file.exists()){
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			String id = raf.readUTF();
			if(!fileID.equals(id)){
				raf.close();
				throw new IllegalArgumentException("this is not a cinema animation");
			}
			int version = raf.readInt();
			switch(version){
			case 0:
				int frameCount = raf.readInt();
				long[] framePositions = new long[frameCount];
				for(int i =0; i< frameCount; i++){
					framePositions[i] = raf.readLong();
				}
				for(int i =0; i< framePositions.length;i++){
					raf.seek(framePositions[i]);
					setFrame(new Frame(raf),i);
				}
				break;
			default:
				raf.close();
				throw new IllegalArgumentException("unknown cinema animation version: "+version);
			}
			raf.close();
		}//else we have an empty file, thus no frames
	}

	public CinemaFile(LinkedList<Frame> frames) {
		this.frames.addAll(frames);
	}

	public void save(File newfile) throws IOException{
		if(newfile != null){
			file = newfile;
		}else if(newfile == null && file == null){
			throw new IllegalArgumentException("You have to provide a file for saving!");
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.writeUTF(fileID);
		raf.writeInt(fileVersion);
		
		raf.writeInt(getFrameCount());
		long dict = raf.getFilePointer();
		for(int i = 0; i < getFrameCount(); i++){
			raf.writeLong(0); //initing dictionary with 0
		}
		int f = 0;
		for(Frame frame : frames){
			long current = raf.getFilePointer();
			raf.seek(dict+8*f);
			raf.writeLong(current); //write pos in dict
			raf.seek(current);
			frame.save(raf);
			f++;
		}
		raf.close();
	}
	
	public File getFile(){
		return file;
	}
	
	public String getName(){
		return name;
	}
	
	public int getFrameCount(){
		return frames.size();
	}
	
	public Frame getFrame(int index){
		index = verifyIndex(index);
		return frames.get(index);
	}
	
	public Frame getPreviousFrame(int index){
		index = verifyIndex(index);
		index--;
		if(index < 0){
			return null;
		}
		return frames.get(index);
	}
	
	public Frame removeFrame(int index){
		index = verifyIndex(index);
		return frames.remove(index);
	}
	
	public int setFrame(Frame frame, int index){
		index = verifyIndex(index);
		if(frames.size()>index){ // overwrite
			frames.remove(index);
		} // else just insert
		frames.add(index,frame);
		return index;
	}
	
	public int insertFrame(Frame frame, int index){
		index = verifyIndex(index);
		frames.add(index, frame);
		return index;
	}
	
	private int verifyIndex(int index){
		if(index > frames.size()){
			index = frames.size();
		}else if(index < 0){
			index = frames.size()+index;
			if(index < 0){
				index = 0;
			}
		}
		return index;
	}
	
	public Frame getFrameFull(int index){
		index = verifyIndex(index);
		Frame retval = new Frame();
		for(int i =0; i<= index;i++){
			retval.destructiveLayOver(frames.get(i));
		}
		return retval;
	}

	public Frame getPreviousFrameFull(int index) {
		index = verifyIndex(index);
		index--;
		if(index < 0){
			return null;
		}
		return getFrameFull(index);
	}

	public LinkedList<Frame> getFrames()
	{
		return frames;
	}
}
