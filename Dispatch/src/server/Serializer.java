package server;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Club;

public class Serializer {
	String directory;
	public Serializer(String directory) {
		this.directory = directory;
	}
	public void saveClub(Club club) {
		DateFormat dateFormat = new SimpleDateFormat("MMdd-HHmmss");
		Date date = new Date(); 
		
		String filename = club.getClubName() + "_backup_" + dateFormat.format(date); 
		try {
			FileOutputStream FOUT = new FileOutputStream(directory + "/" + filename);
			ObjectOutputStream OOUT = new ObjectOutputStream(FOUT);
			OOUT.writeObject(club);
			OOUT.close();
			
		} catch(Exception e) {
			System.err.println("Error saving");
			e.printStackTrace();
		}
		
	}
}
