package View;

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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

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
	JSpinner spinner_fieldSupes;
	JSpinner spinner_clubs;
	JSpinner spinner_action;
	JSpinner spinner_freeUp;
	

	//for spinner values
	private String clubSelected;
	private String actionSelected;
	private String DFSSelected;
	private String dispatchedSelected;
	
	//for available selected
	
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
	 *	DispatchListener
	 *
	 */
	private class DispatchListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(getParent(), "Dispatch Button!\n"+
														"Field Supe: " + DFSSelected + "\n" +
														"Club: " + clubSelected + "\n" +
														"ActionSelected: " + actionSelected);
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
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBackground(SystemColor.control);
		textArea_timeIn.setBounds(35, 211, 100, 28);
		textArea_timeIn.setText("   Time in:");
		add(textArea_timeIn);
		
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
		
		String[] actionArray = {"CashDrop", "ChangeDrop", "InitialCashBox", "TicketDrop"};
		SpinnerListModel actionModel = new SpinnerListModel(actionArray);		
		spinner_action = new JSpinner(actionModel);
		spinner_action.setBounds(163, 162, 157, 20);
		spinner_action.addChangeListener(new ActionSpinnerListener());
		add(spinner_action);
		actionSelected = spinner_action.getValue().toString();
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(163, 195, 157, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
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
		JScrollPane scrollPane_availableFS = new JScrollPane(availableFSList);
		scrollPane_availableFS.setBounds(342, 94, 309, 236);
		add(scrollPane_availableFS);
		
		//Dispatched Field Supes list
		DefaultListModel<String> dispatchedFS = new DefaultListModel<String>();
		for (String supe: getDispatchedFieldSupes()){
			dispatchedFS.addElement(supe);
		}
		JList<String> dispatchedFSList = new JList<String>(dispatchedFS);
		JScrollPane scrollPane_dispatchedFS = new JScrollPane(dispatchedFSList);
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
//		String[] availFS =	
//			{//ENTER FIELD SUPERVISORS HERE
//				"Valrie Vanfleet",
//				"Loralee Eckert",
//				"Romona Seats",
//				"Danita Mormon",
//				"Shelton Kleiman",
//				"Ty Kovacich",
//				"Lourie Wake",
//				"Diane Toto",
//				"Trudy Blount",
//				"Claude Ferrel",
//				"Santos Thrall",
//				"Lavonda Nanney",
//				"Tanner Laduke",
//				"Oliver Heyden",
//				"Edward Dunson",
//				"Prince Holsey",
//				"Annita Ragsdale",
//				"Carla Lopinto",
//				"Amber Covey",
//				"Alysia Ertel", }; 
		
		availableFS = new ArrayList<String>();
		availableFS.add("Mary McDougal");
		availableFS.add("Lenny Celt");
		availableFS.add("Seven McCallister");
		
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
//		String[] dispatchedArray = {"Some Dude", "Another dude", "Yetanother Dude"};
		
		dispatchedFS = new ArrayList<String>();
		dispatchedFS.add("Bobby Bob");
		dispatchedFS.add("Sally Mae");
		dispatchedFS.add("Green Lantern");
		
		
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
		// TODO NEEDS TO GET LIST OF CLUBS FROM SERVER OR LOCAL
		ArrayList<String> clubs = new ArrayList<>();
		
		//ArrayList<String clubs = 
		
		//String[] clubsArray = {"The Mickey Mouse", "Cliffhangers", "Hulk-smashers", "The Non-Club"};
		
		
		if (activeClubs!=null){
			for (Club club : activeClubs){
				clubs.add(club.toString());
			}
		}
		
		/////TEST DATA////////////////
		clubs.add("The Mickey Mouse");
		clubs.add("Cliffhangers");
		clubs.add("The Non-Club");
		/////////////////////////////
		
		if (clubs.size()==0){
			clubs.add("(No clubs!)");
			
		} else{
			clubs.remove("(No clubs!)");
		}
		
		return clubs;
	}
}
