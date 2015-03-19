package View;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.JList;

public class Panel_Scheduler extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5973410696147468360L;
	private JTextField textField_name;
	private JTextField textField_timeOut;
	public Panel_Scheduler() {
		setLayout(null);
		
		JTextArea textArea_name = new JTextArea();
		textArea_name.setBounds(71, 131, 100, 22);
		textArea_name.setText("      Name:");
		add(textArea_name);
		
		JTextArea textArea_role = new JTextArea();
		textArea_role.setBounds(71, 175, 100, 22);
		textArea_role.setText("      Role:");
		add(textArea_role);
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBounds(71, 219, 100, 22);
		textArea_timeIn.setText("  Time out:");
		add(textArea_timeIn);
		
		JTextArea textArea_timeOut = new JTextArea();
		textArea_timeOut.setBounds(71, 264, 100, 22);
		textArea_timeOut.setText("   Time in:");
		add(textArea_timeOut);
		
		
		String[] fieldSupesArray = {//ENTER FIELD SUPERVISORS HERE
				"Valrie Vanfleet",
				"Loralee Eckert",
				"Romona Seats",
				"Danita Mormon",
				"Shelton Kleiman",
				"Ty Kovacich",
				"Lourie Wake",
				"Diane Toto",
				"Trudy Blount",
				"Claude Ferrel",
				"Santos Thrall",
				"Lavonda Nanney",
				"Tanner Laduke",
				"Oliver Heyden",
				"Edward Dunson",
				"Prince Holsey",
				"Annita Ragsdale",
				"Carla Lopinto",
				"Amber Covey",
				"Alysia Ertel", }; 
		SpinnerListModel fieldSupeModel = new SpinnerListModel(fieldSupesArray);
		
		String[] RoleArray = {"Field Supervisor", "Cashier"};
		SpinnerListModel RoleModel = new SpinnerListModel(RoleArray);	//~LOL~
		JSpinner spinner_roles = new JSpinner(RoleModel);
		spinner_roles.setBounds(190, 175, 120, 20);
		add(spinner_roles);
		
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(190, 265, 120, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
		JButton btnDispatch = new JButton("Add Employee");
		btnDispatch.setBounds(70, 330, 240, 70);
		add(btnDispatch);
		
		textField_name = new JTextField();
		textField_name.setBounds(190, 133, 120, 20);
		add(textField_name);
		textField_name.setColumns(10);
		
		textField_timeOut = new JTextField();
		textField_timeOut.setBounds(190, 221, 120, 20);
		add(textField_timeOut);
		textField_timeOut.setColumns(10);
		
		JList<String> list = new JList<String>();
		list.setBounds(328, 92, 337, 402);
		add(list);
		
		JTextArea textArea_IDList = new JTextArea();
		textArea_IDList.setBounds(328, 59, 46, 22);
		textArea_IDList.setText("ID");
		add(textArea_IDList);
		
		JTextArea textArea_timeInList = new JTextArea();
		textArea_timeInList.setBounds(445, 59, 86, 22);
		textArea_timeInList.setText("Time In");
		add(textArea_timeInList);
		
		JTextArea textArea_timeOutList = new JTextArea();
		textArea_timeOutList.setBounds(580, 59, 86, 22);
		textArea_timeOutList.setText("Time Out");
		add(textArea_timeOutList);
		
	}
}
