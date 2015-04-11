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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Club;
import model.dispatch.DispatchFieldSupe;
import model.dispatch.FreeFieldSupe;
import javax.swing.SwingConstants;

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
	private JSpinner spinner_freeUp;
	private JSpinner spinner_location;
	private JScrollPane scrollPane_availableFS;
	private JScrollPane scrollPane_dispatchedFS;
	private JSpinner spinner_action;
	

	//for spinner values
	private String cashierSelected;
	//private String actionSelected;
	private String DFSSelected;
	private String dispatchedSelected;
	
	//Selected values to reset during updates
	private String DFSSelectedReset;
	private String cashierSelectedReset;
//	private String actionSelectedReset;
	private String dispatchedSelectedReset;
	private String locationSelected;
	private String locationSelectedReset;
	private String actionSelected;
	private String actionSelectedReset;
	
	//Hashtables for hacky solution to keep track of where supes are and what they're doing
	private HashMap<String, String> hash_fs_actions;
	private HashMap<String, String> hash_fs_locations;
	
	
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
		RolloverSpinnerListModel fieldSupeModel = new RolloverSpinnerListModel(fieldSupesArray);
		spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(121, 96, 199, 36);
		spinner_fieldSupes.addChangeListener(new AFSSpinnerListener());
		add(spinner_fieldSupes);
		
		if (fieldSupesArray.contains(DFSSelectedReset)){
			spinner_fieldSupes.setValue(DFSSelectedReset);
		} else {
			DFSSelected = spinner_fieldSupes.getValue().toString();
			DFSSelectedReset = DFSSelected;
		}
		
		
		//Remove, update, replace spinner_freeUp
		remove(spinner_freeUp);
		ArrayList<String> dispatchedArray = getDispatchedFieldSupes();
		RolloverSpinnerListModel dispatchedModel = new RolloverSpinnerListModel(dispatchedArray);
		spinner_freeUp = new JSpinner(dispatchedModel);
		spinner_freeUp.setBounds(121, 437, 199, 41);
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
		//Dispatched Field Supes list
				DefaultListModel<String> dispatchedFS = new DefaultListModel<String>();
				for (String supe: getDispatchedFieldSupes()){
					dispatchedFS.addElement(supe + "***" + hash_fs_locations.get(supe)+ "***" + hash_fs_actions.get(supe));
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
				                spinner_freeUp.setValue(o.toString().substring(0, o.toString().indexOf("*")));
				              }
				        }
				    }
				});
				scrollPane_dispatchedFS = new JScrollPane(dispatchedFSList);
				scrollPane_dispatchedFS.setBounds(342, 388, 309, 311);
				add(scrollPane_dispatchedFS);
		
		
		
		//spinner_action.setValue(actionSelectedReset);
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
	 * All-purpose listener for setting values when changed
	 */
	private class defaultSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			
			locationSelected = spinner_location.getValue().toString();
			locationSelectedReset = locationSelected;
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
					
				int dispatch = JOptionPane.showConfirmDialog(getParent(), DFSSelected + " with " + actionSelected + " to " + locationSelected + "?", null, JOptionPane.OK_CANCEL_OPTION);
				
					if (dispatch==JOptionPane.OK_OPTION){
						try {
							output.writeObject(new DispatchFieldSupe(clientName, DFSSelected));
							hash_fs_actions.put(DFSSelected, actionSelected);
							hash_fs_locations.put(DFSSelected, locationSelected);
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
			//JOptionPane.showMessageDialog(getParent(), "" +dispatchedSelected + " returned to active list.");
			try {
				output.writeObject(new FreeFieldSupe(clientName, dispatchedSelected));
				
				hash_fs_actions.remove(dispatchedSelected);
				hash_fs_locations.remove(dispatchedSelected);
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
		DFSSelectedReset = "(no value)";
		cashierSelectedReset = "(no value)";
		actionSelectedReset = "(no value)";
		dispatchedSelectedReset = "(no value)";
		
		hash_fs_actions = new HashMap<String, String>();
		hash_fs_locations = new HashMap<String, String>();
		
		setLayout(null);
		
		ArrayList<String> fieldSupesArray = getAvailableFieldSupes();
		RolloverSpinnerListModel fieldSupeModel = new RolloverSpinnerListModel(fieldSupesArray);
		spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(121, 96, 199, 36);
		spinner_fieldSupes.addChangeListener(new AFSSpinnerListener());
		add(spinner_fieldSupes);
		DFSSelected = spinner_fieldSupes.getValue().toString();
		

		
//		String[] actionArray = {"InitialCashBox","Dispatch", "Other"};
//		SpinnerListModel actionModel = new SpinnerListModel(actionArray);
		
		/*
		 * 	DISPATCH BUTTON AND LISTENER
		 */		
		JButton btnDispatch = new JButton("DISPATCH");
		btnDispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDispatch.setBounds(10, 260, 310, 70);
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
			dispatchedFS.addElement(supe + "***" + hash_fs_locations.get(supe)+ "***" + hash_fs_actions.get(supe));
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
		                spinner_freeUp.setValue(o.toString().substring(0, o.toString().indexOf("*")));
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
		JButton btnMakeAvailable = new JButton("MAKE AVAILABLE");
		btnMakeAvailable.setBounds(10, 524, 310, 70);
		add(btnMakeAvailable);
		
		AvailableListener availableListener = new AvailableListener();
		btnMakeAvailable.addActionListener(availableListener);
		
		ArrayList<String> dispatchedArray = getDispatchedFieldSupes();
		RolloverSpinnerListModel dispatchedModel = new RolloverSpinnerListModel(dispatchedArray);
		spinner_freeUp = new JSpinner(dispatchedModel);
		spinner_freeUp.setBounds(121, 437, 199, 41);
		spinner_freeUp.addChangeListener(new DispatchedSpinnerListener());
		add(spinner_freeUp);
		
		ArrayList<String> locationsArray = getLocations();
		RolloverSpinnerListModel locationsModel = new RolloverSpinnerListModel(locationsArray);
		spinner_location = new JSpinner(locationsModel);
		spinner_location.setBounds(121, 148, 199, 36);
		spinner_location.addChangeListener(new defaultSpinnerListener());
		locationSelected = spinner_location.getValue().toString();
		add(spinner_location);
		
		ArrayList<String> actionsArray = getActions();
		RolloverSpinnerListModel actionsModel = new RolloverSpinnerListModel(actionsArray);
		spinner_action = new JSpinner(actionsModel);
		spinner_action.setBounds(121, 195, 199, 35);
		spinner_action.addChangeListener(new defaultSpinnerListener());
		actionSelected = spinner_action.getValue().toString();
		add(spinner_action);
		
		JLabel lblNewLabel = new JLabel("Field Supervisor:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 109, 101, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Location:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(35, 159, 76, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Action:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(35, 205, 76, 14);
		add(lblNewLabel_2);
		
		JLabel lblAvailableFieldSupervisors = new JLabel("Available Field Supervisors");
		lblAvailableFieldSupervisors.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvailableFieldSupervisors.setBounds(342, 69, 309, 14);
		add(lblAvailableFieldSupervisors);
		
		JLabel lblDispatchedFieldSupervisors = new JLabel("Dispatched Field Supervisors");
		lblDispatchedFieldSupervisors.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchedFieldSupervisors.setBounds(342, 363, 309, 14);
		add(lblDispatchedFieldSupervisors);
		
		JLabel lblFieldSupervisor = new JLabel("Field Supervisor:");
		lblFieldSupervisor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFieldSupervisor.setBounds(-7, 450, 118, 14);
		add(lblFieldSupervisor);
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
	
	private ArrayList<String> getActions(){
		
		ArrayList<String> actions = new ArrayList<String>();
		actions.add("Jafar/Emergency");
		actions.add("Alice/Cash Drop");
		actions.add("Charming/Full Sheets");
		actions.add("Bambi/Half Sheets");
		actions.add("Aladdi/Single tickets");
		actions.add("Belle/Wristbands");
		actions.add("Mulan/Change1A");
		actions.add("Simba/Change2B");
		actions.add("Fiona/Change3C");
		actions.add("Pinochio/FS or Question");
		actions.add("Genie/Credit Card Q");
		actions.add("Mushu/Field Supe Q");
		return actions;
	}
	
	private ArrayList<String> getLocations() {
		ArrayList<String> locations = new ArrayList<String>();
		locations.add("Castle 1");
		locations.add("Castle 2");
		locations.add("Castle 3");
		locations.add("Castle 4");
		locations.add("Cottage 1");
		locations.add("Cottage 2");
		locations.add("Cottage 3");
		locations.add("Wonderland");
		locations.add("Wishing Well");
		locations.add("Pride Rock");
		locations.add("Palace");
		locations.add("Enchanted Forest");
		locations.add("Swamp");
		locations.add("Woods");
		locations.add("Beanstalk");
		return locations;
	}
	
	 public class RolloverSpinnerListModel extends SpinnerListModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4089704428973563804L;
		public RolloverSpinnerListModel(List<String> values) {
		      super(values);
		   }
		 public RolloverSpinnerListModel(Object[] values) {
		      super(values);
		   }
		   public Object getNextValue() {
		      Object returnValue = super.getNextValue();
		      if (returnValue == null) {
		         returnValue = getList().get(0);
		      }
		      return returnValue;
		   }
		 public Object getPreviousValue() {
		      Object returnValue = super.getPreviousValue();
		      if (returnValue == null) {
		         List list = getList();
		         returnValue = list.get(list.size() - 1);
		      }
		      return returnValue;
		   }
		}
}
