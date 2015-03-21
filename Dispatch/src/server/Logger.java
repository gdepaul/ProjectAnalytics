package server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private Boolean DEBUG = false;
	
	private BufferedWriter FOUT;
	private BufferedWriter FERR;
	private DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
	public Logger(String out,String err) {
			
		    try {
				FOUT = new BufferedWriter(new FileWriter(out,true));
				FERR = new BufferedWriter(new FileWriter(err,true));
			} catch (FileNotFoundException fnfe) {
				System.err.println("Could not open file!");
				fnfe.printStackTrace();
			} catch (IOException ie) {
				System.err.println("Could not open file!");
				ie.printStackTrace();
			}

	}

	public void error(String out) {
		if(DEBUG) { System.err.println(out); }
		Date date = new Date();
		try {
			FERR.write(format.format(date) + ": ");
			FERR.write(out);
			FERR.newLine();
			FERR.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print(String out) {
		if(DEBUG) { System.out.println(out); }
		Date date = new Date();
		try {
			FOUT.write(format.format(date) + ": ");
			FOUT.write(out);
			FOUT.newLine();
			FOUT.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
