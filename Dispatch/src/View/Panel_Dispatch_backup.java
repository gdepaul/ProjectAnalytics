package View;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import model.Booth;
import model.dispatch.DispatchAll;
import model.dispatch.DispatchFieldSupe;
import model.dispatch.FreeFieldSupe;

public class Panel_Dispatch_backup extends JPanel {
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
	private String cashierSelected;
	private String actionSelected;
	private String DFSSelected;
	private String dispatchedSelected;
	
	//Selected values to reset during updates
	private String DFSSelectedReset;
	private String cashierSelectedReset;
	private String actionSelectedReset;
	private String dispatchedSelectedReset;
	
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
		
		if (fieldSupesArray.contains(DFSSelectedReset)){
			spinner_fieldSupes.setValue(DFSSelectedReset);
		} else {
			DFSSelected = spinner_fieldSupes.getValue().toString();
			DFSSelectedReset = DFSSelected;
		}
		
		
		//Remove, update, replace spinner_clubs
		remove(spinner_clubs);
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(163, 127, 157, 20);
		spinner_clubs.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubs);
		
		if (clubsArray.contains(cashierSelectedReset)){
			spinner_clubs.setValue(cashierSelectedReset);
		}else {
		cashierSelected = spinner_clubs.getValue().toString();
		cashierSelectedReset = cashierSelected;
		}
		
		//Remove, update, replace spinner_freeUp
		remove(spinner_freeUp);
		ArrayList<String> dispatchedArray = getDispatchedFieldSupes();
		SpinnerListModel dispatchedModel = new SpinnerListModel(dispatchedArray);
		spinner_freeUp = new JSpinner(dispatchedModel);
		spinner_freeUp.setBounds(163, 446, 147, 22);
		spinner_freeUp.addChangeListener(new DispatchedSpinnerListener());
		add(spinner_freeUp);
		
		if (dispatchedArray.contains(dispatchedSelectedReset)){
			spinner_freeUp.setValue(dispatchedSelectedReset);
		} else{
		dispatchedSelected = spinner_freeUp.getValue().toString();
		dispatchedSelectedReset = dispatchedSelected;
		}
		
		//Remove, update, replace scrollPane_availableFS
		remove(scrollPane_availableFS);
		DefaultListModel<String> availFS = new DefaultListModel<String>();
		for (String supe: getAvailableFieldSupes()){
			availFS.addElement(supe);
		}
		JList<String> availableFSList = new JList<String>(availFS);
		availableFSList.addMouseListener(new MouseAdapter() {
		    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
		        JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0) {
		                Object o = list.getModel().getElementAt(index);
		                //System.out.println("Double-clicked on: " + o.toString());
		                spinner_fieldSupes.setValue(o.toString());
		              }
		        }
		    }
		});
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
		dispatchedFSList.addMouseListener(new MouseAdapter() {
		    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
		        JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0) {
		                Object o = list.getModel().getElementAt(index);
		                //System.out.println("Double-clicked on: " + o.toString());
		                spinner_freeUp.setValue(o.toString());
		              }
		        }
		    }
		});
		scrollPane_dispatchedFS = new JScrollPane(dispatchedFSList);
		scrollPane_dispatchedFS.setBounds(342, 388, 309, 311);
		add(scrollPane_dispatchedFS);
		
		spinner_action.setValue(actionSelectedReset);
	}
	
	/**
	 * DispatchedSpinner Listener
	 */
	private class DispatchedSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			dispatchedSelected = spinner_freeUp.getValue().toString();
			dispatchedSelectedReset = dispatchedSelected;
		}
		
	}
	
	
	/**
	 * AvailableSpinner Listener
	 */
	private class AFSSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			DFSSelected = spinner_fieldSupes.getValue().toString();
			DFSSelectedReset = DFSSelected;
		}
		
	}
	
	/**
	 * ClubSelected Spinner Listener
	 */
	private class ClubSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			cashierSelected = spinner_clubs.getValue().toString();
			cashierSelectedReset = cashierSelected;
		}
		
	}
	
	/**
	 * ActionSpinner Listener
	 */
	private class ActionSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			actionSelected = spinner_action.getValue().toString();
			actionSelectedReset = actionSelected;
		}
		
	}
	
	/**
	 *	DispatchButton Listener
	 *
	 */
	private class DispatchListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				
				// Process actionSelected on Club
				if (DFSSelected.compareTo("(No Field Supervisors available)")!=0){
					if (actionSelected.compareTo("InitialCashBox")==0){
						//DFSSelected;clubSelected;
						//Dispatch field supe
						int dialogButton = JOptionPane.OK_OPTION;
						int option = JOptionPane.showConfirmDialog(null, "Send " + DFSSelected + " with " + cashierSelected +" for initial drop delivery?","Confirm?",dialogButton);
						if (option==JOptionPane.OK_OPTION){
							try {
								output.writeObject(new DispatchFieldSupe(clientName, DFSSelected));
							} catch (IOException e) {
								JOptionPane.showMessageDialog(getParent(), "IO Exception Line 193");
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(getParent(), "Initial Cash drop delivery canceled.");
						}
					}
					if (actionSelected.compareTo("Dispatch")==0){
						try {
							output.writeObject(new DispatchFieldSupe(clientName, DFSSelected));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
			JOptionPane.showMessageDialog(getParent(), "" +dispatchedSelected + " returned to active list.");
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
	public Panel_Dispatch_backup(String clientName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		this.clientName = clientName;
		this.output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;		
		DFSSelectedReset = "(no value)";
		cashierSelectedReset = "(no value)";
		actionSelectedReset = "(no value)";
		dispatchedSelectedReset = "(no value)";
		
		setLayout(null);
		
		JTextArea textArea_fieldSupe = new JTextArea();
		textArea_fieldSupe.setBackground(SystemColor.control);
		textArea_fieldSupe.setBounds(35, 94, 100, 28);
		textArea_fieldSupe.setText("Field supe:");
		add(textArea_fieldSupe);
		
		JTextArea txtrCashier = new JTextArea();
		txtrCashier.setBackground(SystemColor.control);
		txtrCashier.setBounds(35, 133, 100, 28);
		txtrCashier.setText("   Booth:");
		add(txtrCashier);
		
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
		cashierSelected = spinner_clubs.getValue().toString();
		
		String[] actionArray = {"InitialCashBox","Dispatch", "Other"};
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
		availableFSList.addMouseListener(new MouseAdapter() {
		    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
		        JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0) {
		                Object o = list.getModel().getElementAt(index);
		                //System.out.println("Double-clicked on: " + o.toString());
		                spinner_fieldSupes.setValue(o.toString());
		              }
		        }
		    }
		});
		scrollPane_availableFS = new JScrollPane(availableFSList);
		scrollPane_availableFS.setBounds(342, 94, 309, 236);
		add(scrollPane_availableFS);
		
		//Dispatched Field Supes list
		DefaultListModel<String> dispatchedFS = new DefaultListModel<String>();
		for (String supe: getDispatchedFieldSupes()){
			dispatchedFS.addElement(supe);
		}
		JList<String> dispatchedFSList = new JList<String>(dispatchedFS);
		dispatchedFSList.addMouseListener(new MouseAdapter() {
		    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
		        JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0) {
		                Object o = list.getModel().getElementAt(index);
		                //System.out.println("Double-clicked on: " + o.toString());
		                spinner_freeUp.setValue(o.toString());
		              }
		        }
		    }
		});
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
		dispatchedSelectedReset = dispatchedSelected;
		
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
			clubs.add("(No cashiers!)");
			
		} else{
			clubs.remove("(No cashiers!)");
		}
		
		return clubs;
	}


}
