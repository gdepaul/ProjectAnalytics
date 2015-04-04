package server;

/**
 * 
 * @authors Kristoffer Cabulong & Nick Callahan
 * 
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import View.*;
import model.*;
import model.dispatch.*;
import controller.CompleteClient;
import exceptions.*;



/**
 * DispatchServer
 * 
 * <p> This class is the server side of Spring Fling Analytics. It is responsible for
 * managing connections to clients and sending and executing dispatches.
 * 
 * The Rosetta stone for this class was NetPaint Server, authored by Gabriel Kishi
 *
 */

public class DispatchServer extends JFrame {
	private static final long serialVersionUID = -4034798597118847005L;
	
	private static Logger Out;
	private ServerSocket socket;
	private HashMap<String, FieldSupervisor> field_sups;
	private List<String> list_clubs;
	private HashMap<String, Club> activeClubs;
	private HashMap<String, Club> inactiveClubs;
	private HashMap<String, Booth> booths;
	private Map<String, Deque<Dispatch<DispatchServer>>> histories;
	private Map<String, ObjectInputStream> inputs;
	private Map<String, ObjectOutputStream> outputs;
	
	private List<Date> dispatchCommandTimes;
		
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
					//TimeUnit.SECONDS.sleep(3);
					TimeUnit.MINUTES.sleep(15);
					//System.out.println("Save");
					Out.print("Saving clubs to disk!");			
					SER.backup(new SaveFile(activeClubs,activeClubs,field_sups,histories,booths));
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
						System.err.println(ob.getClass());
						@SuppressWarnings("unchecked")
						Dispatch<DispatchServer> dispatch = (Dispatch<DispatchServer>)ob; // cast the object // grab a command off the queue
						try {
							dispatch.execute(DispatchServer.this); // execute the command on the server
							if(dispatch instanceof InitialCashDrop || dispatch instanceof CashDrop || dispatch instanceof ChangeDrop || dispatch instanceof TicketDrop || dispatch instanceof DispatchAll) {
								activeClubs.get(dispatch.getClub()).addTransaction(dispatch);
							}
						} catch(DuplicateClubException dce) {
							Out.error("Client " + dispatch.getSource() + " tried to add a duplicate club");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(dce);
						} catch(DuplicateFieldSupeException dfse) {
							Out.error("Client " + dispatch.getSource() + " tried to add a duplicate Field Supervior");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(dfse);
						} catch(DeployedException de) {
							Out.error("Client " + dispatch.getSource() + " tried to remove a Field Supe currently dispatched.");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(de);
						} catch(NullFieldSupeException nfse) {
							Out.error("Client " + dispatch.getSource() + " tried to dispatch a Field Supe that does not exist.");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(nfse);
						} catch(NotDispatchedException nde) {
							Out.error("Client " + dispatch.getSource() + " tried to free Field Supe that is not dispatched");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(nde);
						} catch(NullClubException nce) {
							Out.error("Client " + dispatch.getSource() + " tried to operate on Club " + nce.getClubName() + " which does not exist");
							ObjectOutputStream clientOut = outputs.get(dispatch.getSource());
							clientOut.writeObject(nce);
						} finally {
							dispatchCommandTimes.add(new Date());
							updateClients();
						}
					if (!(dispatch instanceof UndoLastDispatch)) // undo commands can't be undone
							history.push(dispatch);
					}
				}
				catch(Exception e){
					if(!(e instanceof java.net.SocketException)) {
						e.printStackTrace();
						System.err.println("In Client Handler:");
					}
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
			dispatchCommandTimes = new ArrayList<Date>();
			field_sups = new HashMap<String, FieldSupervisor>();
			activeClubs = new HashMap<String, Club>();
			inactiveClubs = new HashMap<String, Club>();
			booths = new HashMap<String,Booth>();
			setUpBooths();
			Out.print("Server started on port " + port);
			//System.out.println("Server started on port " + port);

			// begin accepting clients
			new Thread(new ClientAccepter()).start();
			new Thread(new AutoSaver()).start();
		} catch(BindException be) {
			System.err.println("A server is already using that port.  Try another port");
		} catch(Exception e){
			Out.error("Error creating server:");
			//System.err.println("Error creating server:");
			e.printStackTrace();
		}
	}
	
	public DispatchServer(int port, SaveFile save) {
		Out = new Logger("server.log","server.err");
		try{
			socket = new ServerSocket(port); // create a new server
		//--Load saved objects
			activeClubs = save.getClubs();
			inactiveClubs = save.getInactiveClubs();
			list_clubs = new ArrayList<String>(activeClubs.keySet());
			field_sups = save.getFieldSupervisors();
			histories = save.getHistory();
			booths = save.getBooths();

		//--Set up ins/outs
			inputs = new ConcurrentHashMap<String, ObjectInputStream>();
			outputs = new ConcurrentHashMap<String, ObjectOutputStream>();
			
			
			Out.print("Server started on port " + port);
			
			printServerState();
			//System.out.println("Server started on port " + port);
			// begin accepting clients
			new Thread(new ClientAccepter()).start();
			new Thread(new AutoSaver()).start();
		} catch(BindException be) {
			System.err.println("A server is already using that port.  Try another port");
		} catch(Exception e){
			Out.error("Error creating server:");
			//System.err.println("Error creating server:");
			e.printStackTrace();
		}	
	}

	public static void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Inside Add Shutdown Hook");
				try {
					Files.deleteIfExists(Paths.get("server.lock"));
					Out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void setUpBooths() {
		this.booths.put("Castle 1", new Booth("Castle 1"));
		this.booths.put("Castle 2", new Booth("Castle 2"));
		this.booths.put("Castle 3", new Booth("Castle 3"));
		this.booths.put("Castle 4", new Booth("Castle 4"));
		
		this.booths.put("Cottage 1", new Booth("Cottage 1"));
		this.booths.put("Cottage 2", new Booth("Cottage 2"));
		this.booths.put("Cottage 3", new Booth("Cottage 3"));
		this.booths.put("Cottage 4", new Booth("Cottage 4"));
		
		this.booths.put("Wonderland", new Booth("Wonderland"));
		this.booths.put("Wishing Well", new Booth("Wishing Well"));
		this.booths.put("Pride Rock", new Booth("Pride Rock"));
		this.booths.put("Palace", new Booth("Palace"));
		this.booths.put("Enchanted Forest", new Booth("Enchanted Forest"));
		this.booths.put("Swamp", new Booth("Swamp"));
		this.booths.put("Woods", new Booth("Woods"));
		this.booths.put("Beanstalk", new Booth("Beanstalk"));
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//SERVER COMMANDS
////////////////////////////////////////////////////////////////////////////////////////////////////
	private void printServerState() {
		System.out.println("Field Supervisors:\n\t" + this.field_sups);
		System.out.println("Active Clubs:\n\t" + this.activeClubs);
		System.out.println("Inactive Clubs:\n\t" + this.inactiveClubs);
		System.out.println("Booths:\n\t" + this.booths);
		System.out.println("Clients:\n\t" + this.inputs.keySet());
	}
	public void saveState() {
		System.out.println("saveState() Executed");
		Serializer SER = new Serializer("backups");
		SER.backup(new SaveFile(activeClubs,activeClubs,field_sups,histories,booths));
	}
	public void addClub(Club newClub) throws DuplicateClubException {
		if(activeClubs.containsKey(newClub.getClubName())) {
			throw new DuplicateClubException(newClub.getClubName() + " already exists! Cannot add " + newClub.getClubName());
		} 
		list_clubs.add(newClub.getClubName());
		activeClubs.put(newClub.getClubName(), newClub);	
	}
	public void removeClub(String name) {
		if(activeClubs.containsKey(name)) {
			inactiveClubs.put(name, activeClubs.get(name));
			list_clubs.remove(name); 
			activeClubs.remove(name);
		}
	}
	/**
	 * @param fs Name of Field Supervisor to Add
	 * @throws DuplicateFieldSupeException
	 */
	public void addFieldSupe(String fs) throws DuplicateFieldSupeException {
		if(this.field_sups.containsKey(fs)) {
			throw new DuplicateFieldSupeException(fs + " is already a Field Supervisor!");
		}
		this.field_sups.put(fs, new FieldSupervisor(fs));
	}
	/**
	 * @param fs
	 * @throws DeployedException
	 */
	public void removeFieldSupe(String fs) throws DeployedException, NullFieldSupeException {
		if(this.field_sups.get(fs).isDispatched())
			throw new DeployedException(fs + " is currently dispatched and cannot be removed.");
		if(!this.field_sups.containsKey(fs))
			throw new NullFieldSupeException(fs + " does not exist in the system as Field Supervisor.");
		this.field_sups.remove(fs);
	}

	public void dispatchFieldSupe(String fs) throws NullFieldSupeException, DeployedException {
		if(!this.field_sups.containsKey(fs))
			throw new NullFieldSupeException(fs + " does not exist in the system as Field Supervisor.");
		if(this.field_sups.get(fs).isDispatched())
			throw new DeployedException(fs + " is already dispatched.");
		this.field_sups.get(fs).dispatch();
	}

	public void freeFieldSupe(String fs) throws NotDispatchedException, NullFieldSupeException {
		if(!this.field_sups.containsKey(fs))
			throw new NullFieldSupeException(fs + " does not exist in the system as a Field Supervisor.");
		if(!this.field_sups.get(fs).isDispatched())
			throw new NotDispatchedException(fs + " is not currently dispatched.");
		this.field_sups.get(fs).free();
	}
	
	public void cashDrop(String club, int amount) throws NullClubException {
		if(activeClubs.containsKey(club)) {
			activeClubs.get(club).putCashDrop(amount);
		}
		else
			throw new NullClubException(club + "does not exist");
	}
	public void initialCashDrop(String club, float drop, int initialTickets, int initialWristbands, String location) throws NullClubException{
		if(activeClubs.containsKey(club)) {
			activeClubs.get(club).setInitialCashDrop(drop);
			activeClubs.get(club).setInitialTickets(initialTickets);
			activeClubs.get(club).setInitialWristbands(initialWristbands);
			activeClubs.get(club).setLocation(location);
			Out.print("Initial cash drop for " + club + ":" + activeClubs.get(club).getInitialCashDrop());
			
		}
		else
			throw new NullClubException(club + "does not exist");
	}
	public void changeDrop(String club, int amount) throws NullClubException {
		if(activeClubs.containsKey(club)) {
			activeClubs.get(club).putChangeDrop(amount);
			Out.print("Change Drop for " + club);
		}
		else
			throw new NullClubException(club + "does not exist");
	}

	public void ticketDrop(String club, int num_full, int num_half, int num_singles, int num_wristbands) throws NullClubException {
		if(activeClubs.containsKey(club)) {
			activeClubs.get(club).putFullSheet(num_full);
			activeClubs.get(club).putHalfSheet(num_half);
			activeClubs.get(club).putSingleTickets(num_singles);
			activeClubs.get(club).putWristbands(num_wristbands);
		}
		else
			throw new NullClubException(club + "does not exist");
	}
	public void addTransaction(Dispatch<DispatchServer> transaction) {
		this.activeClubs.get(transaction.getClub()).addTransaction(transaction);
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
	 *	clubs, available field supervisors, and dispatched field supervisors
	 */
	public void updateClients(){
		//System.err.println("Updating Clients");
		List<Club> clubs = new ArrayList<Club>(activeClubs.values());
		List<String> availableFieldSupervisors = new ArrayList<String>();
		List<String> dispatchedFieldSupervisors = new ArrayList<String>();

		for(FieldSupervisor fs : this.field_sups.values()) {
			if(!fs.isDispatched())
				availableFieldSupervisors.add(fs.getName());
			else
				dispatchedFieldSupervisors.add(fs.getName());
		}
		Dispatch<CompleteClient> update = new UpdateDispatch("server", new ArrayList<Club>(clubs), new ArrayList<String>(availableFieldSupervisors), new ArrayList<String>(dispatchedFieldSupervisors));
		for (ObjectOutputStream out: outputs.values()) {
			try{
				out.reset();
				out.writeObject(update);
			}catch(Exception e){
				Out.error("Error updating clients");
			//	System.err.println("Error updating clients");
			//	e.printStackTrace();
				outputs.remove(out);
			}
		}
		//((ServerPanel_Histories)this.panel_history).updatePanel(this.hash_clubs,this.histories);
	}
	/**
	 * 
	 * @param club
	 * @param cashDrops
	 * @param changeDrops
	 * @param fullSheets
	 * @param halfSheets
	 * @param wristbands 
	 * @throws NullClubException 
	 */
	public void dispatchAll(String club, int cashDrops, int changeDrops,
			int fullSheets, int halfSheets, int singleTickets, int wristbands) throws NullClubException {
		if(cashDrops != 0) 
			this.cashDrop(club, cashDrops);
		if(changeDrops != 0)
			this.changeDrop(club, changeDrops);
		this.ticketDrop(club, fullSheets, halfSheets, singleTickets, wristbands);
	}


	/**
	 *  Disconnects a connected user
	 * @param source	user to disconnect
	 */
	public void disconnect(String source) {
		Out.print("Client " + source + " disconnected");
		try{
			inputs.remove(source).close();
			outputs.remove(source).close();
			histories.remove(source);
		}
		catch(Exception e){ e.printStackTrace();}
	}
	public static void main(String[] args) {
		int port = 9001;
		attachShutDownHook();
	//--Check if Server is already running
		try {
			ServerSocket ignored = new ServerSocket(port);
			ignored.close();
		} catch(Exception ioe) {
			System.err.println("Socket is in use. Try another port");
			System.exit(1);
		}
	//--If not, check to make sure lock file is gone
		File file = new File("server.lock");
		if(file.exists()) { 
			System.err.println("We have detected a server crash. Loading from save file");
			Serializer SER = new Serializer("backups");
			SaveFile save = SER.loadMostRecent();
			if(save != null)
				new DispatchServer(port,save);
			new DispatchServer(port);
		}
		else {
			try {
				FileOutputStream FOUT = new FileOutputStream("server.lock");
				FOUT.write("Server started".getBytes());
				FOUT.close();
			} catch (IOException e) {
				System.err.println("Could not create server lock file.");
				e.printStackTrace();
			}
			new DispatchServer(port);
		}
	}

}