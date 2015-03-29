package View;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Club;
import model.dispatch.CashDrop;
import model.dispatch.ChangeDrop;
import model.dispatch.DispatchAll;
import model.dispatch.DispatchFieldSupe;
import model.dispatch.FreeFieldSupe;
import model.dispatch.InitialCashDrop;
import model.dispatch.TicketDrop;

public class Panel_Dispatch extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4719929453960671801L;
	private String clientName;
	private ObjectOutputStream output;
	private List<Club> activeClubs;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	
	//Widgets
	private JSpinner spinner_fieldSupes;
	private JSpinner spinner_clubs;
	private JSpinner spinner_action;
	private JSpinner spinner_freeUp;
	private JScrollPane scrollPane_availableFS;
	private JScrollPane scrollPane_dispatchedFS;
	

	//for spinner values
	private String clubSelected;
	private String actionSelected;
	private String DFSSelected;
	private String dispatchedSelected;
	
	//for available selected
	
	/**
	 *  UpdateLists method
	 */
	public void UpdateLists(List<Club> clubs, List<String> availableFS2,
			List<String> dispatchedFS2) {
		this.activeClubs = clubs;
		this.availableFS = availableFS2;
		this.dispatchedFS = dispatchedFS2;
		
		//Remove, update, replace spinner_fieldSupes
		remove(spinner_fieldSupes);
		ArrayList<String> fieldSupesArray = getAvailableFieldSupes();
		SpinnerListModel fieldSupeModel = new SpinnerListModel(fieldSupesArray);
		spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(163, 96, 157, 20);
		spinner_fieldSupes.addChangeListener(new AFSSpinnerListener());
		add(spinner_fieldSupes);
		DFSSelected = spinner_fieldSupes.getValue().toString();
		
		//Remove, update, replace spinner_clubs
		remove(spinner_clubs);
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(163, 127, 157, 20);
		spinner_clubs.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubs);
		clubSelected = spinner_clubs.getValue().toString();
		
		//Remove, update, replace spinner_freeUp
		remove(spinner_freeUp);
		ArrayList<String> dispatchedArray = getDispatchedFieldSupes();
		SpinnerListModel dispatchedModel = new SpinnerListModel(dispatchedArray);
		spinner_freeUp = new JSpinner(dispatchedModel);
		spinner_freeUp.setBounds(163, 446, 147, 22);
		spinner_freeUp.addChangeListener(new DispatchedSpinnerListener());
		add(spinner_freeUp);
		dispatchedSelected = spinner_freeUp.getValue().toString();
		
		//Remove, update, replace scrollPane_availableFS
		remove(scrollPane_availableFS);
		DefaultListModel<String> availFS = new DefaultListModel<String>();
		for (String supe: getAvailableFieldSupes()){
			availFS.addElement(supe);
		}
		JList<String> availableFSList = new JList<String>(availFS);
		scrollPane_availableFS = new JScrollPane(availableFSList);
		scrollPane_availableFS.setBounds(342, 94, 309, 236);
		add(scrollPane_availableFS);
		
		//Remove, update, replace scrollPane_dispatchedFS
		remove(scrollPane_dispatchedFS);
		DefaultListModel<String> dispatchedFS = new DefaultListModel<String>();
		for (String supe: getDispatchedFieldSupes()){
			dispatchedFS.addElement(supe);
		}
		JList<String> dispatchedFSList = new JList<String>(dispatchedFS);
		scrollPane_dispatchedFS = new JScrollPane(dispatchedFSList);
		scrollPane_dispatchedFS.setBounds(342, 388, 309, 311);
		add(scrollPane_dispatchedFS);
		
	}
	
	/**
	 * DispatchedSpinner Listener
	 */
	private class DispatchedSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			dispatchedSelected = spinner_freeUp.getValue().toString();
		}
		
	}
	
	
	/**
	 * AvailableSpinner Listener
	 */
	private class AFSSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			DFSSelected = spinner_fieldSupes.getValue().toString();
			
		}
		
	}
	
	/**
	 * ClubSelected Spinner Listener
	 */
	private class ClubSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			clubSelected = spinner_clubs.getValue().toString();
		}
		
	}
	
	/**
	 * ActionSpinner Listener
	 */
	private class ActionSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			actionSelected = spinner_action.getValue().toString();
			
		}
		
	}
	
	/**
	 *	DispatchButton Listener
	 *
	 */
	private class DispatchListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(getParent(), "Dispatch Button!\n"+
														"Field Supe: " + DFSSelected + "\n" +
														"Club: " + clubSelected + "\n" +
														"ActionSelected: " + actionSelected);
				
				// Process actionSelected on Club
				if (DFSSelected.compareTo("(No Field Supervisors available)")!=0){
					if (actionSelected.compareTo("Dispatch")==0){
						int addCashDrops=0;
						int addChangeDrops=0;
						int addFullSheets=0;
						int addHalfSheets=0;
						int addSingleTickets=0;
						int addWristbands=0;
						
						JTextField tf_cashDrops = new JTextField();
							tf_cashDrops.setText("0");
						JTextField tf_changeDrops = new JTextField();
							tf_changeDrops.setText("0");
						JTextField tf_addFullSheets = new JTextField();
							tf_addFullSheets.setText("0");
						JTextField tf_addHalfSheets = new JTextField();
							tf_addHalfSheets.setText("0");
						JTextField tf_addSingletickets = new JTextField();
							tf_addSingletickets.setText("0");
						JTextField tf_addWristbands = new JTextField();
							tf_addWristbands.setText("0");
						
						Object[] getDispatch = {
								"Cash Drops:", tf_cashDrops,
								"Change Drops:", tf_changeDrops,
								"Full Sheets:", tf_addFullSheets,
								"Half Sheets:", tf_addHalfSheets,
								"Single Tickets:", tf_addSingletickets,
								"Wristbands:", tf_addWristbands
								};
						
						int option = JOptionPane.showConfirmDialog(getParent(), getDispatch, "Enter all dispatch values", JOptionPane.OK_CANCEL_OPTION);
						
						if (option==JOptionPane.OK_OPTION){
							try{
							addCashDrops = Integer.parseInt(tf_cashDrops.getText());
							addChangeDrops = Integer.parseInt(tf_changeDrops.getText());
							addFullSheets = Integer.parseInt(tf_addFullSheets.getText());
							addHalfSheets = Integer.parseInt(tf_addHalfSheets.getText());
							addSingleTickets = Integer.parseInt(tf_addSingletickets.getText());
							addWristbands = Integer.parseInt(tf_addWristbands.getText());
							if ( addCashDrops>=0 && 
									addChangeDrops>=0 &&
									addFullSheets>=0 &&
									addHalfSheets>=0 &&
									addSingleTickets>=0 &&
									addWristbands>=0){
								JOptionPane.showMessageDialog(getParent(), "Dispatching "+DFSSelected + "\n" +
																			"to " + clubSelected + "!\n" + 
									"Cash Drops: " + addCashDrops + "\n" +
									"Change Drops: " + addChangeDrops + "\n" +
									"Full Sheets: " + addFullSheets + "\n" +
									"Half Sheets: " + addHalfSheets + "\n" +
									"Single Tickets: " + addSingleTickets + "\n" +
									"Wristbands: " + addWristbands);
								
								//Execute
								output.writeObject(new DispatchAll(clientName, clubSelected, addCashDrops, addChangeDrops, addFullSheets, addHalfSheets, addSingleTickets, addWristbands));
								//Dispatch field supe
								output.writeObject(new DispatchFieldSupe(clientName, DFSSelected));
								
							}else{
								JOptionPane.showMessageDialog(getParent(), "Negative values not permitted.)");
							}
							
							} catch (NumberFormatException e){
								JOptionPane.showMessageDialog(getParent(), "Enter valid number values please!\n(Number Format Exception)");
								e.printStackTrace();
							} catch (IOException e) {
								JOptionPane.showMessageDialog(getParent(), "IO Exception Line 248");
								e.printStackTrace();
							}
						}
					}
				}
			
			
		}
	}
	
	/**
	 *  AvailableListener
	 */
	private class AvailableListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(getParent(), "Make FS Available Button!\n" +
														"dispatchedSelected: " + dispatchedSelected);
			try {
				output.writeObject(new FreeFieldSupe(clientName, dispatchedSelected));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Panel_Dispatch constructor
	 * @param dispatchedFS2 
	 * @param availableFS2 
	 * @param activeClubs2 
	 * 
	 * @param name		the client's name
	 * @param output	the output stream to the server
	 */
	public Panel_Dispatch(String clientName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		this.clientName = clientName;
		this.output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		
		
		setLayout(null);
		
		JTextArea textArea_fieldSupe = new JTextArea();
		textArea_fieldSupe.setBackground(SystemColor.control);
		textArea_fieldSupe.setBounds(35, 94, 100, 28);
		textArea_fieldSupe.setText("Field supe:");
		add(textArea_fieldSupe);
		
		JTextArea textArea_club = new JTextArea();
		textArea_club.setBackground(SystemColor.control);
		textArea_club.setBounds(35, 133, 100, 28);
		textArea_club.setText("      Club:");
		add(textArea_club);
		
		JTextArea textArea_action = new JTextArea();
		textArea_action.setBackground(SystemColor.control);
		textArea_action.setBounds(35, 172, 100, 28);
		textArea_action.setText("    Action:");
		add(textArea_action);
		
		ArrayList<String> fieldSupesArray = getAvailableFieldSupes();
		SpinnerListModel fieldSupeModel = new SpinnerListModel(fieldSupesArray);
		spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(163, 96, 157, 20);
		spinner_fieldSupes.addChangeListener(new AFSSpinnerListener());
		add(spinner_fieldSupes);
		DFSSelected = spinner_fieldSupes.getValue().toString();
		
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(163, 127, 157, 20);
		spinner_clubs.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubs);
		clubSelected = spinner_clubs.getValue().toString();
		
		String[] actionArray = {"InitialCashBox","Dispatch"};
		SpinnerListModel actionModel = new SpinnerListModel(actionArray);		
		spinner_action = new JSpinner(actionModel);
		spinner_action.setBounds(163, 162, 157, 20);
		spinner_action.addChangeListener(new ActionSpinnerListener());
		add(spinner_action);
		spinner_action.setValue("Dispatch");
		actionSelected = spinner_action.getValue().toString();
		
		/*
		 * 	DISPATCH BUTTON AND LISTENER
		 */		
		JButton btnDispatch = new JButton("DISPATCH");
		btnDispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDispatch.setBounds(35, 260, 285, 70);
		add(btnDispatch);
		
		DispatchListener dispatchListener = new DispatchListener();
		btnDispatch.addActionListener(dispatchListener);
		
		
		
		//Available Field Supes list
		DefaultListModel<String> availFS = new DefaultListModel<String>();
		for (String supe: getAvailableFieldSupes()){
			availFS.addElement(supe);
		}
		JList<String> availableFSList = new JList<String>(availFS);
		scrollPane_availableFS = new JScrollPane(availableFSList);
		scrollPane_availableFS.setBounds(342, 94, 309, 236);
		add(scrollPane_availableFS);
		
		//Dispatched Field Supes list
		DefaultListModel<String> dispatchedFS = new DefaultListModel<String>();
		for (String supe: getDispatchedFieldSupes()){
			dispatchedFS.addElement(supe);
		}
		JList<String> dispatchedFSList = new JList<String>(dispatchedFS);
		scrollPane_dispatchedFS = new JScrollPane(dispatchedFSList);
		scrollPane_dispatchedFS.setBounds(342, 388, 309, 311);
		add(scrollPane_dispatchedFS);
		
		/*
		 * 	MAKE AVAILABLE BUTTON AND LISTENER
		 */
		JButton btnMakeAvailable = new JButton("Make Available");
		btnMakeAvailable.setBounds(35, 524, 277, 44);
		add(btnMakeAvailable);
		
		AvailableListener availableListener = new AvailableListener();
		btnMakeAvailable.addActionListener(availableListener);
		
		JTextArea txtrAvailableFS = new JTextArea();
		txtrAvailableFS.setBackground(SystemColor.control);
		txtrAvailableFS.setText("Available Field Supervisors");
		txtrAvailableFS.setBounds(384, 61, 228, 22);
		add(txtrAvailableFS);
		
		JTextArea txtrDispatchedFS = new JTextArea();
		txtrDispatchedFS.setBackground(SystemColor.control);
		txtrDispatchedFS.setText("Dispatched Field Supervisors");
		txtrDispatchedFS.setBounds(384, 355, 228, 22);
		add(txtrDispatchedFS);
		
		JTextArea txtrFieldSupe = new JTextArea();
		txtrFieldSupe.setBackground(SystemColor.control);
		txtrFieldSupe.setText("Field supe:");
		txtrFieldSupe.setBounds(35, 444, 100, 22);
		add(txtrFieldSupe);
		
		ArrayList<String> dispatchedArray = getDispatchedFieldSupes();
		SpinnerListModel dispatchedModel = new SpinnerListModel(dispatchedArray);
		spinner_freeUp = new JSpinner(dispatchedModel);
		spinner_freeUp.setBounds(163, 446, 147, 22);
		spinner_freeUp.addChangeListener(new DispatchedSpinnerListener());
		add(spinner_freeUp);
		dispatchedSelected = spinner_freeUp.getValue().toString();
		
	}

	private ArrayList<String> getAvailableFieldSupes() {
		ArrayList<String> availableFieldSupes = new ArrayList<String>();
		
		if (availableFS!=null){
			for (String supe : availableFS){
				availableFieldSupes.add(supe);
			}
		}
		
		if (availableFieldSupes.size()==0){
			availableFieldSupes.add("(No Field Supervisors available)");
		} else{
			availableFieldSupes.remove("(No Field Supervisors available)");
		}
		
		Collections.sort(availableFieldSupes);
		
		return availableFieldSupes;
	}

	private ArrayList<String> getDispatchedFieldSupes() {
		ArrayList<String> dispatched = new ArrayList<>();
		
		
		if (dispatchedFS!=null){
			for (String supe: dispatchedFS){
				dispatched.add(supe);
			}
		}
		
		if (dispatched.size()==0){
			dispatched.add("(No Field Supervisors dispatched...)");
		} else{
			dispatched.remove("(No Field Supervisors dispatched...)");
		}
		
		Collections.sort(dispatched);
		
		return dispatched;
	}

	private ArrayList<String> getClubs() {
		ArrayList<String> clubs = new ArrayList<>();
		
		if (activeClubs!=null){
			for (Club club : activeClubs){
				clubs.add(club.getClubName());
			}
		}
		
		if (clubs.size()==0){
			clubs.add("(No clubs!)");
			
		} else{
			clubs.remove("(No clubs!)");
		}
		
		return clubs;
	}


}
