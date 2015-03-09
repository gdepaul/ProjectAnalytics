package View;

import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Panel_Dispatch extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4719929453960671801L;

	public Panel_Dispatch(String clientName, ObjectOutputStream out) {
		setLayout(null);
		
		JTextArea textArea_fieldSupe = new JTextArea();
		textArea_fieldSupe.setBounds(71, 131, 100, 22);
		textArea_fieldSupe.setText("Field supe:");
		add(textArea_fieldSupe);
		
		JTextArea textArea_club = new JTextArea();
		textArea_club.setBounds(71, 175, 100, 22);
		textArea_club.setText("      Club:");
		add(textArea_club);
		
		JTextArea textArea_action = new JTextArea();
		textArea_action.setBounds(71, 219, 100, 22);
		textArea_action.setText("    Action:");
		add(textArea_action);
		
		JTextArea textArea_timeIn = new JTextArea();
		textArea_timeIn.setBounds(71, 264, 100, 22);
		textArea_timeIn.setText("   Time in:");
		add(textArea_timeIn);
		
		
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
		JSpinner spinner_fieldSupes = new JSpinner(fieldSupeModel);
		spinner_fieldSupes.setBounds(190, 130, 120, 20);
		add(spinner_fieldSupes);
		
		String[] clubsArray = getClubs();	//THE METHOD getClubs() IS NOT FULLY IMPLEMENTED, SEE BELOW
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		JSpinner spinner_clubs = new JSpinner(clubsModel);
		spinner_clubs.setBounds(190, 175, 120, 20);
		add(spinner_clubs);
		
		String[] actionArray = {"CashDrop", "ChangeDrop", "InitialCashBox", "TicketDrop"};
		SpinnerListModel actionModel = new SpinnerListModel(actionArray);		
		JSpinner spinner_action = new JSpinner(actionModel);
		spinner_action.setBounds(190, 220, 120, 20);
		add(spinner_action);
		
		JTextField textField_timeIn = new JTextField();
		textField_timeIn.setBounds(190, 265, 120, 20);
		add(textField_timeIn);
		textField_timeIn.setColumns(10);
		
		JButton btnDispatch = new JButton("DISPATCH");
		btnDispatch.setBounds(70, 330, 240, 70);
		add(btnDispatch);
		
	}

	private String[] getClubs() {
		// TODO NEEDS TO GET LIST OF CLUBS FROM SERVER OR LOCAL
		
		String[] clubs = {"The Mickey Mouse", "Cliffhangers", "Hulk-smashers", "The Non-Club"};
		
		return clubs;
	}
}
