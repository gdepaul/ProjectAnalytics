package View;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Club;
import model.dispatch.InitialCashDrop;

import javax.swing.JLabel;

public class Panel_CICO extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778947721922456207L;
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
	
	private JTextArea textArea_cashDrops;
	private JTextArea textArea_startTotal;
	private JTextArea textArea_endTotal;
	private JTextArea textArea_endTotalCalc;
	private JTextArea textArea_cashDropsBy800;
	private JTextArea textArea_finalTotal;
	private JTextArea textArea_penniesIn;
	private JTextArea textArea_nickelsIn;
	private JTextArea textArea_dimesIn;
	private JTextArea textArea_quartersIn;
	private JTextArea textArea_dollarsIn;
	private JTextArea textArea_twosIn;
	private JTextArea textArea_fivesIn;
	private JTextArea textArea_tensIn;
	private JTextArea textArea_twentiesIn;
	private JTextArea textArea_fiftiesIn;
	private JTextArea textArea_hundredsIn;
	private JTextArea textArea_penniesOut;
	private JTextArea textArea_nickelsOut;
	private JTextArea textArea_dimesOut;
	private JTextArea textArea_quartersOut;
	private JTextArea textArea_dollarsOut;
	private JTextArea textArea_twosOut;
	private JTextArea textArea_fivesOut;
	private JTextArea textArea_tensOut;
	private JTextArea textArea_twentiesOut;
	private JTextArea textArea_fiftiesOut;
	private JTextArea textArea_hundredsOut;
	private JTextArea textArea_issuedTickets;
	private JTextArea textArea_issuedWristbands;
	private JTextArea textArea_expectedRevenue;
	private JTextArea textArea_soldWristbands;
	private JTextArea textArea_soldTickets;
	
	
	
	private JSpinner spinner_clubSelection;
	
	JButton btn_initialCashDrop;
	
	//Listeners
	private StartValsChangedListener startValsChangedListener;
	private EndValsChangedListener endValsChangedListener;
	private RevenueChangedListener revenueChangedListener;
	private ConfirmInitialDropListener confirmInitialDropListener;
	
	//Initial values
	private String clubSelected;
	private Club actualClub;
	private JTextField textField_fullSheetsUnsold;
	private JTextField textField_halfSheetsUnsold;
	private JTextField textField_singleTicketsUnsold;
	private JTextField textField_wristbandsUnsold;
	
	/**
	 * Listener for the initial cash drop button
	 */
	public class ConfirmInitialDropListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String startTotal = textArea_startTotal.getText().substring(1);
				output.writeObject(new InitialCashDrop(clientName, actualClub.getClubName(), (float) Float.parseFloat(startTotal)));
				JOptionPane.showMessageDialog(getParent(), "Initial Cash Drop logged for " + actualClub.getClubName()+ " for " + Float.parseFloat(startTotal));
			} catch (NumberFormatException | IOException e) {
				JOptionPane.showMessageDialog(getParent(), "Number format exception from ConfirmInitialDropListener");
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Revenue changed listener, update when values changed for tickets, wristbands
	 */
	private class RevenueChangedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int issuedTickets = Integer.parseInt(textArea_issuedTickets.getText().toString());
			int issuedWristbands = Integer.parseInt(textArea_issuedWristbands.getText().toString());
			int unsoldTicketsTotal = 0;
			int unsoldWristbandsTotal = 0;
			float revenueTotal = 0;
			
			if(textField_fullSheetsUnsold.getText().compareTo("")==0){
		 		unsoldTicketsTotal += 0;
		 	}else{
				try { 
					unsoldTicketsTotal += Integer.parseInt(textField_fullSheetsUnsold.getText().toString()) * 40;
							;
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of full sheets!");
			    }
		 	}
			
			if(textField_halfSheetsUnsold.getText().compareTo("")==0){
		 		unsoldTicketsTotal += 0;
		 	}else{
				try { 
					unsoldTicketsTotal += Integer.parseInt(textField_halfSheetsUnsold.getText().toString()) * 20;
							;
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of half sheets!");
			    }
		 	}
			
			if(textField_singleTicketsUnsold.getText().compareTo("")==0){
		 		unsoldTicketsTotal += 0;
		 	}else{
				try { 
					unsoldTicketsTotal += Integer.parseInt(textField_singleTicketsUnsold.getText().toString());
							;
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single tickets!");
			    }
		 	}
			
			if(textField_wristbandsUnsold.getText().compareTo("")==0){
		 		unsoldWristbandsTotal += 0;
		 	}else{
				try { 
					unsoldWristbandsTotal += Integer.parseInt(textField_wristbandsUnsold.getText().toString());
							
							;
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of wristbands!");
			    }
		 	}
			
			
			textArea_soldTickets.setText("" + (issuedTickets - unsoldTicketsTotal));
			textArea_soldWristbands.setText("" + (issuedWristbands - unsoldWristbandsTotal));
			revenueTotal = (float) ((issuedTickets - unsoldTicketsTotal)*.5 + (issuedWristbands - unsoldWristbandsTotal)*25);
			textArea_expectedRevenue.setText("$" + formatDecimal(revenueTotal));
		}
		
	}
	
	/**
	 * Start Values Changed Listener, automatically updates totals when values entered
	 */
	private class StartValsChangedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			float startTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_penniesIn.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesIn.getText())*.01); 
						startTotal += penniesValue;
				        textArea_penniesIn.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_nickelsIn.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsIn.getText())*.05); 
						startTotal += nickelsValue;
				        textArea_nickelsIn.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dimesIn.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesIn.getText())*.10); 
						startTotal += dimesValue;
				        textArea_dimesIn.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_quartersIn.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersIn.getText())*.25); 
						startTotal += quartersValue;
				        textArea_quartersIn.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dollarsIn.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsIn.getText())*1.00); 
						startTotal += dollarsValue;
				        textArea_dollarsIn.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twosIn.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosIn.getText())*2.00); 
						startTotal += twosValue;
				        textArea_twosIn.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fivesIn.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesIn.getText())*5.00); 
						startTotal += fivesValue;
				        textArea_fivesIn.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_tensIn.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensIn.getText())*10.00); 
						startTotal += tensValue;
				        textArea_tensIn.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twentiesIn.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesIn.getText())*20.00); 
						startTotal += twentiesValue;
				        textArea_twentiesIn.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fiftiesIn.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesIn.getText())*50.00); 
						startTotal += fiftiesValue;
				        textArea_fiftiesIn.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_hundredsIn.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsIn.getText())*100.00); 
						startTotal += hundredsValue;
				        textArea_hundredsIn.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 	
			textArea_startTotal.setText("$" + formatDecimal(startTotal));
		}
		
	}
	public String formatDecimal(float number) {
		  float epsilon = 0.004f; // 4 tenths of a cent
		  if (Math.abs(Math.round(number) - number) < epsilon) {
		     return String.format("%10.0f", number);
		  } else {
		     return String.format("%10.2f", number);
		  }
	}
	
	/**
	 * End Values Changed Listener, automatically updates totals when values entered
	 */
	private class EndValsChangedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			float endTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_penniesOut.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesOut.getText())*.01); 
				        endTotal += penniesValue;
				        textArea_penniesOut.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_nickelsOut.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsOut.getText())*.05); 
				        endTotal += nickelsValue;
				        textArea_nickelsOut.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dimesOut.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesOut.getText())*.10); 
				        endTotal += dimesValue;
				        textArea_dimesOut.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_quartersOut.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersOut.getText())*.25); 
				        endTotal += quartersValue;
				        textArea_quartersOut.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dollarsOut.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsOut.getText())*1.00); 
				        endTotal += dollarsValue;
				        textArea_dollarsOut.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twosOut.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosOut.getText())*2.00); 
				        endTotal += twosValue;
				        textArea_twosOut.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fivesOut.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesOut.getText())*5.00); 
				        endTotal += fivesValue;
				        textArea_fivesOut.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_tensOut.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensOut.getText())*10.00); 
				        endTotal += tensValue;
				        textArea_tensOut.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twentiesOut.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesOut.getText())*20.00); 
				        endTotal += twentiesValue;
				        textArea_twentiesOut.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fiftiesOut.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesOut.getText())*50.00); 
				        endTotal += fiftiesValue;
				        textArea_fiftiesOut.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_hundredsOut.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsOut.getText())*100.00); 
				        endTotal += hundredsValue;
				        textArea_hundredsOut.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 	
			textArea_endTotal.setText("$" + formatDecimal(endTotal));
			textArea_endTotalCalc.setText("$" + formatDecimal(endTotal));
			
			if (textArea_endTotalCalc.getText().compareTo("")!=0){
				textArea_finalTotal.setText("$"+
							(formatDecimal(actualClub.getCashdrops()*800
									+endTotal)	
							));
			}else{
				textArea_finalTotal.setText(""+
						(formatDecimal(actualClub.getCashdrops()*800)));
			}
			
		}
	}
	
	/**
	 * UpdateLists method
	 */
	public void UpdateLists(List<Club> clubs, List<String> available, List<String> dispatched){
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
		
		if (clubsArray.contains(clubSelected)){
			spinner_clubSelection.setValue(clubSelected);
			
			//Update tickets value
			textArea_issuedTickets.setText("" + (actualClub.getFullsheets()*40 + actualClub.getHalfsheets()*20));
			
			//Update 
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			
			// ...if it's there, show that value as startTotal
			textArea_startTotal.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			//Update textAreas
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
		}else{
			if (clubSelected.compareTo("(No clubs!)")!=0){
				JOptionPane.showMessageDialog(getParent(), "The cashier you were working on (" + clubSelected + ") was removed from the active list!");
			}
			clubSelected = spinner_clubSelection.getValue().toString();
			
			actualClub = findActualClub(clubSelected);
			clearFields();
		}
		
		
		//if the club already has an initial cashdrop, turn off button and text fields...
		
		if (actualClub.getInitialCashDrop() !=0.0){
			btn_initialCashDrop.setEnabled(false);
			btn_initialCashDrop.setText("INITIAL CASH DROP DELIVERED!");
			textField_penniesIn.setEnabled(false);
			textField_nickelsIn.setEnabled(false);
			textField_dimesIn.setEnabled(false);
			textField_quartersIn.setEnabled(false);
			textField_dollarsIn.setEnabled(false);
			textField_twosIn.setEnabled(false);
			textField_fivesIn.setEnabled(false);
			textField_tensIn.setEnabled(false);
			textField_twentiesIn.setEnabled(false);
			textField_fiftiesIn.setEnabled(false);
			textField_hundredsIn.setEnabled(false);
			
		} else{ // else, make sure, they're on...
			btn_initialCashDrop.setEnabled(true);
			btn_initialCashDrop.setText("CONFIRM INITIAL CASH DROP");
			textField_penniesIn.setEnabled(true);
			textField_nickelsIn.setEnabled(true);
			textField_dimesIn.setEnabled(true);
			textField_quartersIn.setEnabled(true);
			textField_dollarsIn.setEnabled(true);
			textField_twosIn.setEnabled(true);
			textField_fivesIn.setEnabled(true);
			textField_tensIn.setEnabled(true);
			textField_twentiesIn.setEnabled(true);
			textField_fiftiesIn.setEnabled(true);
			textField_hundredsIn.setEnabled(true);
		}
		this.repaint();
	}
	private Club findActualClub(String clubSelected2) {
		for(Club club : activeClubs){
			if (clubSelected.compareTo(club.getClubName())==0){
				return club;
			}
		}
		return null;
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
			clearFields();
			clubSelected = spinner_clubSelection.getValue().toString();
			actualClub = findActualClub(clubSelected);
			//if the club already has an initial cashdrop, turn off button and text fields...
			
//			JOptionPane.showMessageDialog(getParent(), "SpinnerChanged!\n"+
//					"actualClub = " + actualClub.getClubName() + "\n"+
//					"Initial Cash Drop = " + actualClub.getInitialCashDrop());
			
			if (actualClub.getInitialCashDrop()!=0.0){
				btn_initialCashDrop.setEnabled(false);
				btn_initialCashDrop.setText("INITIAL CASH DROP DELIVERED!");
				textField_penniesIn.setEnabled(false);
				textField_nickelsIn.setEnabled(false);
				textField_dimesIn.setEnabled(false);
				textField_quartersIn.setEnabled(false);
				textField_dollarsIn.setEnabled(false);
				textField_twosIn.setEnabled(false);
				textField_fivesIn.setEnabled(false);
				textField_tensIn.setEnabled(false);
				textField_twentiesIn.setEnabled(false);
				textField_fiftiesIn.setEnabled(false);
				textField_hundredsIn.setEnabled(false);
				
			} else{ // else, make sure, they're on...
				btn_initialCashDrop.setEnabled(true);
				btn_initialCashDrop.setText("CONFIRM INITIAL CASH DROP");
				textField_penniesIn.setEnabled(true);
				textField_nickelsIn.setEnabled(true);
				textField_dimesIn.setEnabled(true);
				textField_quartersIn.setEnabled(true);
				textField_dollarsIn.setEnabled(true);
				textField_twosIn.setEnabled(true);
				textField_fivesIn.setEnabled(true);
				textField_tensIn.setEnabled(true);
				textField_twentiesIn.setEnabled(true);
				textField_fiftiesIn.setEnabled(true);
				textField_hundredsIn.setEnabled(true);
			}
			
			// ...if it's there, show that value as startTotal
			textArea_startTotal.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			//Update textAreas
			
			//Update tickets value
			textArea_issuedTickets.setText("" + (actualClub.getFullsheets()*40 + actualClub.getHalfsheets()*20));
			
			//Update 
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			
			//Update cashdrop stuff
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			if (textArea_endTotalCalc.getText().compareTo("")!=0){
				textArea_finalTotal.setText(
							(formatDecimal(actualClub.getCashdrops()*800
									+Float.parseFloat(textArea_endTotalCalc.getText().substring(0))
							)));
			}else{
				textArea_finalTotal.setText(
						(formatDecimal(actualClub.getCashdrops()*800)));
			}
			
		}
	}
	/**
	 * This clears all the fields. Should run when the cashier changes.
	 */
	private void clearFields() {
		textField_penniesIn.setText("");
		textField_penniesOut.setText("");
		textField_nickelsIn.setText("");
		textField_nickelsOut.setText("");
		textField_dimesIn.setText("");
		textField_dimesOut.setText("");
		textField_quartersIn.setText("");
		textField_quartersOut.setText("");
		textField_dollarsIn.setText("");
		textField_dollarsOut.setText("");
		textField_twosIn.setText("");
		textField_twosOut.setText("");
		textField_fivesIn.setText("");
		textField_fivesOut.setText("");
		textField_tensIn.setText("");
		textField_tensOut.setText("");
		textField_twentiesIn.setText("");
		textField_twentiesOut.setText("");
		textField_fiftiesIn.setText("");
		textField_fiftiesOut.setText("");
		textField_hundredsIn.setText("");
		textField_hundredsOut.setText("");
		textArea_finalTotal.setText("");
		textArea_endTotal.setText("");
		textArea_endTotalCalc.setText("");
	}

	public Panel_CICO(String userName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		clientName = userName;
		output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		
		startValsChangedListener = new StartValsChangedListener();
		endValsChangedListener = new EndValsChangedListener();
		confirmInitialDropListener = new ConfirmInitialDropListener();
		revenueChangedListener = new RevenueChangedListener();
		
		setLayout(null);
		
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
		textField_penniesIn.addActionListener(startValsChangedListener);
		add(textField_penniesIn);
		textField_penniesIn.setColumns(10);
		
		textField_nickelsIn = new JTextField();
		textField_nickelsIn.setColumns(10);
		textField_nickelsIn.addActionListener(startValsChangedListener);
		textField_nickelsIn.setBounds(87, 173, 41, 22);
		add(textField_nickelsIn);
		
		textField_dimesIn = new JTextField();
		textField_dimesIn.setColumns(10);
		textField_dimesIn.setBounds(87, 206, 41, 22);
		textField_dimesIn.addActionListener(startValsChangedListener);
		add(textField_dimesIn);
		
		textField_quartersIn = new JTextField();
		textField_quartersIn.setColumns(10);
		textField_quartersIn.setBounds(87, 239, 41, 22);
		textField_quartersIn.addActionListener(startValsChangedListener);
		add(textField_quartersIn);
		
		textField_dollarsIn = new JTextField();
		textField_dollarsIn.setColumns(10);
		textField_dollarsIn.setBounds(87, 272, 41, 22);
		textField_dollarsIn.addActionListener(startValsChangedListener);
		add(textField_dollarsIn);
		
		textField_twosIn = new JTextField();
		textField_twosIn.setColumns(10);
		textField_twosIn.setBounds(87, 305, 41, 22);
		textField_twosIn.addActionListener(startValsChangedListener);
		add(textField_twosIn);
		
		textField_fivesIn = new JTextField();
		textField_fivesIn.setColumns(10);
		textField_fivesIn.setBounds(87, 338, 41, 22);
		textField_fivesIn.addActionListener(startValsChangedListener);
		add(textField_fivesIn);
		
		textField_tensIn = new JTextField();
		textField_tensIn.setColumns(10);
		textField_tensIn.setBounds(87, 371, 41, 22);
		textField_tensIn.addActionListener(startValsChangedListener);
		add(textField_tensIn);
		
		textField_twentiesIn = new JTextField();
		textField_twentiesIn.setColumns(10);
		textField_twentiesIn.setBounds(87, 404, 41, 22);
		textField_twentiesIn.addActionListener(startValsChangedListener);
		add(textField_twentiesIn);
		
		textField_fiftiesIn = new JTextField();
		textField_fiftiesIn.setColumns(10);
		textField_fiftiesIn.setBounds(87, 437, 41, 22);
		textField_fiftiesIn.addActionListener(startValsChangedListener);
		add(textField_fiftiesIn);
		
		textField_hundredsIn = new JTextField();
		textField_hundredsIn.setColumns(10);
		textField_hundredsIn.setBounds(87, 470, 41, 22);
		textField_hundredsIn.addActionListener(startValsChangedListener);
		add(textField_hundredsIn);
		
		JTextArea txtrStartTotal = new JTextArea();
		txtrStartTotal.setBackground(SystemColor.control);
		txtrStartTotal.setText("START =");
		txtrStartTotal.setLineWrap(true);
		txtrStartTotal.setEditable(false);
		txtrStartTotal.setBounds(10, 502, 67, 27);
		add(txtrStartTotal);
		
		textArea_startTotal = new JTextArea();
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
		
		btn_initialCashDrop = new JButton("CONFIRM INITIAL CASH DROP");
		btn_initialCashDrop.setBounds(10, 536, 252, 40);
		btn_initialCashDrop.addActionListener(confirmInitialDropListener);
		add(btn_initialCashDrop);
		
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
		
		textArea_penniesIn = new JTextArea();
		textArea_penniesIn.setText("$0.00");
		textArea_penniesIn.setEditable(false);
		textArea_penniesIn.setBackground(SystemColor.menu);
		textArea_penniesIn.setBounds(160, 139, 67, 22);
		add(textArea_penniesIn);
		
		textArea_nickelsIn = new JTextArea();
		textArea_nickelsIn.setText("$0.00");
		textArea_nickelsIn.setEditable(false);
		textArea_nickelsIn.setBackground(SystemColor.menu);
		textArea_nickelsIn.setBounds(160, 172, 67, 22);
		add(textArea_nickelsIn);
		
		textArea_dimesIn = new JTextArea();
		textArea_dimesIn.setText("$0.00");
		textArea_dimesIn.setEditable(false);
		textArea_dimesIn.setBackground(SystemColor.menu);
		textArea_dimesIn.setBounds(160, 205, 67, 22);
		add(textArea_dimesIn);
		
		textArea_quartersIn = new JTextArea();
		textArea_quartersIn.setText("$0.00");
		textArea_quartersIn.setEditable(false);
		textArea_quartersIn.setBackground(SystemColor.menu);
		textArea_quartersIn.setBounds(160, 238, 67, 22);
		add(textArea_quartersIn);
		
		textArea_dollarsIn = new JTextArea();
		textArea_dollarsIn.setText("$0.00");
		textArea_dollarsIn.setEditable(false);
		textArea_dollarsIn.setBackground(SystemColor.menu);
		textArea_dollarsIn.setBounds(160, 271, 67, 22);
		add(textArea_dollarsIn);
		
		textArea_twosIn = new JTextArea();
		textArea_twosIn.setText("$0.00");
		textArea_twosIn.setEditable(false);
		textArea_twosIn.setBackground(SystemColor.menu);
		textArea_twosIn.setBounds(160, 304, 67, 22);
		add(textArea_twosIn);
		
		textArea_fivesIn = new JTextArea();
		textArea_fivesIn.setText("$0.00");
		textArea_fivesIn.setEditable(false);
		textArea_fivesIn.setBackground(SystemColor.menu);
		textArea_fivesIn.setBounds(160, 337, 67, 22);
		add(textArea_fivesIn);
		
		textArea_tensIn = new JTextArea();
		textArea_tensIn.setText("$0.00");
		textArea_tensIn.setEditable(false);
		textArea_tensIn.setBackground(SystemColor.menu);
		textArea_tensIn.setBounds(160, 370, 67, 22);
		add(textArea_tensIn);
		
		textArea_twentiesIn = new JTextArea();
		textArea_twentiesIn.setText("$0.00");
		textArea_twentiesIn.setEditable(false);
		textArea_twentiesIn.setBackground(SystemColor.menu);
		textArea_twentiesIn.setBounds(160, 403, 67, 22);
		add(textArea_twentiesIn);
		
		textArea_fiftiesIn = new JTextArea();
		textArea_fiftiesIn.setText("$0.00");
		textArea_fiftiesIn.setEditable(false);
		textArea_fiftiesIn.setBackground(SystemColor.menu);
		textArea_fiftiesIn.setBounds(160, 436, 67, 22);
		add(textArea_fiftiesIn);
		
		textArea_hundredsIn = new JTextArea();
		textArea_hundredsIn.setText("$0.00");
		textArea_hundredsIn.setEditable(false);
		textArea_hundredsIn.setBackground(SystemColor.menu);
		textArea_hundredsIn.setBounds(160, 469, 67, 22);
		add(textArea_hundredsIn);
		
		textField_penniesOut = new JTextField();
		textField_penniesOut.setColumns(10);
		textField_penniesOut.setBounds(336, 139, 41, 22);
		textField_penniesOut.addActionListener(endValsChangedListener);
		add(textField_penniesOut);
		
		textField_nickelsOut = new JTextField();
		textField_nickelsOut.setColumns(10);
		textField_nickelsOut.setBounds(336, 172, 41, 22);
		textField_nickelsOut.addActionListener(endValsChangedListener);
		add(textField_nickelsOut);
		
		textField_dimesOut = new JTextField();
		textField_dimesOut.setColumns(10);
		textField_dimesOut.setBounds(336, 205, 41, 22);
		textField_dimesOut.addActionListener(endValsChangedListener);
		add(textField_dimesOut);
		
		textField_quartersOut = new JTextField();
		textField_quartersOut.setColumns(10);
		textField_quartersOut.setBounds(336, 238, 41, 22);
		textField_quartersOut.addActionListener(endValsChangedListener);
		add(textField_quartersOut);
		
		textField_dollarsOut = new JTextField();
		textField_dollarsOut.setColumns(10);
		textField_dollarsOut.setBounds(336, 271, 41, 22);
		textField_dollarsOut.addActionListener(endValsChangedListener);
		add(textField_dollarsOut);
		
		textField_twosOut = new JTextField();
		textField_twosOut.setColumns(10);
		textField_twosOut.setBounds(336, 304, 41, 22);
		textField_twosOut.addActionListener(endValsChangedListener);
		add(textField_twosOut);
		
		textField_fivesOut = new JTextField();
		textField_fivesOut.setColumns(10);
		textField_fivesOut.setBounds(336, 337, 41, 22);
		textField_fivesOut.addActionListener(endValsChangedListener);
		add(textField_fivesOut);
		
		textField_tensOut = new JTextField();
		textField_tensOut.setColumns(10);
		textField_tensOut.setBounds(336, 370, 41, 22);
		textField_tensOut.addActionListener(endValsChangedListener);
		add(textField_tensOut);
		
		textField_twentiesOut = new JTextField();
		textField_twentiesOut.setColumns(10);
		textField_twentiesOut.setBounds(336, 403, 41, 22);
		textField_twentiesOut.addActionListener(endValsChangedListener);
		add(textField_twentiesOut);
		
		textField_fiftiesOut = new JTextField();
		textField_fiftiesOut.setColumns(10);
		textField_fiftiesOut.setBounds(336, 436, 41, 22);
		textField_fiftiesOut.addActionListener(endValsChangedListener);
		add(textField_fiftiesOut);
		
		textField_hundredsOut = new JTextField();
		textField_hundredsOut.setColumns(10);
		textField_hundredsOut.setBounds(336, 469, 41, 22);
		textField_hundredsOut.addActionListener(endValsChangedListener);
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
		
		textArea_penniesOut = new JTextArea();
		textArea_penniesOut.setText("$0.00");
		textArea_penniesOut.setEditable(false);
		textArea_penniesOut.setBackground(SystemColor.menu);
		textArea_penniesOut.setBounds(409, 139, 67, 22);
		add(textArea_penniesOut);
		
		textArea_nickelsOut = new JTextArea();
		textArea_nickelsOut.setText("$0.00");
		textArea_nickelsOut.setEditable(false);
		textArea_nickelsOut.setBackground(SystemColor.menu);
		textArea_nickelsOut.setBounds(409, 173, 67, 22);
		add(textArea_nickelsOut);
		
		textArea_dimesOut = new JTextArea();
		textArea_dimesOut.setText("$0.00");
		textArea_dimesOut.setEditable(false);
		textArea_dimesOut.setBackground(SystemColor.menu);
		textArea_dimesOut.setBounds(409, 205, 67, 22);
		add(textArea_dimesOut);
		
		textArea_quartersOut = new JTextArea();
		textArea_quartersOut.setText("$0.00");
		textArea_quartersOut.setEditable(false);
		textArea_quartersOut.setBackground(SystemColor.menu);
		textArea_quartersOut.setBounds(409, 237, 67, 22);
		add(textArea_quartersOut);
		
		textArea_dollarsOut = new JTextArea();
		textArea_dollarsOut.setText("$0.00");
		textArea_dollarsOut.setEditable(false);
		textArea_dollarsOut.setBackground(SystemColor.menu);
		textArea_dollarsOut.setBounds(409, 271, 54, 22);
		add(textArea_dollarsOut);
		
		textArea_twosOut = new JTextArea();
		textArea_twosOut.setText("$0.00");
		textArea_twosOut.setEditable(false);
		textArea_twosOut.setBackground(SystemColor.menu);
		textArea_twosOut.setBounds(409, 304, 67, 22);
		add(textArea_twosOut);
		
		textArea_fivesOut = new JTextArea();
		textArea_fivesOut.setText("$0.00");
		textArea_fivesOut.setEditable(false);
		textArea_fivesOut.setBackground(SystemColor.menu);
		textArea_fivesOut.setBounds(409, 337, 67, 22);
		add(textArea_fivesOut);
		
		textArea_tensOut = new JTextArea();
		textArea_tensOut.setText("$0.00");
		textArea_tensOut.setEditable(false);
		textArea_tensOut.setBackground(SystemColor.menu);
		textArea_tensOut.setBounds(409, 370, 67, 22);
		add(textArea_tensOut);
		
		textArea_twentiesOut = new JTextArea();
		textArea_twentiesOut.setText("$0.00");
		textArea_twentiesOut.setEditable(false);
		textArea_twentiesOut.setBackground(SystemColor.menu);
		textArea_twentiesOut.setBounds(409, 403, 67, 22);
		add(textArea_twentiesOut);
		
		textArea_fiftiesOut = new JTextArea();
		textArea_fiftiesOut.setText("$0.00");
		textArea_fiftiesOut.setEditable(false);
		textArea_fiftiesOut.setBackground(SystemColor.menu);
		textArea_fiftiesOut.setBounds(409, 436, 67, 22);
		add(textArea_fiftiesOut);
		
		textArea_hundredsOut = new JTextArea();
		textArea_hundredsOut.setText("$0.00");
		textArea_hundredsOut.setEditable(false);
		textArea_hundredsOut.setBackground(SystemColor.menu);
		textArea_hundredsOut.setBounds(409, 469, 67, 22);
		add(textArea_hundredsOut);
		
		textArea_endTotal = new JTextArea();
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
		txtrBooth.setText("      CASHIER:");
		txtrBooth.setBounds(10, 31, 121, 27);
		add(txtrBooth);
		
		JTextArea txtrX_11 = new JTextArea();
		txtrX_11.setText("X $800 =");
		txtrX_11.setEditable(false);
		txtrX_11.setBackground(SystemColor.menu);
		txtrX_11.setBounds(628, 238, 75, 22);
		add(txtrX_11);
		
		textArea_endTotalCalc = new JTextArea();
		textArea_endTotalCalc.setEditable(false);
		textArea_endTotalCalc.setBackground(SystemColor.menu);
		textArea_endTotalCalc.setBounds(671, 288, 103, 27);
		add(textArea_endTotalCalc);
		
		JTextArea txtrOfCash = new JTextArea();
		txtrOfCash.setText("   Cash Drops");
		txtrOfCash.setLineWrap(true);
		txtrOfCash.setEditable(false);
		txtrOfCash.setBackground(SystemColor.menu);
		txtrOfCash.setBounds(479, 238, 114, 22);
		add(txtrOfCash);
		
		JTextArea txtrEndTotal = new JTextArea();
		txtrEndTotal.setText("End Total");
		txtrEndTotal.setLineWrap(true);
		txtrEndTotal.setEditable(false);
		txtrEndTotal.setBackground(SystemColor.menu);
		txtrEndTotal.setBounds(577, 288, 95, 27);
		add(txtrEndTotal);
		
		JTextArea textArea_34 = new JTextArea();
		textArea_34.setText("=");
		textArea_34.setEditable(false);
		textArea_34.setBackground(SystemColor.menu);
		textArea_34.setBounds(547, 326, 12, 22);
		add(textArea_34);
		
		textArea_finalTotal = new JTextArea();
		textArea_finalTotal.setEditable(false);
		textArea_finalTotal.setBackground(SystemColor.menu);
		textArea_finalTotal.setBounds(671, 326, 103, 27);
		add(textArea_finalTotal);
		
		JTextArea txtrFinalTotal = new JTextArea();
		txtrFinalTotal.setText("Final Total");
		txtrFinalTotal.setLineWrap(true);
		txtrFinalTotal.setEditable(false);
		txtrFinalTotal.setBackground(SystemColor.menu);
		txtrFinalTotal.setBounds(566, 326, 95, 27);
		add(txtrFinalTotal);
		
		textArea_cashDrops = new JTextArea();
		textArea_cashDrops.setBackground(SystemColor.control);
		textArea_cashDrops.setEditable(false);
		textArea_cashDrops.setBounds(577, 238, 41, 27);
		add(textArea_cashDrops);
		
		textArea_cashDropsBy800 = new JTextArea();
		textArea_cashDropsBy800.setBackground(SystemColor.control);
		textArea_cashDropsBy800.setEditable(false);
		textArea_cashDropsBy800.setBounds(713, 238, 61, 27);
		add(textArea_cashDropsBy800);
		
		JTextArea textArea_35 = new JTextArea();
		textArea_35.setText("+");
		textArea_35.setEditable(false);
		textArea_35.setBackground(SystemColor.menu);
		textArea_35.setBounds(547, 288, 12, 22);
		add(textArea_35);
		
		textField_fullSheetsUnsold = new JTextField();
		textField_fullSheetsUnsold.setColumns(10);
		textField_fullSheetsUnsold.setBounds(229, 594, 41, 22);
		textField_fullSheetsUnsold.addActionListener(revenueChangedListener);
		add(textField_fullSheetsUnsold);
		
		textField_halfSheetsUnsold = new JTextField();
		textField_halfSheetsUnsold.setColumns(10);
		textField_halfSheetsUnsold.setBounds(229, 620, 41, 22);
		textField_halfSheetsUnsold.addActionListener(revenueChangedListener);
		add(textField_halfSheetsUnsold);
		
		textField_singleTicketsUnsold = new JTextField();
		textField_singleTicketsUnsold.setColumns(10);
		textField_singleTicketsUnsold.setBounds(229, 652, 41, 22);
		textField_singleTicketsUnsold.addActionListener(revenueChangedListener);
		add(textField_singleTicketsUnsold);
		
		JTextArea textArea_43 = new JTextArea();
		textArea_43.setText("=");
		textArea_43.setEditable(false);
		textArea_43.setBackground(SystemColor.menu);
		textArea_43.setBounds(280, 593, 12, 22);
		add(textArea_43);
		
		textArea_soldTickets = new JTextArea();
		textArea_soldTickets.setEditable(false);
		textArea_soldTickets.setBounds(302, 593, 59, 22);
		add(textArea_soldTickets);
		
		JTextArea textArea_54 = new JTextArea();
		textArea_54.setText("=");
		textArea_54.setEditable(false);
		textArea_54.setBackground(SystemColor.menu);
		textArea_54.setBounds(561, 593, 12, 22);
		add(textArea_54);
		
		textArea_expectedRevenue = new JTextArea();
		textArea_expectedRevenue.setBackground(SystemColor.control);
		textArea_expectedRevenue.setEditable(false);
		textArea_expectedRevenue.setBounds(601, 593, 102, 22);
		add(textArea_expectedRevenue);
		
		textArea_issuedTickets = new JTextArea();
		textArea_issuedTickets.setBackground(SystemColor.control);
		textArea_issuedTickets.setEditable(false);
		textArea_issuedTickets.setBounds(30, 619, 100, 22);
		add(textArea_issuedTickets);
		
		JLabel lblNewLabel = new JLabel("Location:");
		lblNewLabel.setBounds(23, 78, 54, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("x $0.50 + ");
		lblNewLabel_1.setBounds(371, 598, 54, 14);
		add(lblNewLabel_1);
		
		textArea_soldWristbands = new JTextArea();
		textArea_soldWristbands.setBounds(435, 593, 59, 22);
		add(textArea_soldWristbands);
		
		JLabel lblX = new JLabel("x $25.00");
		lblX.setBounds(504, 598, 54, 14);
		add(lblX);
		
		ArrayList<String> locationsArray = getLocations();
		SpinnerListModel locationsModel = new SpinnerListModel(locationsArray);
		JSpinner spinner_locations = new JSpinner(locationsModel);
		spinner_locations.setBounds(87, 69, 162, 33);
		add(spinner_locations);
		
		JLabel lblTicketsIssued = new JLabel("Tickets Issued:");
		lblTicketsIssued.setBounds(10, 594, 128, 14);
		add(lblTicketsIssued);
		
		JLabel lblWristbandsIssued = new JLabel("Wristbands Issued:");
		lblWristbandsIssued.setBounds(10, 656, 118, 14);
		add(lblWristbandsIssued);
		
		textArea_issuedWristbands = new JTextArea();
		textArea_issuedWristbands.setBackground(SystemColor.control);
		textArea_issuedWristbands.setEditable(false);
		textArea_issuedWristbands.setBounds(30, 683, 100, 22);
		add(textArea_issuedWristbands);
		
		JLabel lblUnsold = new JLabel("UNSOLD");
		lblUnsold.setBounds(152, 576, 67, 14);
		add(lblUnsold);
		
		JLabel lblNewLabel_2 = new JLabel("Sold Tickets");
		lblNewLabel_2.setBounds(302, 576, 91, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Sold Wristbands");
		lblNewLabel_3.setBounds(431, 576, 114, 14);
		add(lblNewLabel_3);
		
		textField_wristbandsUnsold = new JTextField();
		textField_wristbandsUnsold.setColumns(10);
		textField_wristbandsUnsold.setBounds(229, 685, 41, 22);
		textField_wristbandsUnsold.addActionListener(revenueChangedListener);
		add(textField_wristbandsUnsold);
		
		JLabel lblFullSheets = new JLabel("Full Sheets");
		lblFullSheets.setBounds(138, 598, 81, 14);
		add(lblFullSheets);
		
		JLabel lblHalfSheets = new JLabel("Half Sheets");
		lblHalfSheets.setBounds(138, 624, 68, 14);
		add(lblHalfSheets);
		
		JLabel lblSingleTickets = new JLabel("Single Tickets");
		lblSingleTickets.setBounds(138, 656, 81, 14);
		add(lblSingleTickets);
		
		JLabel lblWristbands = new JLabel("Wristbands");
		lblWristbands.setBounds(138, 688, 81, 14);
		add(lblWristbands);
		
		JLabel lblExpectedRevenue = new JLabel("Expected Revenue");
		lblExpectedRevenue.setBounds(601, 576, 121, 14);
		add(lblExpectedRevenue);
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
