package controller;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Command;
import model.Disconnect_command;
import View.Window_dispatch;


public class Dispatch extends JFrame{
	
	private String clientName;
	
	private Window_dispatch window;
	//This was changed from Window_dispatch. TOFIX
	
	private Socket server;
	private ObjectOutputStream output_stream;
	private ObjectInputStream input_stream;
	
	/************* Server Handler Private Class *****************/
	// Reads commands from the server and executes them 
	
	private class ServerHandler implements Runnable {
		
		public void run() {
			//Implement the ServerHandler
			while(true) {
				try {
					Object obj = input_stream.readObject();
					if(obj instanceof Command<?>) { // See if we have a valid command
						Command<Dispatch> command = (Command<Dispatch>)obj;
						command.execute(Dispatch.this);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				} //this will block if no object written
			}
		}
	}
	
	/*************************************************************/
	
	
	
	
	public Dispatch(){
		// ask the user for a host, port, and user name
				String host = JOptionPane.showInputDialog("Host address:");
				String port = JOptionPane.showInputDialog("Host port:");
				clientName = JOptionPane.showInputDialog("User name:");
				
				if (host == null || port == null || clientName == null)
					return;
				
				try{
					//Open a connection to the server
					server = new Socket(host, Integer.parseInt(port));
					output_stream = new ObjectOutputStream(server.getOutputStream());
					input_stream = new ObjectInputStream(server.getInputStream());
					
					String check;
					
					do {
					// write out the name of this client
					output_stream.writeObject(clientName);
					// Check to see if the client is accepted
					check = (String)input_stream.readObject();
					if(check.equals("reject"))
						clientName = JOptionPane.showInputDialog("User name:");
					} while(check.equals("reject"));
					
					// add a listener that sends a disconnect command to the server when closing
					
					this.addWindowListener(new WindowAdapter(){
						public void windowClosing(WindowEvent arg0) {
							try {
								output_stream.writeObject(new Disconnect_command(clientName));
								output_stream.close();
								input_stream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					
					setupGUI();
					
					new Thread(new ServerHandler()).start();
					
					
				}catch(Exception e){
					e.printStackTrace();
				}

	}
	
	// Create the gui in the client once signed in
	private void setupGUI() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add a Drawing Panel TO FIX!!!!!!!!!!!!!!!!!
		window = new Window_dispatch(clientName, output_stream);
//		this.add(window);
//		//Quick fix via Eclipse was to change from window to component
//		//this.add(window);
		
		this.setVisible(true);
	}

}
