package View;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import model.Club;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		textArea_fieldSupe.setBounds(35, 94, 100, 22);
		textArea_fieldSupe.setText("Field supe:");
		add(textArea_fieldSupe);
		
		JTextArea textArea_club = new JTextArea();
		textArea_club.setBounds(35, 127, 100, 22);
		textArea_club.setText("      Club:");
		add(textArea_club);
		
		JTextArea textArea_action = new JTextArea();
		textArea_action.setBounds(35, 160, 100, 22);
		textArea_action.setText("    Action:");
		add(textArea_action);
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBounds(35, 193, 100, 22);
		textArea_timeIn.setText("   Time in:");
		add(textArea_timeIn);
		
		ArrayList<String> fieldSupesArray = getAvailableFieldSupes();
		SpinnerListModel fieldSupeModel = new SpinnerListModel(fieldSupesArray);
		JSpinner spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(163, 96, 157, 20);
		add(spinner_fieldSupes);
		
		ArrayList<String> clubsArray = getClubs();	//THE METHOD getClubs() IS NOT FULLY IMPLEMENTED, SEE BELOW
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		JSpinner spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(163, 127, 157, 20);
		add(spinner_clubs);
		
		String[] actionArray = {"CashDrop", "ChangeDrop", "InitialCashBox", "TicketDrop"};
		SpinnerListModel actionModel = new SpinnerListModel(actionArray);		
		JSpinner spinner_action = new JSpinner(actionModel);
		spinner_action.setBounds(163, 162, 157, 20);
		add(spinner_action);
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(163, 195, 157, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
		JButton btnDispatch = new JButton("DISPATCH");
		btnDispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDispatch.setBounds(35, 246, 285, 70);
		add(btnDispatch);
		
		
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
		
		JButton btnMakeAvailable = new JButton("Make Available");
		btnMakeAvailable.setBounds(35, 524, 277, 44);
		add(btnMakeAvailable);
		
		JTextArea txtrAvailableFS = new JTextArea();
		txtrAvailableFS.setText("Available Field Supervisors");
		txtrAvailableFS.setBounds(384, 61, 228, 22);
		add(txtrAvailableFS);
		
		JTextArea txtrDispatchedFS = new JTextArea();
		txtrDispatchedFS.setText("Dispatched Field Supervisors");
		txtrDispatchedFS.setBounds(384, 355, 228, 22);
		add(txtrDispatchedFS);
		
	}

	private ArrayList<String> getAvailableFieldSupes() {
		// TODO NEEDS TO GET LIST OF AVAILABLE FIELD SUPES FROM SERVER OR LOCAL
		ArrayList<String> availableFieldSupes = new ArrayList<String>();
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

	private ArrayList<String> getDispatchedFieldSupes() {
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
