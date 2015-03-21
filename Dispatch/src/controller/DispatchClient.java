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


import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Club;
import model.dispatch.AddObjectDispatch;
import model.dispatch.DisconnectDispatch;
import model.dispatch.Dispatch;
import model.dispatchObject.AddActiveClub;
import model.dispatchObject.CashDrop;
import model.dispatchObject.ChangeDrop;
import model.dispatchObject.InitialCashBox;
import model.dispatchObject.RemoveActiveClub;
import model.dispatchObject.TicketDrop;
import View.Panel_Dispatch;


public class DispatchClient extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9046456846310614397L;

	private List<Club> activeClubs;
	
	private String userName; // user name of the client
	
	private Panel_Dispatch dispatchPanel;
	
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	
	/************* Server Handler Private Class *****************/
	// Reads commands from the server and executes them 
	
	private class ServerHandler implements Runnable {
		
		public void run() {
			//Implement the ServerHandler
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
//		// ask the user for a host, port, and user name
		String host = JOptionPane.showInputDialog("Host address:");
		String port = JOptionPane.showInputDialog("Host port:");
		userName = JOptionPane.showInputDialog("User name:");
		
		System.out.println("host: " + host + "\n" + 
							"port: " + port + "\n" +
							"clientName: " + userName + "\n");
//		
		if (host == null || port == null || userName == null){
			//JOptionPane.showMessageDialog(null, "Please fill all fields.");
			return;
		} else
		
		try{
			//Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			String check;
			
			do {
			// write out the name of this client
			out.writeObject(userName);
			// Check to see if the client is accepted
			check = (String)in.readObject();
			if(check.equals("reject"))
				userName = JOptionPane.showInputDialog("User name:");
			} while(check.equals("reject"));
			
			// add a listener that sends a disconnect command to the server when closing
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					try {
						out.writeObject(new DisconnectDispatch(userName));
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			//setupGUI();
		//	c
		//	out.writeObject(new AddObjectDispatch(userName, new CashDrop("Hairclub for Men")));
		//	out.writeObject(new AddObjectDispatch(userName, new ChangeDrop("Chestclub for non-men", 123, 232, 3032)));
	//		out.writeObject(new AddObjectDispatch(userName, new InitialCashBox("Saracens Separation Support", 132, 2032, 3320)));
//			out.writeObject(new AddObjectDispatch(userName, new AddActiveClub("Club of Clubs", 1430, 2430, 343)));
//			out.writeObject(new AddObjectDispatch(userName, new AddActiveClub("Faraway Horizons", 1430, 2430, 343)));
//			out.writeObject(new AddObjectDispatch(userName, new TicketDrop("Faraway Horizons", 132, 220, 3042)));
//			
//			out.writeObject(new AddObjectDispatch(userName, new RemoveActiveClub("Club of Clubs", 1034, 234, 234)));
			
			new Thread(new ServerHandler()).start();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Create the gui in the client once signed in
	private void setupGUI() {
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		// Create tab pages here
		panel_scheduler = new Panel_Scheduler(userName, out, activeClubs, availableFS, dispatchedFS);
		panel_CICO = new Panel_CICO(userName, out, activeClubs, availableFS, dispatchedFS);
		panel_dispatch = new Panel_Dispatch(userName, out, activeClubs, availableFS, dispatchedFS);
		
		// Create the tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Scheduler", panel_scheduler);
		tabbedPane.addTab("Cash In/Cash Out", panel_CICO);
		tabbedPane.addTab("Dispatch", panel_dispatch);
		topPanel.add( tabbedPane, BorderLayout.CENTER);
		
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
	public void update(List<Club> clubs, List<String> availableFS, List<String> dispatchedFS) {
		this.activeClubs = clubs;

		this.availableFS = availableFS;
		this.dispatchedFS = dispatchedFS;
		
		System.out.println("From Server: Update ");
		System.out.println(clubs.toString());
		System.out.println(availableFS.toString());
		System.out.println(dispatchedFS.toString());
		
		System.out.println(userName + " client data: \n");
		this.activeClubs.toString();
		this.availableFS.toString();
		this.dispatchedFS.toString();
		
		
		this.repaint();

		((Panel_Scheduler) panel_scheduler).updateLists(this.activeClubs, this.availableFS, this.dispatchedFS);
//		panel_CICO.repaint();
//		panel_dispatch.repaint();
		
//		private List<Club> activeClubs;
//		private List<String> availableFS;
//		private	List<String> dispatchedFS;
		
		
	}
	
	// Main method in order to run the client
	public static void main(String[] args){
		new DispatchClient();
	}

}
