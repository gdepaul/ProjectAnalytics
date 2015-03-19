package server;

/*
 * 
 * Kristoffer Cabulong
 * 
 */


import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import model.Club;
import model.dispatch.Dispatch;
import model.dispatch.UndoLastDispatch;
import model.dispatch.UpdateDispatch;
import model.dispatchObject.DispatchObject;
import controller.DispatchClient;


/**
 * Netpaint Server
 * 
 * <p> This class is the server side of Netpaint. It is responsible for
 * managing connections to clients, sending and executing commands, and
 * holds the list of all PaintObjects on the shared canvas. <P>
 * 
 * @author Gabriel Kishi
 *
 */

public class DispatchServer {
	private Logger Out;
	private ServerSocket socket;
	private List<String> list_clubs;
	private HashMap<String, Club> hash_clubs;
	
	private Map<String, Deque<Dispatch<DispatchServer>>> histories;
	private Map<String, ObjectInputStream> inputs;
	private Map<String, ObjectOutputStream> outputs;
	
	/**
	 * AutoSaver
	 * 
	 * This class handles auto saving all active clubs.  This is achieved by sleeping for 15 Minutes then saving each club.
	 * @author N R Callahan
	 * 
	 */
	private class AutoSaver implements Runnable {

		private Serializer SER;
		
		@Override
		public void run() {
			//--Create Backups directory
			File backups_dir = new File("backups");
			if(!backups_dir.exists()) {
				backups_dir.mkdirs();
			}
			SER = new Serializer("backups");
			while(true) {
				try {
					TimeUnit.SECONDS.sleep(3);
					//TimeUnit.MINUTES.sleep(15);
					System.out.println("Save");
					Out.print("Saving clubs to disk!");			
					List<Club> clubs = new ArrayList<Club>(hash_clubs.values());
					for(Club club : clubs) {
						SER.saveClub(club);
					}
				} catch(InterruptedException ie) { Out.error(ie.getMessage());} 
			}
		}
	}
	
	/**
	 *	ClientHandler
	 * 
	 *	This class handles executing command from a single client. It 
	 *	manages the queue of pending commands and maintains a history 
	 *	of	executed commands that can be undone.
	 */
	private class ClientHandler implements Runnable{
		
		private String clientId; // name of the client
		private Deque<Dispatch<DispatchServer>> history; // history of executed commands
		private ObjectInputStream input; // input stream to read command from
		
		public ClientHandler(String id, Deque<Dispatch<DispatchServer>> history)
		{
			clientId = id;
			this.history=history;
			input = inputs.get(id);
			
			Out.print("New Client " + id + " connected");
			
			updateClients();
		}

		public void run() {
			while(true){
				try{
					Object ob = input.readObject();
					if (ob instanceof Dispatch<?>){
						@SuppressWarnings("unchecked")
						Dispatch<DispatchServer> dispatch = (Dispatch<DispatchServer>)ob; // cast the object // grab a command off the queue
						dispatch.execute(DispatchServer.this); // execute the command on the server
					
					if (!(dispatch instanceof UndoLastDispatch)) // undo commands can't be undone
							history.push(dispatch);
					}
				}
				catch(Exception e){
					//System.err.println("In Client Handler:");
					//e.printStackTrace();
					break;
				}
			}
		}
	}
	
	/**
	 * 	ClientAccepter
	 * 
	 * 	This class is responsible for listening for new clients and subsequently setting up
	 * 	a ClientHandler for the new client.
	 */
	private class ClientAccepter implements Runnable{
		public void run() {
			while (true){
				try{
					Socket s = socket.accept(); // wait for a new client
					
					// grab the output and input streams for the new client
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					

					String name;
					do{
						// read the client's name
						name = (String)input.readObject();
						
						// if that name is already connected, reject
						if (outputs.containsKey(name))
							output.writeObject("reject");
					}while (outputs.containsKey(name));
					
					// tell the client their name is accepted
					output.writeObject("accept");
					
					// add the output, input streams to the correct maps
					outputs.put(name, output);
					inputs.put(name, input);
					
					// create a command history queue for the new client
					histories.put(name, new LinkedBlockingDeque<Dispatch<DispatchServer>>());
					
					// start a new ClientHandler for this new client
					new Thread(new ClientHandler(name, histories.get(name))).start();
				}
				catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	public DispatchServer(int port){
		Out = new Logger("server.log","server.err");
		try{
			socket = new ServerSocket(port); // create a new server
			
			// setup hashmaps
			histories = new ConcurrentHashMap<String, Deque<Dispatch<DispatchServer>>>();
			inputs = new ConcurrentHashMap<String, ObjectInputStream>();
			outputs = new ConcurrentHashMap<String, ObjectOutputStream>();
			
			// setup lists for clubs, field supervisors
			list_clubs = new ArrayList<String>();
			
			hash_clubs = new HashMap<String, Club>();
			
			Out.print("Server started on port " + port);
			//System.out.println("Server started on port " + port);
			
			// begin accepting clients
			new Thread(new ClientAccepter()).start();
			new Thread(new AutoSaver()).start();
		}catch(Exception e){
			Out.error("Error creating server:");
			//System.err.println("Error creating server:");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method undoes the last command of a client
	 * 
	 * @param clientName name of the client whose command should be undone
	 */
	public void undoLast(String clientName) {
		Deque<Dispatch<DispatchServer>> commands = histories.get(clientName);
		if (commands.isEmpty())
			return;
		commands.pop().undo(DispatchServer.this);
	}

	/**
	 *	This method updates all connected clients with the current list of
	 *	PaintObjects in the world
	 */
	public void updateClients(){
		System.err.println("Updating Clients");
//		Command<NetpaintClient> update = new UpdateCommand("server", objects.toArray(new PaintObject[objects.size()]));
		List<Club> clubs = new ArrayList<Club>(hash_clubs.values());
		Dispatch<DispatchClient> update = new UpdateDispatch("server", clubs);
		for (ObjectOutputStream out: outputs.values())
			try{
				out.writeObject(update);
			}catch(Exception e){
				Out.error("Error updating clients");
				//System.err.println("Error updating clients");
				//e.printStackTrace();
				outputs.remove(out);
			}
	}


	/**
	 *  Disconnects a connected user
	 * @param source	user to disconnect
	 */
	public void disconnect(String source) {
		Out.print("Client " + source + " disconnecting");
		//System.out.printf("Client \'%s\' disconnecting\n", source);
		try{
			inputs.remove(source).close();
			outputs.remove(source).close();
			histories.remove(source);
		}
		catch(Exception e){ e.printStackTrace();}
	}
	
	
	
	public static void main(String[] args)
	{
		new DispatchServer(9001);
	}
	
	/*
	 * So hey, this addObject() handles all the different kinds of objects that can be added.
	 * It isn't elegant, I guess I could've used an enum, but this work, eh? Right now, the 
	 * functionality is barebones. Replace the SYSO comments as needed.
	 */

	public void addObject(DispatchObject<?> object) {
		
		String task = object.getObjectType();
		
		String clubName = object.getClub();
		int cash = object.getCash();
		int change = object.getChange();
		int tickets = object.getTickets();
		Club club = new Club(clubName);
		
		long now = System.currentTimeMillis();

		java.util.Date date = new java.util.Date(now);
		//Clubs need cash drop var
		//	incremend # of cash drops by 1, decriment amount of money by 800
		if (task.compareTo("CashDrop")==0){
			Out.print("" + date.toString()+  ": \nThis task is a CashDrop. \n" +
								clubName + " " + cash + " " + change + " " + tickets);
		}
		//Change drop var
		//	100$
		if (task.compareTo("ChangeDrop")==0){
			Out.print("" + date.toString()+  ": \nThis task is a ChangeDrop\n"+
					clubName + " " + cash + " " + change + " " + tickets);
			hash_clubs.get(clubName).putCashDrop();
			Out.print(clubName + " had a cash drop");
		}
		//
		if (task.compareTo("InitialCashBox")==0){
			Out.print("" + date.toString()+  ": \nThis task is an InitialCashBox\n"+
					clubName + " " + cash + " " + change + " " + tickets);
		}
		//Ticket drop var
		// Keep track of how many half sheets and full sheets we give them
		if (task.compareTo("TicketDrop")==0){
			Out.print("" + date.toString()+  ": \nThis task is a TicketDrop\n"+
					clubName + " " + cash + " " + change + " " + tickets);
			hash_clubs.get(clubName).addTickets(tickets);	// Assumes a ticket drop increases tickets
			
		}
		
		
		if (task.compareTo("AddActiveClub")==0){			
			Out.print("" + date.toString()+  ": \nThis task is an AddActiveClub\n"+
					clubName + " " + cash + " " + change + " " + tickets);
			
			list_clubs.add(clubName);
			hash_clubs.put(clubName, club);		
			
			System.out.println("Current clubs: \n");
			
			for (String current : list_clubs){
				Out.print("Club: " + hash_clubs.get(current).getClubName());
				Out.print("   Money: " + hash_clubs.get(current).getMoney());
				Out.print("   Tickets: " + hash_clubs.get(current).getTickets());
			}
		}
		if (task.compareTo("RemoveActiveClub")==0){			
			Out.print("" + date.toString()+  ": \nThis task is a RemoveActiveClub\n"+
					clubName + " " + cash + " " + change + " " + tickets);
			
					list_clubs.remove(clubName);
					hash_clubs.remove(clubName);
			
					System.out.println("Current clubs: \n");
					
					for (String current : list_clubs){
						Out.print("Club: " + hash_clubs.get(current).getClubName());
						Out.print("   Money: " + hash_clubs.get(current).getMoney());
						Out.print("   Tickets: " + hash_clubs.get(current).getTickets());
					}
		}
		updateClients();
	}
}