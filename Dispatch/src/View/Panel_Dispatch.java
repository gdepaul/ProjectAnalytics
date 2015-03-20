package View;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

public class Panel_Dispatch extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4719929453960671801L;

	public Panel_Dispatch(String clientName, ObjectOutputStream out) {
		setLayout(null);
		
		JTextArea textArea_fieldSupe = new JTextArea();
		textArea_fieldSupe.setBounds(35, 45, 100, 22);
		textArea_fieldSupe.setText("Field supe:");
		add(textArea_fieldSupe);
		
		JTextArea textArea_club = new JTextArea();
		textArea_club.setBounds(35, 78, 100, 22);
		textArea_club.setText("      Club:");
		add(textArea_club);
		
		JTextArea textArea_action = new JTextArea();
		textArea_action.setBounds(35, 111, 100, 22);
		textArea_action.setText("    Action:");
		add(textArea_action);
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBounds(35, 144, 100, 22);
		textArea_timeIn.setText("   Time in:");
		add(textArea_timeIn);
		
		ArrayList<String> fieldSupesArray = getAvailableFieldSupes();
		SpinnerListModel fieldSupeModel = new SpinnerListModel(fieldSupesArray);
		JSpinner spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(163, 47, 120, 20);
		add(spinner_fieldSupes);
		
		ArrayList<String> clubsArray = getClubs();	//THE METHOD getClubs() IS NOT FULLY IMPLEMENTED, SEE BELOW
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		JSpinner spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(163, 80, 120, 20);
		add(spinner_clubs);
		
		String[] actionArray = {"CashDrop", "ChangeDrop", "InitialCashBox", "TicketDrop"};
		SpinnerListModel actionModel = new SpinnerListModel(actionArray);		
		JSpinner spinner_action = new JSpinner(actionModel);
		spinner_action.setBounds(163, 113, 120, 20);
		add(spinner_action);
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(163, 146, 120, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
		JButton btnDispatch = new JButton("DISPATCH");
		btnDispatch.setBounds(43, 200, 240, 70);
		add(btnDispatch);
		
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for (String supe: getAvailableFieldSupes()){
			list.addElement(supe);
		}
		JList<String> availableFSList = new JList<String>(list);
		JScrollPane scrollPane_availableFS = new JScrollPane(availableFSList);
		scrollPane_availableFS.setBounds(342, 94, 309, 176);
		add(scrollPane_availableFS);
		
		JScrollPane scrollPane_dispatchedFS = new JScrollPane();
		scrollPane_dispatchedFS.setBounds(342, 335, 309, 156);
		add(scrollPane_dispatchedFS);
		
		JButton btnMakeAvailable = new JButton("Make Available");
		btnMakeAvailable.setBounds(43, 388, 240, 44);
		add(btnMakeAvailable);
		
		JTextArea txtrAvailableFS = new JTextArea();
		txtrAvailableFS.setText("Available Field Supervisors");
		txtrAvailableFS.setBounds(384, 61, 228, 22);
		add(txtrAvailableFS);
		
		JTextArea txtrDispatchedFS = new JTextArea();
		txtrDispatchedFS.setText("Dispatched Field Supervisors");
		txtrDispatchedFS.setBounds(384, 302, 228, 22);
		add(txtrDispatchedFS);
		
	}

	private ArrayList<String> getAvailableFieldSupes() {
		// TODO NEEDS TO GET LIST OF AVAILABLE FIELD SUPES FROM SERVER OR LOCAL
		ArrayList<String> availableFieldSupes = new ArrayList<>();
		String[] availFS =	
			{//ENTER FIELD SUPERVISORS HERE
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
		for (String supe : availFS){
			availableFieldSupes.add(supe);
		}
		return availableFieldSupes;
	}

	private ArrayList<String> getDispatched() {
		// TODO NEEDS TO GET LIST OF DISPATCHED FIELD SUPES FROM SERVER OR LOCAL
		ArrayList<String> dispatched = new ArrayList<>();
		String[] dispatchedArray = {"Some Dude", "Another dude", "Yetanother Dude"};
		for (String supe: dispatchedArray){
			dispatched.add(supe);
		}
		return dispatched;
	}

	private ArrayList<String> getClubs() {
		// TODO NEEDS TO GET LIST OF CLUBS FROM SERVER OR LOCAL
		ArrayList<String> clubs = new ArrayList<>();
		
		//ArrayList<String clubs = 
		
		String[] clubsArray = {"The Mickey Mouse", "Cliffhangers", "Hulk-smashers", "The Non-Club"};
		
		for (String booth : clubsArray){
			clubs.add(booth);
		}
		return clubs;
	}
}
