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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import model.Club;
import model.dispatch.AddActiveClub;
import model.dispatch.AddFieldSupe;
import model.dispatch.RemoveClub;
import model.dispatch.RemoveFieldSupe;

public class Panel_Scheduler extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5973410696147468360L;
	private String clientName;
	private ObjectOutputStream output;
	private List<Club> activeCashiers;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	private List<String> fieldSupeTeamsList;
	private List<String> cashiersList;
	
	//widgets
	private JTextField textField_name;
	private JSpinner spinner_removeFS;
	private JSpinner spinner_roles;
	private JSpinner spinner_removeCashier;
	private JScrollPane scrollPane_listFS;
	private JScrollPane scrollPane_listCashiers;
	
	
	//initialize some values for scope
	private String addEmployee;
	private String removeFieldSupe;
	private String removeCashier;
	
	/**
	 * UpdateList
	 */
	public void UpdateLists(List<Club> cashiers, List<String> available,
			List<String> dispatched) {
		this.activeCashiers = cashiers;
		this.availableFS = available;
		this.dispatchedFS = dispatched;

		fieldSupeTeamsList.clear();
		// repopulate Field Supe List;
		if (availableFS != null) {
			for (String emp : availableFS) {
				fieldSupeTeamsList.add(emp);
			}
		}
		if (dispatchedFS != null) {
			for (String emp : dispatchedFS) {
				fieldSupeTeamsList.add(emp);
			}
		}
		// sort
		Collections.sort(fieldSupeTeamsList);
		
		cashiersList.clear();
		// repopulate cashier list;
		if (activeCashiers != null){
			for (Club cashier : activeCashiers){
				cashiersList.add(cashier.getClubName());
			}
		}
		//sort
		Collections.sort(cashiersList);

		// Cheesy remove, reset, and replace ScrollPane
		remove(scrollPane_listFS);
		DefaultListModel<String> currentEmployees = new DefaultListModel<String>();
		for (String emp : fieldSupeTeamsList) {
			currentEmployees.addElement(emp);
		}
		if (currentEmployees.size() == 0) {
			currentEmployees.addElement("(No current employees)");
		} else {
			currentEmployees.removeElement("(No current employees)");
		}

		JList<String> empList = new JList<String>(currentEmployees);
		scrollPane_listFS = new JScrollPane(empList);
		scrollPane_listFS.setBounds(372, 105, 313, 250);
		add(scrollPane_listFS);
		
		// Cheesy remove, reset, and replace cashier ScrollPane
		remove(scrollPane_listCashiers);
		DefaultListModel<String> currentCashiers = new DefaultListModel<String>();
		for (String cashier : cashiersList){
			currentCashiers.addElement(cashier);
		}
		if (currentCashiers.size() == 0){
			currentCashiers.addElement("(No current cashiers)");
		} else {
			currentCashiers.removeElement("(No current cashiers)");
		}
		JList<String> cashiersList = new JList<String>(currentCashiers);
		scrollPane_listCashiers = new JScrollPane(cashiersList);
		scrollPane_listCashiers.setBounds(372, 428, 311, 348);
		add(scrollPane_listCashiers);
		
		// Cheesy remove, reset, and replace remove field supe Spinner
		remove(spinner_removeFS);
		ArrayList<String> removeEmpArray = new ArrayList<String>();
		for (String emp : fieldSupeTeamsList){
			removeEmpArray.add(emp);
		}
		if (removeEmpArray.size()==0){
			removeEmpArray.add("(No current employees)");
		} else{
			currentEmployees.removeElement("(No current employees)");
		}
		SpinnerListModel removeEmpModel = new SpinnerListModel(removeEmpArray);
		spinner_removeFS = new JSpinner(removeEmpModel);
		spinner_removeFS.setBounds(181, 319, 154, 27);
		add(spinner_removeFS);
		
		// Cheesy remove, reset, and replace remove cashier Spinner
		remove(spinner_removeCashier);
		ArrayList<String> removeCashierArray = getCashiers();
		SpinnerListModel removeCashierModel = new SpinnerListModel(removeCashierArray);
		spinner_removeCashier = new JSpinner((removeCashierModel));
		spinner_removeCashier.setBounds(181, 476, 154, 27);
		add(spinner_removeCashier);
	}
	
	
	/**
	 *	Add Employee Button Listener
	 *
	 */
	private class AddEmpListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			addEmployee = textField_name.getText();
			
			String action = spinner_roles.getValue().toString();
			
			if (action.compareTo("Field Supervisor")==0){
						try {
						output.writeObject(new AddFieldSupe(clientName, addEmployee));
						JOptionPane.showMessageDialog(getParent(), addEmployee + " added to available field supervisors");
						} catch (IOException e) {
						JOptionPane.showMessageDialog(getParent(), "Failed to add " + addEmployee + " to available field supervisors.\n" +
																	"Error is Panel_Scheduler, AddEmpListener, actionPerformed override");
						e.printStackTrace();
						}
			} else if(action.compareTo("Cashier")==0){
						try {
							output.writeObject(new AddActiveClub(clientName, addEmployee)); 	//New cashier with no cash, no tickets
							JOptionPane.showMessageDialog(getParent(), addEmployee + " added to active cashiers");
							} catch (IOException e) {
							JOptionPane.showMessageDialog(getParent(), "Failed to add " + addEmployee + " to available cashiers.\n" +
																		"Error is Panel_Scheduler, AddEmpListener, actionPerformed override");
							e.printStackTrace();
							}
			}
			
		}
	}
	
	/**
	 *  Remove Employee Button Listener
	 */
	private class RemoveFieldSupeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String check = spinner_removeFS.getValue().toString();
			
			if (!availableFS.contains(check) && (!dispatchedFS.contains(check))){
				JOptionPane.showMessageDialog(getParent(), "'"+check+"' does not exist! Remove field supervisor action canceled!");
			} 
			else if(!availableFS.contains(check) && (dispatchedFS.contains(check)) ){
				JOptionPane.showMessageDialog(getParent(), "'"+check+"' is still dispatched! Remove field supervisor action canceled!");
				
			}
			else if (availableFS.contains(check)){
				removeFieldSupe = check;
				
	//			this.activeCashiers = cashiers;
	//			this.availableFS = available;
	//			this.dispatchedFS = dispatched;
	//			JOptionPane.showMessageDialog(getParent(), "Remove Employee Button!\n"+
	//														"removeEmployee: " + removeFieldSupe);
				int response = JOptionPane.showConfirmDialog(null, "Do you want to remove " + 
						removeFieldSupe + 
						" from the active list of field supervisor teams?", 
						"Confirm", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(getParent(), "Remove Field Supervisor team Action Canceled");
				} else if (response == JOptionPane.YES_OPTION) {
				
					try {
						output.writeObject(new RemoveFieldSupe(clientName, removeFieldSupe));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(getParent(), "Could not send remove to server! (224)");
						e.printStackTrace();
					}
		
					JOptionPane.showMessageDialog(getParent(), removeFieldSupe + "removed from active field supervisor teams.");
				} else if (response == JOptionPane.CLOSED_OPTION) {
				System.out.println("JOptionPane closed");
				}
			}
		}
	}
	
	/**
	 * 	Remove Cashier Button Listener
	 */
	private class RemoveCashierListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			removeCashier = spinner_removeCashier.getValue().toString();
			int response = JOptionPane.showConfirmDialog(null, "Do you want to remove " + 
																		removeCashier + 
																		" from the active list of cashiers?", 
																		"Confirm", 
																		JOptionPane.YES_NO_OPTION, 
																		JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(getParent(), "Remove Cashier Action Canceled");
			} else if (response == JOptionPane.YES_OPTION) {
			    
				try {
					output.writeObject(new RemoveClub(clientName, removeCashier));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(getParent(), "Could not send remove to server! (224)");
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(getParent(), removeCashier + "removed from active cashiers.");
			} else if (response == JOptionPane.CLOSED_OPTION) {
			    System.out.println("JOptionPane closed");
			}
		}
		
		
	}
	
	
	public Panel_Scheduler(String userName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		clientName = userName;
		output = out;
		activeCashiers = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		fieldSupeTeamsList = new ArrayList<String>();
		cashiersList = new ArrayList<String>();
		
		//populate fullFieldSupesList;
		if (availableFS!=null){
			for (String emp : availableFS){
				fieldSupeTeamsList.add(emp);
			}
		}
		if (dispatchedFS!=null){
			for (String emp : dispatchedFS){
				fieldSupeTeamsList.add(emp);
			}
		}
		//sort
		Collections.sort(fieldSupeTeamsList);
		
		// Populate cashier list;
		if (activeCashiers != null){
			for (Club cashier : activeCashiers){
				cashiersList.add(cashier.getClubName());
			}
		}
		//sort
		Collections.sort(cashiersList);	
		
		setLayout(null);
		
		String[] RoleArray = {"Field Supervisor", "Cashier"};
		SpinnerListModel RoleModel = new SpinnerListModel(RoleArray);	//~LOL~
		spinner_roles = new JSpinner(RoleModel);
		spinner_roles.setBounds(190, 175, 154, 30);
		spinner_roles.setValue("Cashier");
		add(spinner_roles);
		
		JButton btnAddEmp = new JButton("Add Employee");
		btnAddEmp.setBounds(71, 216, 274, 70);
		btnAddEmp.addActionListener(new AddEmpListener());
		add(btnAddEmp);
		
		textField_name = new JTextField();
		textField_name.setBounds(190, 133, 154, 31);
		add(textField_name);
		textField_name.setColumns(10);
		
		DefaultListModel<String> currentFieldSupes = new DefaultListModel<String>();
		for (String emp: fieldSupeTeamsList){
			currentFieldSupes.addElement(emp);
		}
		if (currentFieldSupes.size()==0){
			currentFieldSupes.addElement("(No current employees)");
		} else{
			currentFieldSupes.removeElement("(No current employees)");
		}
		
		JList<String> list_FS = new JList<String>(currentFieldSupes);
		scrollPane_listFS = new JScrollPane(list_FS);
		scrollPane_listFS.setBounds(372, 105, 313, 250);
		add(scrollPane_listFS);
		
		DefaultListModel<String> currentCashiers = new DefaultListModel<String>();
		for (String cashier : cashiersList){
			currentCashiers.addElement(cashier);
		}
		if (currentCashiers.size() == 0){
			currentCashiers.addElement("(No current cashiers)");
		} else {
			currentCashiers.removeElement("(No current cashiers)");
		}
		JList<String> cashiersList = new JList<String>(currentCashiers);
		scrollPane_listCashiers = new JScrollPane(cashiersList);
		scrollPane_listCashiers.setBounds(372, 428, 311, 348);
		add(scrollPane_listCashiers);
		
		
		JButton btnRemoveFS = new JButton("Remove Field Supe");
		btnRemoveFS.setBounds(71, 368, 273, 78);
		btnRemoveFS.addActionListener(new RemoveFieldSupeListener());
		add(btnRemoveFS);
		
		JButton btnRemoveCashier = new JButton("Remove Cashier");
		btnRemoveCashier.setBounds(71, 530, 273, 78);
		btnRemoveCashier.addActionListener(new RemoveCashierListener());
		add(btnRemoveCashier);
		

		ArrayList<String> removeEmpArray = new ArrayList<String>();
		for (String emp : fieldSupeTeamsList){
			removeEmpArray.add(emp);
		}
		if (removeEmpArray.size()==0){
			removeEmpArray.add("(No current employees)");
		} else{
			currentFieldSupes.removeElement("(No current employees)");
		}
		SpinnerListModel removeEmpModel = new SpinnerListModel(removeEmpArray);
		spinner_removeFS = new JSpinner(removeEmpModel);
		spinner_removeFS.setBounds(181, 319, 154, 27);
		add(spinner_removeFS);
		
		ArrayList<String> removeCashierArray = getCashiers();
		SpinnerListModel removeCashierModel = new SpinnerListModel(removeCashierArray);
		spinner_removeCashier = new JSpinner((removeCashierModel));
		spinner_removeCashier.setBounds(181, 476, 154, 27);
		add(spinner_removeCashier);
		
		JLabel lblFieldSupervisorTeams = new JLabel("Field Supervisor Teams:");
		lblFieldSupervisorTeams.setBounds(372, 80, 176, 14);
		add(lblFieldSupervisorTeams);
		
		JLabel lblActiveCashiers = new JLabel("Active Cashiers:");
		lblActiveCashiers.setBounds(372, 403, 125, 14);
		add(lblActiveCashiers);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(71, 136, 46, 14);
		add(lblName);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setBounds(71, 178, 46, 14);
		add(lblRole);
		
		JLabel lblFieldSupeTo = new JLabel("Field Supe to Remove:");
		lblFieldSupeTo.setBounds(31, 325, 140, 14);
		add(lblFieldSupeTo);
		
		JLabel lblCashierToRemove = new JLabel("Cashier to Remove:");
		lblCashierToRemove.setBounds(31, 482, 125, 14);
		add(lblCashierToRemove);
		
	}
	
	private ArrayList<String> getCashiers() {
		ArrayList<String> clubs = new ArrayList<>();
		
		if (activeCashiers!=null){
			for (Club club : activeCashiers){
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
