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

import model.Club;
import model.dispatch.AddFieldSupe;
import model.dispatch.RemoveFieldSupe;

public class Panel_Scheduler extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5973410696147468360L;
	private String clientName;
	private ObjectOutputStream output;
	private List<Club> activeClubs;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	private List<String> fullEmployeeList;
	
	//widgets
	private JTextField textField_name;
	private JTextField textField_timeOut;
	JSpinner spinner_removeEmp;
	JScrollPane scrollPane_empList;
	
	//initialize some values for scope
	private String addEmployee;
	private String removeEmployee;
	
	/**
	 * UpdateList
	 */
	public void UpdateLists(List<Club> clubs, List<String> available,
			List<String> dispatched) {
		this.activeClubs = clubs;
		this.availableFS = available;
		this.dispatchedFS = dispatched;

		fullEmployeeList.clear();
		// repopulate fullEmployeeList;
		if (availableFS != null) {
			for (String emp : availableFS) {
				fullEmployeeList.add(emp);
			}
		}
		if (dispatchedFS != null) {
			for (String emp : dispatchedFS) {
				fullEmployeeList.add(emp);
			}
		}
		// sort
		Collections.sort(fullEmployeeList);

		// Cheesy remove, reset, and replace ScrollPane
		remove(scrollPane_empList);
		DefaultListModel<String> currentEmployees = new DefaultListModel<String>();
		for (String emp : fullEmployeeList) {
			currentEmployees.addElement(emp);
		}
		if (currentEmployees.size() == 0) {
			currentEmployees.addElement("(No current employees)");
		} else {
			currentEmployees.removeElement("(No current employees)");
		}

		JList<String> empList = new JList<String>(currentEmployees);
		scrollPane_empList = new JScrollPane(empList);
		scrollPane_empList.setBounds(372, 105, 313, 488);
		add(scrollPane_empList);
		
		// Cheesy remove, reset, and replace Spinner
		remove(spinner_removeEmp);
		ArrayList<String> removeEmpArray = new ArrayList<String>();
		for (String emp : fullEmployeeList){
			removeEmpArray.add(emp);
		}
		if (removeEmpArray.size()==0){
			removeEmpArray.add("(No current employees)");
		} else{
			currentEmployees.removeElement("(No current employees)");
		}
		SpinnerListModel removeEmpModel = new SpinnerListModel(removeEmpArray);
		spinner_removeEmp = new JSpinner(removeEmpModel);
		spinner_removeEmp.setBounds(190, 463, 154, 27);
		add(spinner_removeEmp);

	}
	
	
	/**
	 *	Add Employee Button Listener
	 *
	 */
	private class AddEmpListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			addEmployee = textField_name.getText();
			JOptionPane.showMessageDialog(getParent(), "Add Employee Button!\n"+
														"addEmployee: " + addEmployee);
			try {
				output.writeObject(new AddFieldSupe(clientName, addEmployee));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  Remove Employee Button Listener
	 */
	private class RemoveEmpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			removeEmployee = spinner_removeEmp.getValue().toString();
			JOptionPane.showMessageDialog(getParent(), "Remove Employee Button!\n"+
														"removeEmployee: " + removeEmployee);
			try {
				output.writeObject(new RemoveFieldSupe(clientName, removeEmployee));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public Panel_Scheduler(String userName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		clientName = userName;
		output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		fullEmployeeList = new ArrayList<String>();
		
		//populate fullEmployeeList;
		if (availableFS!=null){
			for (String emp : availableFS){
				fullEmployeeList.add(emp);
			}
		}
		if (dispatchedFS!=null){
			for (String emp : dispatchedFS){
				fullEmployeeList.add(emp);
			}
		}
		//sort
		Collections.sort(fullEmployeeList);
		
		
//		//FOR TESTING ONLY /////////////////////////////////////////////
//		String[] fieldSupesArray = {//ENTER FIELD SUPERVISORS HERE
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
//		
//		for (String supe : fieldSupesArray){
//			fullEmployeeList.add(supe);
//		}
//		Collections.sort(fullEmployeeList);
//		//////////////////////////////////////////////////////////////////
		
		
		
		setLayout(null);
		
		JTextArea textArea_name = new JTextArea();
		textArea_name.setBackground(SystemColor.control);
		textArea_name.setBounds(71, 131, 100, 22);
		textArea_name.setText("      Name:");
		textArea_name.setEditable(false);
		add(textArea_name);
		
		JTextArea textArea_role = new JTextArea();
		textArea_role.setBackground(SystemColor.control);
		textArea_role.setBounds(71, 175, 100, 22);
		textArea_role.setText("      Role:");
		textArea_role.setEditable(false);
		add(textArea_role);
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBackground(SystemColor.control);
		textArea_timeIn.setBounds(71, 219, 100, 22);
		textArea_timeIn.setText("  Time out:");
		textArea_timeIn.setEditable(false);
		add(textArea_timeIn);
		
		JTextArea textArea_timeOut = new JTextArea();
		textArea_timeOut.setBackground(SystemColor.control);
		textArea_timeOut.setBounds(71, 264, 100, 22);
		textArea_timeOut.setText("   Time in:");
		textArea_timeOut.setEditable(false);
		add(textArea_timeOut);
		
		String[] RoleArray = {"Field Supervisor", "Cashier"};
		SpinnerListModel RoleModel = new SpinnerListModel(RoleArray);	//~LOL~
		JSpinner spinner_roles = new JSpinner(RoleModel);
		spinner_roles.setBounds(190, 175, 154, 20);
		add(spinner_roles);
		
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(190, 265, 154, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
		JButton btnAddEmp = new JButton("Add Employee");
		btnAddEmp.setBounds(70, 330, 274, 70);
		btnAddEmp.addActionListener(new AddEmpListener());
		add(btnAddEmp);
		
		textField_name = new JTextField();
		textField_name.setBounds(190, 133, 154, 20);
		add(textField_name);
		textField_name.setColumns(10);
		
		textField_timeOut = new JTextField();
		textField_timeOut.setBounds(190, 221, 154, 20);
		add(textField_timeOut);
		textField_timeOut.setColumns(10);
		
		JTextArea textArea_IDList = new JTextArea();
		textArea_IDList.setBackground(SystemColor.control);
		textArea_IDList.setBounds(372, 59, 46, 22);
		textArea_IDList.setText("ID");
		textArea_IDList.setEditable(false);
		add(textArea_IDList);
		
		JTextArea textArea_timeInList = new JTextArea();
		textArea_timeInList.setBackground(SystemColor.control);
		textArea_timeInList.setBounds(503, 59, 86, 22);
		textArea_timeInList.setText("Time In");
		textArea_timeInList.setEditable(false);
		add(textArea_timeInList);
		
		JTextArea textArea_timeOutList = new JTextArea();
		textArea_timeOutList.setBackground(SystemColor.control);
		textArea_timeOutList.setBounds(599, 59, 86, 22);
		textArea_timeOutList.setText("Time Out");
		textArea_timeOutList.setEditable(false);
		add(textArea_timeOutList);
		
		DefaultListModel<String> currentEmployees = new DefaultListModel<String>();
		for (String emp: fullEmployeeList){
			currentEmployees.addElement(emp);
		}
		if (currentEmployees.size()==0){
			currentEmployees.addElement("(No current employees)");
		} else{
			currentEmployees.removeElement("(No current employees)");
		}
		
		JList<String> empList = new JList<String>(currentEmployees);
		scrollPane_empList = new JScrollPane(empList);
		scrollPane_empList.setBounds(372, 105, 313, 488);
		add(scrollPane_empList);
		
		
		JButton btnRemoveEmployee = new JButton("Remove Employee");
		btnRemoveEmployee.setBounds(71, 515, 273, 78);
		btnRemoveEmployee.addActionListener(new RemoveEmpListener());
		add(btnRemoveEmployee);
		

		ArrayList<String> removeEmpArray = new ArrayList<String>();
		for (String emp : fullEmployeeList){
			removeEmpArray.add(emp);
		}
		if (removeEmpArray.size()==0){
			removeEmpArray.add("(No current employees)");
		} else{
			currentEmployees.removeElement("(No current employees)");
		}
		SpinnerListModel removeEmpModel = new SpinnerListModel(removeEmpArray);
		spinner_removeEmp = new JSpinner(removeEmpModel);
		spinner_removeEmp.setBounds(190, 463, 154, 27);
		add(spinner_removeEmp);
		
		
		JTextArea txtrName = new JTextArea();
		txtrName.setBackground(SystemColor.control);
		txtrName.setText("     Name:");
		txtrName.setBounds(71, 464, 100, 22);
		txtrName.setEditable(false);
		add(txtrName);
		
	}
}
