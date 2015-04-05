package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import model.Club;
import model.dispatch.AddActiveClub;
import model.dispatch.DisconnectDispatch;
import model.dispatch.Dispatch;
import View.Panel_ADMIN;
import View.Panel_CICO;
import View.Panel_Dispatch;
import View.Panel_Scheduler;

public class CompleteClient extends JFrame{
	
	private JTabbedPane tabbedPane;
	private JPanel panel_scheduler;
	private JPanel panel_CICO;
	private JPanel panel_dispatch;
	private JPanel panel_ADMIN;
	private List<Club> activeClubs;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	private JScrollPane scrollPane_scheduler;
	private JScrollPane scrollPane_CICO;
	private JScrollPane scrollPane_dispatch;
	private JScrollPane scrollPane_ADMIN;
	
	private String userName; // user name of the client
	
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	private boolean exitThread;
	
	/************* Server Handler Private Class *****************/
	// Reads commands from the server and executes them 
	
	private class ServerHandler implements Runnable {
		
		public void run() {
			//Implement the ServerHandler
			while(true) {
				try {
					Object obj = in.readObject();
					if(obj instanceof Dispatch<?>) { // See if we have a valid command
						Dispatch<CompleteClient> command = (Dispatch<CompleteClient>)obj;
						System.out.println("Update from server: " + command.getSource());
						command.execute(CompleteClient.this);
					}	
				} catch (Exception e) {
				//	e.printStackTrace();
				} //this will block if no object written
			}
		}
	}
	
	/*************************************************************/

	/**
	 * 
	 */
	private static final long serialVersionUID = -4326297992047795976L;
	
	public CompleteClient(){
		
		// Open connection to server
//		// ask the user for a host, port, and user name
		
//		String host = JOptionPane.showInputDialog("Host address:");
//		String port = JOptionPane.showInputDialog("Host port:");
		
		String host = "LOCALHOST";
		String port = "9001";
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
			setupGUI();
//			
//			out.writeObject(new AddFieldSupe(userName, "Russell Leeves"));
//			out.writeObject(new AddFieldSupe(userName, "Polly Ester"));
//			out.writeObject(new AddFieldSupe(userName, "Emma Royds"));
//			out.writeObject(new AddFieldSupe(userName, "Mike Easter"));
//			out.writeObject(new AddFieldSupe(userName, "Jasmine Rice"));
//			out.writeObject(new AddActiveClub(userName,"United Loners",0,0));
//			out.writeObject(new AddActiveClub(userName,"Sad Clown Awareness",0,0));
//			out.writeObject(new AddActiveClub(userName,"Tequila Mockingbird",0,0));
//			out.writeObject(new AddActiveClub(userName,"Menace to Sobriety",0,0));	
//			out.writeObject(new AddActiveClub(userName,"Kids Who Can't Read Good",0,0));
//			out.writeObject(new AddActiveClub(userName,"Seal Walked Into A",0,0));
			
			out.writeObject(new AddActiveClub(userName,"Club0"));
			out.writeObject(new AddActiveClub(userName,"Club1"));
			out.writeObject(new AddActiveClub(userName,"Club2"));
			out.writeObject(new AddActiveClub(userName,"Club3"));	
			out.writeObject(new AddActiveClub(userName,"Club4"));
			out.writeObject(new AddActiveClub(userName,"Club5"));
//			
			new Thread(new ServerHandler()).start();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Create the gui in the client once signed in
	private void setupGUI() {
		
		setTitle("SpringFlingSoft 2015 - User: " + userName);
		setSize(800,800);
		setBackground(Color.gray);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout() );

		
		
		// Create tab pages here
		panel_scheduler = new Panel_Scheduler(userName, out, activeClubs, availableFS, dispatchedFS);
		panel_scheduler.setPreferredSize(new Dimension(800, 800));
		panel_CICO = new Panel_CICO(userName, out, activeClubs, availableFS, dispatchedFS);
		panel_CICO.setPreferredSize(new Dimension(800, 900));
		panel_dispatch = new Panel_Dispatch(userName, out, activeClubs, availableFS, dispatchedFS);
		panel_dispatch.setPreferredSize(new Dimension(800, 800));
		
		
		// Create Scroll panels for each tab page (or else we lose tabs!) Hopefully updates work normally....
		scrollPane_scheduler = new JScrollPane(panel_scheduler);
			scrollPane_scheduler.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane_CICO = new JScrollPane(panel_CICO);
			scrollPane_CICO.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane_dispatch = new JScrollPane(panel_dispatch);
			scrollPane_dispatch.getVerticalScrollBar().setUnitIncrement(16);
			
		// Create the tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Scheduler", scrollPane_scheduler);
		tabbedPane.addTab("Cash In/Cash Out", scrollPane_CICO);
		tabbedPane.addTab("Dispatch", scrollPane_dispatch);
		
				// If userName is "ADMIN", add admin panel
		if (userName.equals("ADMIN")){
			panel_ADMIN = new Panel_ADMIN(userName, out, activeClubs, availableFS, dispatchedFS);
			panel_ADMIN.setPreferredSize(new Dimension(800,800));
			scrollPane_ADMIN = new JScrollPane(panel_ADMIN);
			tabbedPane.addTab("ADMIN", scrollPane_ADMIN);
		}
		
		topPanel.add( tabbedPane, BorderLayout.CENTER);
		
		

		
		// Finally add topPanel
		getContentPane().add(topPanel);
		
		
		this.setVisible(true);		
	}	
		
//	/**
//	 * Updates the chat log. Called by UpdateClientCommands
//	 * 
//	 * @param messages	the current chat log
//	 */
//	public void update(List<PaintObject> objects) {
//		window_dispatch.update(objects);
//	}
	public void update(List<Club> clubs, List<String> availableFS, List<String> dispatchedFS) {
		this.activeClubs = clubs;
		this.availableFS = availableFS;
		this.dispatchedFS = dispatchedFS;
		
		System.out.println(clubs);
		System.out.println(availableFS);
		System.out.println(dispatchedFS);
		
		for(Club c: clubs) {
			System.out.println(c.getClubName() + "\n" +
					"InitialCashDrop: "+ c.getInitialCashDrop()+
					"CashDrops: " + c.getCashdrops()+
					"ChangeDrops: " + c.getChangedrops());
		}
		System.out.println("========================");
		
		
		((Panel_Scheduler) panel_scheduler).UpdateLists(clubs, availableFS, dispatchedFS);
		((Panel_Dispatch) panel_dispatch).UpdateLists(clubs, availableFS, dispatchedFS);
		((Panel_CICO) panel_CICO).UpdateLists(clubs, availableFS, dispatchedFS);
		if (userName.equals("ADMIN")){
			((Panel_ADMIN) panel_ADMIN).UpdateLists(clubs);
		}
		
		
		this.repaint();
		
//		private List<Club> activeClubs;
//		private List<String> availableFS;
//		private	List<String> dispatchedFS;
		
		
	}
	// Main method in order to run the client
	public static void main(String[] args){
		new CompleteClient();
	}	
	
	

}
