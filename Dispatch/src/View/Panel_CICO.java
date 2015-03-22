package View;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SpinnerListModel;

import model.Club;

import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Panel_CICO extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778947721922456207L;
	private JTextArea TA_StartHere;
	private String clientName;
	private ObjectOutputStream output;
	private List<Club> activeClubs;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	
	//WIDGETS
	private JTextField textField_penniesIn;
	private JTextField textField_nickelsIn;
	private JTextField textField_dimesIn;
	private JTextField textField_quartersIn;
	private JTextField textField_dollarsIn;
	private JTextField textField_twosIn;
	private JTextField textField_fivesIn;
	private JTextField textField_tensIn;
	private JTextField textField_twentiesIn;
	private JTextField textField_fiftiesIn;
	private JTextField textField_hundredsIn;
	private JTextField textField_penniesOut;
	private JTextField textField_nickelsOut;
	private JTextField textField_dimesOut;
	private JTextField textField_quartersOut;
	private JTextField textField_dollarsOut;
	private JTextField textField_twosOut;
	private JTextField textField_fivesOut;
	private JTextField textField_tensOut;
	private JTextField textField_twentiesOut;
	private JTextField textField_fiftiesOut;
	private JTextField textField_hundredsOut;
	private JTextField textField_cashDrops;
	
	private JSpinner spinner_clubSelection;
	
	//Initial values
	private String clubSelected;

	
	/**
	 * UpdateLists method
	 */
	public void updateLists(List<Club> clubs, List<String> available, List<String> dispatched){
		this.activeClubs = clubs;
		this.availableFS = available;
		this.dispatchedFS = dispatched;
		
		//Remove, update, replace spinner_clubs
		remove(spinner_clubSelection);
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubSelection = new JSpinner(clubsModel);
		spinner_clubSelection.setBounds(147, 23, 252, 40);
		spinner_clubSelection.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubSelection);
		clubSelected = spinner_clubSelection.getValue().toString();
	}
	/**
	 *  Creates an ArrayList<String> from the activeClubs list
	 * 
	 */
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
	
	/**
	 * ClubSelected Spinner Listener
	 */
	private class ClubSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			clubSelected = spinner_clubSelection.getValue().toString();
		}
		
	}

	public Panel_CICO(String userName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		clientName = userName;
		output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		
		setLayout(null);
		
		TA_StartHere = new JTextArea();
		TA_StartHere.setBackground(SystemColor.control);
		TA_StartHere.setEditable(false);
		TA_StartHere.setText("START HERE ");
		TA_StartHere.setBounds(10, 73, 100, 22);
		add(TA_StartHere);
		TA_StartHere.setColumns(10);
		
		JTextArea txtrCashOut = new JTextArea();
		txtrCashOut.setBackground(SystemColor.control);
		txtrCashOut.setEditable(false);
		txtrCashOut.setText("Cash Out:");
		txtrCashOut.setBounds(10, 106, 100, 22);
		add(txtrCashOut);
		
		JTextArea txtrX = new JTextArea();
		txtrX.setBackground(SystemColor.control);
		txtrX.setEditable(false);
		txtrX.setText("$0.01 X");
		txtrX.setBounds(10, 139, 67, 22);
		add(txtrX);
		
		JTextArea txtrX_1 = new JTextArea();
		txtrX_1.setBackground(SystemColor.control);
		txtrX_1.setEditable(false);
		txtrX_1.setText("$0.05 X");
		txtrX_1.setBounds(10, 172, 67, 22);
		add(txtrX_1);
		
		JTextArea txtrX_2 = new JTextArea();
		txtrX_2.setBackground(SystemColor.control);
		txtrX_2.setEditable(false);
		txtrX_2.setText("$0.10 X");
		txtrX_2.setBounds(10, 205, 67, 22);
		add(txtrX_2);
		
		JTextArea txtrX_3 = new JTextArea();
		txtrX_3.setBackground(SystemColor.control);
		txtrX_3.setEditable(false);
		txtrX_3.setText("$0.25 X");
		txtrX_3.setBounds(10, 238, 67, 22);
		add(txtrX_3);
		
		JTextArea txtrX_4 = new JTextArea();
		txtrX_4.setBackground(SystemColor.control);
		txtrX_4.setEditable(false);
		txtrX_4.setText("$1    X");
		txtrX_4.setBounds(10, 271, 67, 22);
		add(txtrX_4);
		
		JTextArea txtrX_5 = new JTextArea();
		txtrX_5.setBackground(SystemColor.control);
		txtrX_5.setEditable(false);
		txtrX_5.setText("$2    X");
		txtrX_5.setBounds(10, 304, 67, 22);
		add(txtrX_5);
		
		JTextArea txtrX_6 = new JTextArea();
		txtrX_6.setBackground(SystemColor.control);
		txtrX_6.setEditable(false);
		txtrX_6.setText("$5    X");
		txtrX_6.setBounds(10, 337, 67, 22);
		add(txtrX_6);
		
		JTextArea txtrX_7 = new JTextArea();
		txtrX_7.setBackground(SystemColor.control);
		txtrX_7.setEditable(false);
		txtrX_7.setText("$10   X");
		txtrX_7.setBounds(10, 370, 67, 22);
		add(txtrX_7);
		
		JTextArea txtrX_8 = new JTextArea();
		txtrX_8.setBackground(SystemColor.control);
		txtrX_8.setEditable(false);
		txtrX_8.setText("$20   X");
		txtrX_8.setBounds(10, 403, 67, 22);
		add(txtrX_8);
		
		JTextArea txtrX_9 = new JTextArea();
		txtrX_9.setBackground(SystemColor.control);
		txtrX_9.setEditable(false);
		txtrX_9.setText("$50   X");
		txtrX_9.setBounds(10, 436, 67, 22);
		add(txtrX_9);
		
		JTextArea txtrX_10 = new JTextArea();
		txtrX_10.setBackground(SystemColor.control);
		txtrX_10.setEditable(false);
		txtrX_10.setText("$100  X");
		txtrX_10.setBounds(10, 469, 67, 22);
		add(txtrX_10);
		
		textField_penniesIn = new JTextField();
		textField_penniesIn.setBounds(87, 140, 41, 22);
		add(textField_penniesIn);
		textField_penniesIn.setColumns(10);
		
		textField_nickelsIn = new JTextField();
		textField_nickelsIn.setColumns(10);
		textField_nickelsIn.setBounds(87, 173, 41, 22);
		add(textField_nickelsIn);
		
		textField_dimesIn = new JTextField();
		textField_dimesIn.setColumns(10);
		textField_dimesIn.setBounds(87, 206, 41, 22);
		add(textField_dimesIn);
		
		textField_quartersIn = new JTextField();
		textField_quartersIn.setColumns(10);
		textField_quartersIn.setBounds(87, 239, 41, 22);
		add(textField_quartersIn);
		
		textField_dollarsIn = new JTextField();
		textField_dollarsIn.setColumns(10);
		textField_dollarsIn.setBounds(87, 272, 41, 22);
		add(textField_dollarsIn);
		
		textField_twosIn = new JTextField();
		textField_twosIn.setColumns(10);
		textField_twosIn.setBounds(87, 305, 41, 22);
		add(textField_twosIn);
		
		textField_fivesIn = new JTextField();
		textField_fivesIn.setColumns(10);
		textField_fivesIn.setBounds(87, 338, 41, 22);
		add(textField_fivesIn);
		
		textField_tensIn = new JTextField();
		textField_tensIn.setColumns(10);
		textField_tensIn.setBounds(87, 371, 41, 22);
		add(textField_tensIn);
		
		textField_twentiesIn = new JTextField();
		textField_twentiesIn.setColumns(10);
		textField_twentiesIn.setBounds(87, 404, 41, 22);
		add(textField_twentiesIn);
		
		textField_fiftiesIn = new JTextField();
		textField_fiftiesIn.setColumns(10);
		textField_fiftiesIn.setBounds(87, 437, 41, 22);
		add(textField_fiftiesIn);
		
		textField_hundredsIn = new JTextField();
		textField_hundredsIn.setColumns(10);
		textField_hundredsIn.setBounds(87, 470, 41, 22);
		add(textField_hundredsIn);
		
		JTextArea txtrStartTotal = new JTextArea();
		txtrStartTotal.setBackground(SystemColor.control);
		txtrStartTotal.setText("START =");
		txtrStartTotal.setLineWrap(true);
		txtrStartTotal.setEditable(false);
		txtrStartTotal.setBounds(10, 502, 67, 27);
		add(txtrStartTotal);
		
		JTextArea textArea_startTotal = new JTextArea();
		textArea_startTotal.setBackground(SystemColor.control);
		textArea_startTotal.setBounds(87, 503, 91, 27);
		add(textArea_startTotal);
		
		JTextArea txtrFinishHere = new JTextArea();
		txtrFinishHere.setText("FINISH HERE ");
		txtrFinishHere.setEditable(false);
		txtrFinishHere.setColumns(10);
		txtrFinishHere.setBackground(SystemColor.menu);
		txtrFinishHere.setBounds(263, 73, 100, 22);
		add(txtrFinishHere);
		
		JTextArea txtrCashIn = new JTextArea();
		txtrCashIn.setText("Cash In:");
		txtrCashIn.setEditable(false);
		txtrCashIn.setBackground(SystemColor.menu);
		txtrCashIn.setBounds(263, 106, 100, 22);
		add(txtrCashIn);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("$0.01 X");
		textArea_1.setEditable(false);
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setBounds(263, 139, 67, 22);
		add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setText("$0.05 X");
		textArea_2.setEditable(false);
		textArea_2.setBackground(SystemColor.menu);
		textArea_2.setBounds(263, 172, 67, 22);
		add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText("$0.10 X");
		textArea_3.setEditable(false);
		textArea_3.setBackground(SystemColor.menu);
		textArea_3.setBounds(263, 205, 67, 22);
		add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setText("$0.25 X");
		textArea_4.setEditable(false);
		textArea_4.setBackground(SystemColor.menu);
		textArea_4.setBounds(263, 238, 67, 22);
		add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setText("$1    X");
		textArea_5.setEditable(false);
		textArea_5.setBackground(SystemColor.menu);
		textArea_5.setBounds(263, 271, 67, 22);
		add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setText("$2    X");
		textArea_6.setEditable(false);
		textArea_6.setBackground(SystemColor.menu);
		textArea_6.setBounds(263, 304, 67, 22);
		add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setText("$5    X");
		textArea_7.setEditable(false);
		textArea_7.setBackground(SystemColor.menu);
		textArea_7.setBounds(263, 337, 67, 22);
		add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setText("$10   X");
		textArea_8.setEditable(false);
		textArea_8.setBackground(SystemColor.menu);
		textArea_8.setBounds(263, 370, 67, 22);
		add(textArea_8);
		
		JTextArea textArea_9 = new JTextArea();
		textArea_9.setText("$20   X");
		textArea_9.setEditable(false);
		textArea_9.setBackground(SystemColor.menu);
		textArea_9.setBounds(263, 403, 67, 22);
		add(textArea_9);
		
		JTextArea textArea_10 = new JTextArea();
		textArea_10.setText("$50   X");
		textArea_10.setEditable(false);
		textArea_10.setBackground(SystemColor.menu);
		textArea_10.setBounds(263, 436, 67, 22);
		add(textArea_10);
		
		JTextArea textArea_11 = new JTextArea();
		textArea_11.setText("$100  X");
		textArea_11.setEditable(false);
		textArea_11.setBackground(SystemColor.menu);
		textArea_11.setBounds(263, 469, 67, 22);
		add(textArea_11);
		
		JTextArea txtrEnd = new JTextArea();
		txtrEnd.setText("END  =");
		txtrEnd.setLineWrap(true);
		txtrEnd.setEditable(false);
		txtrEnd.setBackground(SystemColor.menu);
		txtrEnd.setBounds(263, 502, 67, 27);
		add(txtrEnd);
		
		JButton btnNewButton = new JButton("CONFIRM INITIAL CASH DROP");
		btnNewButton.setBounds(10, 536, 252, 40);
		add(btnNewButton);
		
		JTextArea textArea_12 = new JTextArea();
		textArea_12.setBackground(SystemColor.control);
		textArea_12.setEditable(false);
		textArea_12.setText("=");
		textArea_12.setBounds(138, 139, 12, 22);
		add(textArea_12);
		
		JTextArea textArea_13 = new JTextArea();
		textArea_13.setText("=");
		textArea_13.setEditable(false);
		textArea_13.setBackground(SystemColor.menu);
		textArea_13.setBounds(138, 172, 12, 22);
		add(textArea_13);
		
		JTextArea textArea_14 = new JTextArea();
		textArea_14.setText("=");
		textArea_14.setEditable(false);
		textArea_14.setBackground(SystemColor.menu);
		textArea_14.setBounds(138, 205, 12, 22);
		add(textArea_14);
		
		JTextArea textArea_15 = new JTextArea();
		textArea_15.setText("=");
		textArea_15.setEditable(false);
		textArea_15.setBackground(SystemColor.menu);
		textArea_15.setBounds(138, 238, 12, 22);
		add(textArea_15);
		
		JTextArea textArea_16 = new JTextArea();
		textArea_16.setText("=");
		textArea_16.setEditable(false);
		textArea_16.setBackground(SystemColor.menu);
		textArea_16.setBounds(138, 271, 12, 22);
		add(textArea_16);
		
		JTextArea textArea_17 = new JTextArea();
		textArea_17.setText("=");
		textArea_17.setEditable(false);
		textArea_17.setBackground(SystemColor.menu);
		textArea_17.setBounds(138, 304, 12, 22);
		add(textArea_17);
		
		JTextArea textArea_18 = new JTextArea();
		textArea_18.setText("=");
		textArea_18.setEditable(false);
		textArea_18.setBackground(SystemColor.menu);
		textArea_18.setBounds(138, 337, 12, 22);
		add(textArea_18);
		
		JTextArea textArea_19 = new JTextArea();
		textArea_19.setText("=");
		textArea_19.setEditable(false);
		textArea_19.setBackground(SystemColor.menu);
		textArea_19.setBounds(138, 370, 12, 22);
		add(textArea_19);
		
		JTextArea textArea_20 = new JTextArea();
		textArea_20.setText("=");
		textArea_20.setEditable(false);
		textArea_20.setBackground(SystemColor.menu);
		textArea_20.setBounds(138, 403, 12, 22);
		add(textArea_20);
		
		JTextArea textArea_21 = new JTextArea();
		textArea_21.setText("=");
		textArea_21.setEditable(false);
		textArea_21.setBackground(SystemColor.menu);
		textArea_21.setBounds(138, 436, 12, 22);
		add(textArea_21);
		
		JTextArea textArea_22 = new JTextArea();
		textArea_22.setText("=");
		textArea_22.setEditable(false);
		textArea_22.setBackground(SystemColor.menu);
		textArea_22.setBounds(138, 469, 12, 22);
		add(textArea_22);
		
		JTextArea textArea_penniesIn = new JTextArea();
		textArea_penniesIn.setText("$0.00");
		textArea_penniesIn.setEditable(false);
		textArea_penniesIn.setBackground(SystemColor.menu);
		textArea_penniesIn.setBounds(160, 139, 67, 22);
		add(textArea_penniesIn);
		
		JTextArea textArea_nickelsIn = new JTextArea();
		textArea_nickelsIn.setText("$0.00");
		textArea_nickelsIn.setEditable(false);
		textArea_nickelsIn.setBackground(SystemColor.menu);
		textArea_nickelsIn.setBounds(160, 172, 67, 22);
		add(textArea_nickelsIn);
		
		JTextArea textArea_dimesIn = new JTextArea();
		textArea_dimesIn.setText("$0.00");
		textArea_dimesIn.setEditable(false);
		textArea_dimesIn.setBackground(SystemColor.menu);
		textArea_dimesIn.setBounds(160, 205, 67, 22);
		add(textArea_dimesIn);
		
		JTextArea textArea_quartersIn = new JTextArea();
		textArea_quartersIn.setText("$0.00");
		textArea_quartersIn.setEditable(false);
		textArea_quartersIn.setBackground(SystemColor.menu);
		textArea_quartersIn.setBounds(160, 238, 67, 22);
		add(textArea_quartersIn);
		
		JTextArea textArea_dollarsIn = new JTextArea();
		textArea_dollarsIn.setText("$0.00");
		textArea_dollarsIn.setEditable(false);
		textArea_dollarsIn.setBackground(SystemColor.menu);
		textArea_dollarsIn.setBounds(160, 271, 67, 22);
		add(textArea_dollarsIn);
		
		JTextArea textArea_twosIn = new JTextArea();
		textArea_twosIn.setText("$0.00");
		textArea_twosIn.setEditable(false);
		textArea_twosIn.setBackground(SystemColor.menu);
		textArea_twosIn.setBounds(160, 304, 67, 22);
		add(textArea_twosIn);
		
		JTextArea textArea_fivesIn = new JTextArea();
		textArea_fivesIn.setText("$0.00");
		textArea_fivesIn.setEditable(false);
		textArea_fivesIn.setBackground(SystemColor.menu);
		textArea_fivesIn.setBounds(160, 337, 67, 22);
		add(textArea_fivesIn);
		
		JTextArea textArea_tensIn = new JTextArea();
		textArea_tensIn.setText("$0.00");
		textArea_tensIn.setEditable(false);
		textArea_tensIn.setBackground(SystemColor.menu);
		textArea_tensIn.setBounds(160, 370, 67, 22);
		add(textArea_tensIn);
		
		JTextArea textArea_twentiesIn = new JTextArea();
		textArea_twentiesIn.setText("$0.00");
		textArea_twentiesIn.setEditable(false);
		textArea_twentiesIn.setBackground(SystemColor.menu);
		textArea_twentiesIn.setBounds(160, 403, 67, 22);
		add(textArea_twentiesIn);
		
		JTextArea textArea_fiftiesIn = new JTextArea();
		textArea_fiftiesIn.setText("$0.00");
		textArea_fiftiesIn.setEditable(false);
		textArea_fiftiesIn.setBackground(SystemColor.menu);
		textArea_fiftiesIn.setBounds(160, 436, 67, 22);
		add(textArea_fiftiesIn);
		
		JTextArea textArea_hundredsIn = new JTextArea();
		textArea_hundredsIn.setText("$0.00");
		textArea_hundredsIn.setEditable(false);
		textArea_hundredsIn.setBackground(SystemColor.menu);
		textArea_hundredsIn.setBounds(160, 469, 67, 22);
		add(textArea_hundredsIn);
		
		textField_penniesOut = new JTextField();
		textField_penniesOut.setColumns(10);
		textField_penniesOut.setBounds(336, 139, 41, 22);
		add(textField_penniesOut);
		
		textField_nickelsOut = new JTextField();
		textField_nickelsOut.setColumns(10);
		textField_nickelsOut.setBounds(336, 172, 41, 22);
		add(textField_nickelsOut);
		
		textField_dimesOut = new JTextField();
		textField_dimesOut.setColumns(10);
		textField_dimesOut.setBounds(336, 205, 41, 22);
		add(textField_dimesOut);
		
		textField_quartersOut = new JTextField();
		textField_quartersOut.setColumns(10);
		textField_quartersOut.setBounds(336, 238, 41, 22);
		add(textField_quartersOut);
		
		textField_dollarsOut = new JTextField();
		textField_dollarsOut.setColumns(10);
		textField_dollarsOut.setBounds(336, 271, 41, 22);
		add(textField_dollarsOut);
		
		textField_twosOut = new JTextField();
		textField_twosOut.setColumns(10);
		textField_twosOut.setBounds(336, 304, 41, 22);
		add(textField_twosOut);
		
		textField_fivesOut = new JTextField();
		textField_fivesOut.setColumns(10);
		textField_fivesOut.setBounds(336, 337, 41, 22);
		add(textField_fivesOut);
		
		textField_tensOut = new JTextField();
		textField_tensOut.setColumns(10);
		textField_tensOut.setBounds(336, 370, 41, 22);
		add(textField_tensOut);
		
		textField_twentiesOut = new JTextField();
		textField_twentiesOut.setColumns(10);
		textField_twentiesOut.setBounds(336, 403, 41, 22);
		add(textField_twentiesOut);
		
		textField_fiftiesOut = new JTextField();
		textField_fiftiesOut.setColumns(10);
		textField_fiftiesOut.setBounds(336, 436, 41, 22);
		add(textField_fiftiesOut);
		
		textField_hundredsOut = new JTextField();
		textField_hundredsOut.setColumns(10);
		textField_hundredsOut.setBounds(336, 469, 41, 22);
		add(textField_hundredsOut);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("=");
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.menu);
		textArea.setBounds(387, 139, 12, 22);
		add(textArea);
		
		JTextArea textArea_23 = new JTextArea();
		textArea_23.setText("=");
		textArea_23.setEditable(false);
		textArea_23.setBackground(SystemColor.menu);
		textArea_23.setBounds(387, 172, 12, 22);
		add(textArea_23);
		
		JTextArea textArea_24 = new JTextArea();
		textArea_24.setText("=");
		textArea_24.setEditable(false);
		textArea_24.setBackground(SystemColor.menu);
		textArea_24.setBounds(387, 205, 12, 22);
		add(textArea_24);
		
		JTextArea textArea_25 = new JTextArea();
		textArea_25.setText("=");
		textArea_25.setEditable(false);
		textArea_25.setBackground(SystemColor.menu);
		textArea_25.setBounds(387, 238, 12, 22);
		add(textArea_25);
		
		JTextArea textArea_26 = new JTextArea();
		textArea_26.setText("=");
		textArea_26.setEditable(false);
		textArea_26.setBackground(SystemColor.menu);
		textArea_26.setBounds(387, 271, 12, 22);
		add(textArea_26);
		
		JTextArea textArea_27 = new JTextArea();
		textArea_27.setText("=");
		textArea_27.setEditable(false);
		textArea_27.setBackground(SystemColor.menu);
		textArea_27.setBounds(387, 304, 12, 22);
		add(textArea_27);
		
		JTextArea textArea_28 = new JTextArea();
		textArea_28.setText("=");
		textArea_28.setEditable(false);
		textArea_28.setBackground(SystemColor.menu);
		textArea_28.setBounds(387, 337, 12, 22);
		add(textArea_28);
		
		JTextArea textArea_29 = new JTextArea();
		textArea_29.setText("=");
		textArea_29.setEditable(false);
		textArea_29.setBackground(SystemColor.menu);
		textArea_29.setBounds(387, 369, 12, 22);
		add(textArea_29);
		
		JTextArea textArea_30 = new JTextArea();
		textArea_30.setText("=");
		textArea_30.setEditable(false);
		textArea_30.setBackground(SystemColor.menu);
		textArea_30.setBounds(387, 403, 12, 22);
		add(textArea_30);
		
		JTextArea textArea_31 = new JTextArea();
		textArea_31.setText("=");
		textArea_31.setEditable(false);
		textArea_31.setBackground(SystemColor.menu);
		textArea_31.setBounds(387, 436, 12, 22);
		add(textArea_31);
		
		JTextArea textArea_32 = new JTextArea();
		textArea_32.setText("=");
		textArea_32.setEditable(false);
		textArea_32.setBackground(SystemColor.menu);
		textArea_32.setBounds(387, 469, 12, 22);
		add(textArea_32);
		
		JTextArea textArea_penniesOut = new JTextArea();
		textArea_penniesOut.setText("$0.00");
		textArea_penniesOut.setEditable(false);
		textArea_penniesOut.setBackground(SystemColor.menu);
		textArea_penniesOut.setBounds(409, 139, 67, 22);
		add(textArea_penniesOut);
		
		JTextArea textArea_nickelsOut = new JTextArea();
		textArea_nickelsOut.setText("$0.00");
		textArea_nickelsOut.setEditable(false);
		textArea_nickelsOut.setBackground(SystemColor.menu);
		textArea_nickelsOut.setBounds(409, 173, 67, 22);
		add(textArea_nickelsOut);
		
		JTextArea textArea_dimesOut = new JTextArea();
		textArea_dimesOut.setText("$0.00");
		textArea_dimesOut.setEditable(false);
		textArea_dimesOut.setBackground(SystemColor.menu);
		textArea_dimesOut.setBounds(409, 205, 67, 22);
		add(textArea_dimesOut);
		
		JTextArea textArea_quartersOut = new JTextArea();
		textArea_quartersOut.setText("$0.00");
		textArea_quartersOut.setEditable(false);
		textArea_quartersOut.setBackground(SystemColor.menu);
		textArea_quartersOut.setBounds(409, 237, 67, 22);
		add(textArea_quartersOut);
		
		JTextArea textArea_onesOut = new JTextArea();
		textArea_onesOut.setText("$0.00");
		textArea_onesOut.setEditable(false);
		textArea_onesOut.setBackground(SystemColor.menu);
		textArea_onesOut.setBounds(409, 271, 67, 22);
		add(textArea_onesOut);
		
		JTextArea textArea_twosOut = new JTextArea();
		textArea_twosOut.setText("$0.00");
		textArea_twosOut.setEditable(false);
		textArea_twosOut.setBackground(SystemColor.menu);
		textArea_twosOut.setBounds(409, 304, 67, 22);
		add(textArea_twosOut);
		
		JTextArea textArea_fivesOut = new JTextArea();
		textArea_fivesOut.setText("$0.00");
		textArea_fivesOut.setEditable(false);
		textArea_fivesOut.setBackground(SystemColor.menu);
		textArea_fivesOut.setBounds(409, 337, 67, 22);
		add(textArea_fivesOut);
		
		JTextArea textArea_tensOut = new JTextArea();
		textArea_tensOut.setText("$0.00");
		textArea_tensOut.setEditable(false);
		textArea_tensOut.setBackground(SystemColor.menu);
		textArea_tensOut.setBounds(409, 370, 67, 22);
		add(textArea_tensOut);
		
		JTextArea textArea_twentiesOut = new JTextArea();
		textArea_twentiesOut.setText("$0.00");
		textArea_twentiesOut.setEditable(false);
		textArea_twentiesOut.setBackground(SystemColor.menu);
		textArea_twentiesOut.setBounds(409, 403, 67, 22);
		add(textArea_twentiesOut);
		
		JTextArea textArea_fiftiesOut = new JTextArea();
		textArea_fiftiesOut.setText("$0.00");
		textArea_fiftiesOut.setEditable(false);
		textArea_fiftiesOut.setBackground(SystemColor.menu);
		textArea_fiftiesOut.setBounds(409, 436, 67, 22);
		add(textArea_fiftiesOut);
		
		JTextArea textArea_hundredsOut = new JTextArea();
		textArea_hundredsOut.setText("$0.00");
		textArea_hundredsOut.setEditable(false);
		textArea_hundredsOut.setBackground(SystemColor.menu);
		textArea_hundredsOut.setBounds(409, 469, 67, 22);
		add(textArea_hundredsOut);
		
		JTextArea textArea_endTotal = new JTextArea();
		textArea_endTotal.setBackground(SystemColor.menu);
		textArea_endTotal.setBounds(324, 502, 91, 27);
		add(textArea_endTotal);
		
		spinner_clubSelection = new JSpinner();
		
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubSelection = new JSpinner(clubsModel);
		spinner_clubSelection.setBounds(147, 23, 252, 40);
		spinner_clubSelection.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubSelection);
		clubSelected = spinner_clubSelection.getValue().toString();
		
		JTextArea txtrBooth = new JTextArea();
		txtrBooth.setBackground(SystemColor.control);
		txtrBooth.setEditable(false);
		txtrBooth.setText("         BOOTH:");
		txtrBooth.setBounds(10, 31, 121, 27);
		add(txtrBooth);
		
		textField_cashDrops = new JTextField();
		textField_cashDrops.setColumns(10);
		textField_cashDrops.setBounds(492, 403, 41, 22);
		add(textField_cashDrops);
		
		JTextArea txtrX_11 = new JTextArea();
		txtrX_11.setText("X $500 +");
		txtrX_11.setEditable(false);
		txtrX_11.setBackground(SystemColor.menu);
		txtrX_11.setBounds(543, 403, 75, 22);
		add(txtrX_11);
		
		JTextArea textArea_endTotalCalc = new JTextArea();
		textArea_endTotalCalc.setBackground(SystemColor.menu);
		textArea_endTotalCalc.setBounds(628, 403, 91, 27);
		add(textArea_endTotalCalc);
		
		JTextArea txtrOfCash = new JTextArea();
		txtrOfCash.setText("  Number of   Cash Drops");
		txtrOfCash.setLineWrap(true);
		txtrOfCash.setEditable(false);
		txtrOfCash.setBackground(SystemColor.menu);
		txtrOfCash.setBounds(465, 355, 114, 40);
		add(txtrOfCash);
		
		JTextArea txtrEndTotal = new JTextArea();
		txtrEndTotal.setText("End Total");
		txtrEndTotal.setLineWrap(true);
		txtrEndTotal.setEditable(false);
		txtrEndTotal.setBackground(SystemColor.menu);
		txtrEndTotal.setBounds(636, 370, 95, 27);
		add(txtrEndTotal);
		
		JTextArea textArea_34 = new JTextArea();
		textArea_34.setText("=");
		textArea_34.setEditable(false);
		textArea_34.setBackground(SystemColor.menu);
		textArea_34.setBounds(594, 469, 12, 22);
		add(textArea_34);
		
		JTextArea textArea_finalTotal = new JTextArea();
		textArea_finalTotal.setBackground(SystemColor.menu);
		textArea_finalTotal.setBounds(616, 469, 103, 27);
		add(textArea_finalTotal);
		
		JTextArea txtrFinalTotal = new JTextArea();
		txtrFinalTotal.setText("Final Total");
		txtrFinalTotal.setLineWrap(true);
		txtrFinalTotal.setEditable(false);
		txtrFinalTotal.setBackground(SystemColor.menu);
		txtrFinalTotal.setBounds(616, 436, 95, 27);
		add(txtrFinalTotal);
	}
	
	/**
	 * This method is called by a UpdateClientCommand executed on
	 * a Client
	 * 
	 * @param objects	the PaintObjects in the world
	 */
	public void update(){
		repaint();
	}
}
