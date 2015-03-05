package controller;

/*
 * 
 * Kristoffer Cabulong
 * 
 * Client in order to:
 *  - connect to server by asking for host, port, and username
 *  - display the drawing panel to the user
 *  
 */


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.dispatch.DisconnectDispatch;
import model.dispatch.Dispatch;
import View.Window_dispatch;



public class DispatchClient extends JFrame {
	
	private String clientName; // user name of the client
	
	private Window_dispatch window_dispatch;
	
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	
	/************* Server Handler Private Class *****************/
	// Reads commands from the server and executes them 
	
	private class ServerHandler implements Runnable {
		
		public void run() {
			//Implement the ServerHandler
			//TODO:
			while(true) {
				try {
					Object obj = in.readObject();
					if(obj instanceof Dispatch<?>) { // See if we have a valid command
						Dispatch<DispatchClient> command = (Dispatch<DispatchClient>)obj;
						command.execute(DispatchClient.this);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				} //this will block if no object written
			}
		}
	}
	
	/*************************************************************/
	
	public DispatchClient(){
		// ask the user for a host, port, and user name
		String host = JOptionPane.showInputDialog("Host address:");
		String port = JOptionPane.showInputDialog("Host port:");
		clientName = JOptionPane.showInputDialog("User name:");
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			//Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			String check;
			
			do {
			// write out the name of this client
			out.writeObject(clientName);
			// Check to see if the client is accepted
			check = (String)in.readObject();
			if(check.equals("reject"))
				clientName = JOptionPane.showInputDialog("User name:");
			} while(check.equals("reject"));
			
			// add a listener that sends a disconnect command to the server when closing
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					try {
						out.writeObject(new DisconnectDispatch(clientName));
						out.close();
						in.close();
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
		
		// add a Drawing Panel
		window_dispatch = new Window_dispatch();
		this.add(window_dispatch);
		
		this.setVisible(true);
	}

//	/**
//	 * Updates the chat log. Called by UpdateClientCommands
//	 * 
//	 * @param messages	the current chat log
//	 */
//	public void update(List<PaintObject> objects) {
//		// TODO Auto-generated method stub
//		window_dispatch.update(objects);
//	}
	
	// Main method in order to run the client
	public static void main(String[] args){
		new DispatchClient();
	}

}
