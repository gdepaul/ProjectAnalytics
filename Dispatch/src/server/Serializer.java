package server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Serializer {
	String directory;
	public Serializer(String directory) {
		this.directory = directory;
	}
	public void backup(SaveFile sf) {
		DateFormat dateFormat = new SimpleDateFormat("MMdd-HHmmss");
		Date date = new Date(); 
		
		String filename = "backup_" + dateFormat.format(date); 
		try {
			FileOutputStream FOUT = new FileOutputStream(directory + "/" + filename);
			ObjectOutputStream OOUT = new ObjectOutputStream(FOUT);
			OOUT.writeObject(sf);
			OOUT.close();
			
		} catch(Exception e) {
			System.err.println("Error saving");
			e.printStackTrace();
		}		
	}
	public SaveFile loadMostRecent() {
	//--Find the most recent file in the save directory
		File dir = new File(this.directory);
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File saveFile = null;
		for(File file : files) {
			if(file.lastModified() > lastMod) {
				saveFile = file;
				lastMod = file.lastModified();
			}
		}
		return loadFile(saveFile);
	}
	public SaveFile loadFile(File saveFile) {
		SaveFile sf = null;
		
		//--Open an InputStream to the file and read in the SaveFile object
			try {
				FileInputStream FIN = new FileInputStream(saveFile);
				ObjectInputStream OIN = new ObjectInputStream(FIN);
				sf = (SaveFile)OIN.readObject();
				OIN.close();
				
			} catch(NullPointerException npe) {
				System.err.println("Could not load savefile!");
				return null;
			}
			catch(Exception e) {
				System.err.println("Error loading savefile");
				e.printStackTrace();
			}
			return sf;
	}
}
